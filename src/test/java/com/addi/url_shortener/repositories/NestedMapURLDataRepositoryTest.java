/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.addi.url_shortener.utils.Constants;
import com.addi.url_shortener.utils.UrlShortenerUtils;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NestedMapURLDataRepositoryTest {

	private NestedMapURLDataRepository nestedMapUrlDataRepository;

	@BeforeEach
	void setUp() {
		Map<String, Map<String, String>> urlMap = new HashMap<>();
		Map<String, String> codeMap = new HashMap<>();
		codeMap.put(Constants.ALLOWED_CHARACTERS[0], "test");

		urlMap.put("code", codeMap);

		nestedMapUrlDataRepository = new NestedMapURLDataRepository(urlMap);
	}

	@Test
	@DisplayName("gets the url from the memory")
	void getUrl() {
		Optional<String> result = nestedMapUrlDataRepository.findByCode("code");

		assertEquals("test", result.orElse(null));
	}

	@Test
	@DisplayName("throws exception when url is not found in the map")
	void getUrl2() {
		Map<String, Map<String, String>> urlMap = new HashMap<>();

		nestedMapUrlDataRepository = new NestedMapURLDataRepository(urlMap);

		Optional<String> result = nestedMapUrlDataRepository.findByCode("nocode");

		assertTrue(result.isEmpty());
	}

	@Test
	@DisplayName("throws exception when url is found in the map, but not found in the child map")
	void getUrl3() {
		Map<String, Map<String, String>> urlMap = new HashMap<>();
		Map<String, String> codeMap = new HashMap<>();
		codeMap.put(Constants.ALLOWED_CHARACTERS[5], Constants.LOCAL_URL + "test");

		urlMap.put("codenuev", codeMap);

		nestedMapUrlDataRepository = new NestedMapURLDataRepository(urlMap);

		Optional<String> result = nestedMapUrlDataRepository.findByCode("codenuevo");

		assertTrue(result.isEmpty());
	}

	@Test
	@DisplayName("generates a url with a given code")
	void buildShortCode() {

		try (MockedStatic<UrlShortenerUtils> utilities = Mockito.mockStatic(UrlShortenerUtils.class)) {
			utilities.when(UrlShortenerUtils::generateCode).thenReturn(new StringBuilder("test"));
			utilities.when(() -> UrlShortenerUtils.generateSubcode(0)).thenReturn("sub");

			String result = nestedMapUrlDataRepository.save("url");

			assertEquals("test", result);
		}
	}

	@Test
	@DisplayName("generates a second url with the same code")
	void buildShortCode2() {

		Map<String, Map<String, String>> urlMap = new HashMap<>();
		Map<String, String> codeMap = new HashMap<>();
		urlMap.put("test", codeMap);

		nestedMapUrlDataRepository = new NestedMapURLDataRepository(urlMap);

		try (MockedStatic<UrlShortenerUtils> utilities = Mockito.mockStatic(UrlShortenerUtils.class)) {
			utilities.when(UrlShortenerUtils::generateCode).thenReturn(new StringBuilder("test"));
			utilities.when(() -> UrlShortenerUtils.generateSubcode(0)).thenReturn("sub");

			String result = nestedMapUrlDataRepository.save("url");

			assertEquals("testsub", result);
		}
	}
}
