package com.example.demo.controller;

import com.example.demo.dto.ShortenUrlRequest;
import com.example.demo.dto.ShortenUrlResponse;
import com.example.demo.service.URLShortenerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/urls")
public class URLShortenerController {

    private final URLShortenerService urlShortenerService;

    public URLShortenerController(URLShortenerService urlShortenerService) {
        this.urlShortenerService = urlShortenerService;
    }

    @PostMapping
    public ResponseEntity<ShortenUrlResponse> shortenURL(@RequestBody ShortenUrlRequest request) {
        ShortenUrlResponse response = urlShortenerService.shortenUrl(request.getUrl());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirectURL(@PathVariable String code) {
        String originalUrl = urlShortenerService.resolveOriginalUrl(code);
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }
}
