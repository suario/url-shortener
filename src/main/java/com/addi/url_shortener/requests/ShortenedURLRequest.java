/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class ShortenedURLRequest {

	@NotBlank(message = "URL is mandatory")
	@Pattern(regexp = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]")
	private String url;
}
