/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.config;

import com.addi.url_shortener.usecases.GetURLUseCase;
import com.addi.url_shortener.usecases.ShortenURLUseCase;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

	@Bean
	public ShortenURLUseCase createShortenURLUseCase(Map<String, Map<String, String>> urlMap) {
		return new ShortenURLUseCase(urlMap);
	}

	@Bean
	public GetURLUseCase createGetURLUseCase(Map<String, Map<String, String>> urlMap) {
		return new GetURLUseCase(urlMap);
	}
}
