/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.usecases;

import com.addi.url_shortener.utils.Constants;
import com.addi.url_shortener.utils.UrlShortenerUtils;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ShortenURLUseCase {

	private final Map<String, Map<String, String>> urlMap;

	public String buildShortCode(String url) {
		var codeBuilder = UrlShortenerUtils.generateCode();

		String code = codeBuilder.toString();

		if (urlMap.containsKey(code)) {
			int size = urlMap.get(code).size();
			var subString = UrlShortenerUtils.generateSubcode(size);

			urlMap.get(code).put(subString, url);
			codeBuilder.append(subString);
		} else {
			Map<String, String> codeMap = new HashMap<>();
			codeMap.put(Constants.ALLOWED_CHARACTERS[0], url);

			this.urlMap.put(code, codeMap);
		}

		return codeBuilder.insert(0, Constants.LOCAL_URL).toString();
	}
}
