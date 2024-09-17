/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.usecases;

import com.addi.url_shortener.adapters.URLRepository;
import com.addi.url_shortener.utils.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class ShortenURLUseCase {

	private final URLRepository urlRepository;

	public String buildShortCode(String url) {
		var code = urlRepository.save(url);

		return Constants.LOCAL_URL + code;
	}
}
