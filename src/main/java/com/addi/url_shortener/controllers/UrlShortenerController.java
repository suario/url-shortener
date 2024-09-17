/* (C) Jorge Suarez 2024 */
package com.addi.url_shortener.controllers;

import com.addi.url_shortener.exceptions.BusinessException;
import com.addi.url_shortener.requests.ShortenedURLRequest;
import com.addi.url_shortener.responses.ShortenedURLResponse;
import com.addi.url_shortener.usecases.GetURLUseCase;
import com.addi.url_shortener.usecases.ShortenURLUseCase;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("url")
public class UrlShortenerController {

	@Autowired
	private ShortenURLUseCase shortenURLUseCase;

	@Autowired
	private GetURLUseCase getURLUseCase;

	@PostMapping(name = "/", produces = "application/json")
	public ShortenedURLResponse createUrl(@Valid @RequestBody ShortenedURLRequest shortenedURLRequest)
			throws BusinessException {
		String shortenedUrl = shortenURLUseCase.buildShortCode(shortenedURLRequest.getUrl());

		return new ShortenedURLResponse(shortenedUrl);
	}

	@GetMapping(name = "/{code}", produces = "application/json")
	public ShortenedURLResponse getUrl(@PathVariable @Pattern(regexp = "[A-Za-z0-9]+") String code)
			throws BusinessException {
		String shortenedUrl = getURLUseCase.getUrl(code);

		return new ShortenedURLResponse(shortenedUrl);
	}
}
