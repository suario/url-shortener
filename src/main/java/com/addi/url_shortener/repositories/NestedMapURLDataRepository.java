/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.repositories;

import com.addi.url_shortener.adapters.URLRepository;
import com.addi.url_shortener.utils.Constants;
import com.addi.url_shortener.utils.UrlShortenerUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
@RequiredArgsConstructor
public class NestedMapURLDataRepository implements URLRepository {

	private final Map<String, Map<String, String>> urlMap;

	public NestedMapURLDataRepository() {
		this.urlMap = new HashMap<>();
	}

	public String save(String url) {
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

		return codeBuilder.toString();
	}

	public Optional<String> findByCode(String code) {
		String mainCode = code;
		String subCode = Constants.ALLOWED_CHARACTERS[0];

		if (code.length() > Constants.RECOMMENDED_LENGTH) {
			mainCode = code.substring(0, Constants.RECOMMENDED_LENGTH);
			subCode = code.substring(Constants.RECOMMENDED_LENGTH);
		}

		if (this.urlMap.containsKey(mainCode)) {
			Map<String, String> subUrls = this.urlMap.get(mainCode);

			if (subUrls.containsKey(subCode)) {
				return Optional.of(subUrls.get(subCode));
			}
		}

		return Optional.empty();
	}
}
