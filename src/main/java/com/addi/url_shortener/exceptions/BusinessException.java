/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.exceptions;

import com.addi.url_shortener.enums.ApplicationResponseEnum;

public class BusinessException extends Exception {

	private final ApplicationResponseEnum responseCode;

	public BusinessException(ApplicationResponseEnum responseCode) {
		this.responseCode = responseCode;
	}

	public BusinessException(ApplicationResponseEnum responseCode, Exception exception) {
		super(exception);
		this.responseCode = responseCode;
	}

	public ApplicationResponseEnum getResponseCode() {
		return responseCode;
	}
}
