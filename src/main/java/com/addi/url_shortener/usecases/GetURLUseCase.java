/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.usecases;

import com.addi.url_shortener.adapters.URLRepository;
import com.addi.url_shortener.enums.ApplicationResponseEnum;
import com.addi.url_shortener.exceptions.BusinessException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetURLUseCase {
	private final URLRepository urlRepository;

	public String getUrl(String code) throws BusinessException {
		Optional<String> result = urlRepository.findByCode(code);

		return result.orElseThrow(() -> new BusinessException(ApplicationResponseEnum.URL_NOT_FOUND_EXCEPTION));
	}
}
