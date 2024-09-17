/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.responses;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ShortenedURLResponse {
	private final String url;
}
