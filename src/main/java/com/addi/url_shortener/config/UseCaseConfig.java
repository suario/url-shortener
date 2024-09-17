/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.config;

import com.addi.url_shortener.adapters.URLRepository;
import com.addi.url_shortener.usecases.GetURLUseCase;
import com.addi.url_shortener.usecases.ShortenURLUseCase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

	@Bean
	public ShortenURLUseCase createShortenURLUseCase(URLRepository urlRepository) {
		return new ShortenURLUseCase(urlRepository);
	}

	@Bean
	public GetURLUseCase createGetURLUseCase(URLRepository urlRepository) {
		return new GetURLUseCase(urlRepository);
	}
}
