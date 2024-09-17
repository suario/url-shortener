/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.addi.url_shortener.adapters.URLRepository;
import com.addi.url_shortener.utils.Constants;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ShortenURLUseCaseTest {

	@Mock
	private URLRepository urlRepository;

	@InjectMocks
	private ShortenURLUseCase urlUseCase;

	@Test
	@DisplayName("generates a url with a given code")
	void buildShortCode() {

		when(urlRepository.save(anyString())).thenReturn("xyz");

		String result = urlUseCase.buildShortCode("url");

		assertEquals(Constants.LOCAL_URL + "xyz", result);
	}
}
