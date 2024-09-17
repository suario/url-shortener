/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.enums;

import lombok.Getter;

@Getter
public enum ApplicationResponseEnum {
	URL_SHORTENER_BAD_REQUEST(300, "Bad request"), URL_SHORTENER_VALIDATION_ERROR(301,
			"There are some Validation errors"), URL_NOT_FOUND_EXCEPTION(302, "Requested URL was not found!");

	private final int code;
	private final String message;

	ApplicationResponseEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
