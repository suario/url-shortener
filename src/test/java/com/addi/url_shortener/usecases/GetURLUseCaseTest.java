/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.addi.url_shortener.enums.ApplicationResponseEnum;
import com.addi.url_shortener.exceptions.BusinessException;
import com.addi.url_shortener.utils.Constants;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class GetURLUseCaseTest {

	private GetURLUseCase urlUseCase;

	@BeforeEach
	void setUp() {
		Map<String, Map<String, String>> urlMap = new HashMap<>();
		Map<String, String> codeMap = new HashMap<>();
		codeMap.put(Constants.ALLOWED_CHARACTERS[0], Constants.LOCAL_URL + "test");

		urlMap.put("code", codeMap);

		urlUseCase = new GetURLUseCase(urlMap);
	}

	@Test
	@DisplayName("gets the url from the memory")
	void getUrl() throws BusinessException {
		String result = urlUseCase.getUrl("code");

		assertEquals(Constants.LOCAL_URL + "test", result);
	}

	@Test
	@DisplayName("throws exception when url is not found in the map")
	void getUrl2() {
		Map<String, Map<String, String>> urlMap = new HashMap<>();

		urlUseCase = new GetURLUseCase(urlMap);

		BusinessException exception = assertThrows(BusinessException.class, () -> urlUseCase.getUrl("nocode"));

		assertEquals(ApplicationResponseEnum.URL_NOT_FOUND_EXCEPTION, exception.getResponseCode());
	}

	@Test
	@DisplayName("throws exception when url is found in the map, but not found in the child map")
	void getUrl3() {
		Map<String, Map<String, String>> urlMap = new HashMap<>();
		Map<String, String> codeMap = new HashMap<>();
		codeMap.put(Constants.ALLOWED_CHARACTERS[5], Constants.LOCAL_URL + "test");

		urlMap.put("codenuev", codeMap);

		urlUseCase = new GetURLUseCase(urlMap);

		BusinessException exception = assertThrows(BusinessException.class, () -> urlUseCase.getUrl("codenuevo"));

		assertEquals(ApplicationResponseEnum.URL_NOT_FOUND_EXCEPTION, exception.getResponseCode());
	}
}
