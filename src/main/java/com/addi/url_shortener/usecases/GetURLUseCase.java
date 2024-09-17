/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.usecases;

import com.addi.url_shortener.enums.ApplicationResponseEnum;
import com.addi.url_shortener.exceptions.BusinessException;
import com.addi.url_shortener.utils.Constants;
import java.util.Map;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetURLUseCase {

	private final Map<String, Map<String, String>> urlMap;

	public String getUrl(String code) throws BusinessException {
		String mainCode = code;
		String subCode = Constants.ALLOWED_CHARACTERS[0];

		if (code.length() > Constants.RECOMMENDED_LENGTH) {
			mainCode = code.substring(0, Constants.RECOMMENDED_LENGTH);
			subCode = code.substring(Constants.RECOMMENDED_LENGTH);
		}

		if (this.urlMap.containsKey(mainCode)) {
			Map<String, String> subUrls = this.urlMap.get(mainCode);

			if (subUrls.containsKey(subCode)) {
				return subUrls.get(subCode);
			}
			throw new BusinessException(ApplicationResponseEnum.URL_NOT_FOUND_EXCEPTION);
		}

		throw new BusinessException(ApplicationResponseEnum.URL_NOT_FOUND_EXCEPTION);
	}
}
