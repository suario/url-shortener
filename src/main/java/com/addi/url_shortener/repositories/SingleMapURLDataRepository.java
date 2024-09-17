/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.repositories;

import com.addi.url_shortener.adapters.URLRepository;
import com.addi.url_shortener.utils.Constants;
import com.addi.url_shortener.utils.UrlShortenerUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class SingleMapURLDataRepository implements URLRepository {

	private final Map<String, String> urlMap;
	private final Map<String, Map<String, String>> exceptionMap;

	public SingleMapURLDataRepository() {
		this.urlMap = new HashMap<>();
		this.exceptionMap = new HashMap<>();
	}

	public String save(String url) {
		var codeBuilder = UrlShortenerUtils.generateCode();

		String code = codeBuilder.toString();

		if (urlMap.containsKey(code)) {
			if (!this.exceptionMap.containsKey(code)) {
				this.exceptionMap.put(code, new HashMap<>());
			}
			int size = this.exceptionMap.get(code).size();
			var subString = UrlShortenerUtils.generateSubcode(size);
			codeBuilder.append(subString);

			this.exceptionMap.get(code).put(subString, url);
		} else {
			this.urlMap.put(code, url);
		}

		return codeBuilder.toString();
	}

	public Optional<String> findByCode(String code) {
		if (this.urlMap.containsKey(code)) {
			return Optional.of(this.urlMap.get(code));
		} else {
			if (code.length() > Constants.RECOMMENDED_LENGTH) {
				String mainCode = code.substring(0, Constants.RECOMMENDED_LENGTH);
				String subCode = code.substring(Constants.RECOMMENDED_LENGTH);

				if (this.exceptionMap.containsKey(mainCode) && this.exceptionMap.get(mainCode).containsKey(subCode)) {
					return Optional.of(this.exceptionMap.get(mainCode).get(subCode));
				}
			}
		}

		return Optional.empty();
	}
}
