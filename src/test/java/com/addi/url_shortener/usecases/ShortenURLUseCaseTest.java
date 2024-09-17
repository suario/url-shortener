/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.addi.url_shortener.utils.Constants;
import com.addi.url_shortener.utils.UrlShortenerUtils;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class ShortenURLUseCaseTest {
	private ShortenURLUseCase urlUseCase;

	@BeforeEach
	void setUp() {
		Map<String, Map<String, String>> urlMap = new HashMap<>();

		urlUseCase = new ShortenURLUseCase(urlMap);
	}

	@Test
	@DisplayName("generates a url with a given code")
	void buildShortCode() {

		try (MockedStatic<UrlShortenerUtils> utilities = Mockito.mockStatic(UrlShortenerUtils.class)) {
			utilities.when(UrlShortenerUtils::generateCode).thenReturn(new StringBuilder("test"));
			utilities.when(() -> UrlShortenerUtils.generateSubcode(0)).thenReturn("sub");

			String result = urlUseCase.buildShortCode("url");

			assertEquals(Constants.LOCAL_URL + "test", result);
		}
	}

	@Test
	@DisplayName("generates a second url with the same code")
	void buildShortCode2() {

		Map<String, Map<String, String>> urlMap = new HashMap<>();
		Map<String, String> codeMap = new HashMap<>();
		urlMap.put("test", codeMap);

		urlUseCase = new ShortenURLUseCase(urlMap);

		try (MockedStatic<UrlShortenerUtils> utilities = Mockito.mockStatic(UrlShortenerUtils.class)) {
			utilities.when(UrlShortenerUtils::generateCode).thenReturn(new StringBuilder("test"));
			utilities.when(() -> UrlShortenerUtils.generateSubcode(0)).thenReturn("sub");

			String result = urlUseCase.buildShortCode("url");

			assertEquals(Constants.LOCAL_URL + "testsub", result);
		}
	}
}
