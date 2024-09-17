/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.controllers;

import com.addi.url_shortener.exceptions.BusinessException;
import com.addi.url_shortener.requests.ShortenedURLRequest;
import com.addi.url_shortener.responses.ShortenedURLResponse;
import com.addi.url_shortener.usecases.GetURLUseCase;
import com.addi.url_shortener.usecases.ShortenURLUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("url")
@Slf4j
public class UrlShortenerController {

	@Autowired
	private ShortenURLUseCase shortenURLUseCase;

	@Autowired
	private GetURLUseCase getURLUseCase;

	@PostMapping(produces = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ShortenedURLResponse createUrl(@Valid @RequestBody ShortenedURLRequest shortenedURLRequest) {
		log.info("Processing URL {}", shortenedURLRequest.getUrl());
		String shortenedUrl = shortenURLUseCase.buildShortCode(shortenedURLRequest.getUrl().strip());

		return new ShortenedURLResponse(shortenedUrl);
	}

	@RequestMapping(value = "/{code}", method = RequestMethod.GET)
	public ShortenedURLResponse getUrl(@PathVariable @Pattern(regexp = "[A-Za-z0-9]+") String code)
			throws BusinessException {
		log.info("Finding URL for code {}", code);

		String shortenedUrl = getURLUseCase.getUrl(code);

		return new ShortenedURLResponse(shortenedUrl);
	}
}
