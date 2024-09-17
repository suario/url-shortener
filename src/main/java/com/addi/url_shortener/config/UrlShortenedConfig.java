/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.config;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UrlShortenedConfig {

	@Bean
	public Map<String, Map<String, String>> createUrlStorage() {
		return Collections.synchronizedMap(new HashMap<>());
	}
}
