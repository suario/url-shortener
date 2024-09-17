/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.addi.url_shortener.adapters.URLRepository;
import com.addi.url_shortener.enums.ApplicationResponseEnum;
import com.addi.url_shortener.exceptions.BusinessException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class GetURLUseCaseTest {

	@Mock
	private URLRepository urlRepository;

	@InjectMocks
	private GetURLUseCase urlUseCase;

	@Test
	@DisplayName("gets the url from the memory")
	void getUrl() throws BusinessException {

		when(urlRepository.findByCode(anyString())).thenReturn(Optional.of("url"));
		String result = urlUseCase.getUrl("code");

		assertEquals("url", result);
	}

	@Test
	@DisplayName("throws exception when url is not found in the map")
	void getUrl2() {
		when(urlRepository.findByCode(anyString())).thenReturn(Optional.empty());

		BusinessException exception = assertThrows(BusinessException.class, () -> urlUseCase.getUrl("code"));

		assertEquals(ApplicationResponseEnum.URL_NOT_FOUND_EXCEPTION, exception.getResponseCode());
	}
}
