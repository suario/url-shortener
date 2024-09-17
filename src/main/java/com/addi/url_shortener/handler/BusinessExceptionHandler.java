/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.handler;

import com.addi.url_shortener.enums.ApplicationResponseEnum;
import com.addi.url_shortener.exceptions.BusinessException;
import com.addi.url_shortener.responses.ErrorResponse;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ControllerAdvice
@Slf4j
public class BusinessExceptionHandler {

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleInternalException(Exception ex) {
		if (!ObjectUtils.isEmpty(ex.getCause())) {
			log.error("Exception: {}", ex.getCause().getLocalizedMessage());
		}
		return new ErrorResponse(300, ex.getMessage());
	}

	@ExceptionHandler(HandlerMethodValidationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleBusinessException(HandlerMethodValidationException ex) {
		log.error("Validation exception: {}", ex.getLocalizedMessage());
		return new ErrorResponse(ApplicationResponseEnum.URL_SHORTENER_BAD_REQUEST.getCode(),
				ApplicationResponseEnum.URL_SHORTENER_BAD_REQUEST.getMessage());
	}

	@ExceptionHandler(BusinessException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleBusinessException(BusinessException ex) {
		if (!ObjectUtils.isEmpty(ex.getCause())) {
			log.error("Exception: {}", ex.getCause().getLocalizedMessage());
		}
		return new ErrorResponse(ex.getResponseCode().getCode(), ex.getResponseCode().getMessage());
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ErrorResponse handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return new ErrorResponse(ApplicationResponseEnum.URL_SHORTENER_VALIDATION_ERROR.getCode(),
				ApplicationResponseEnum.URL_SHORTENER_VALIDATION_ERROR.getMessage(), errors);
	}
}
