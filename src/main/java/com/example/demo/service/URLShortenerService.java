package com.example.demo.service;

import com.example.demo.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class URLShortenerService {

    private static final String BASE62_CHARS = "abcdefghijklmnopqrstuvwxyz";

    private final UrlRepository urlRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    public URLShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    private String makeCode(long num) {
        StringBuilder sb = new StringBuilder();

        while (num > 0) {
            int rem = (int) (num % 62);
            sb.append(BASE62_CHARS.charAt(rem));
            num = num / 62;
        }

        if (sb.isEmpty()) {
            sb.append(BASE62_CHARS.charAt(0));
        }

        return sb.reverse().toString();
    }
}
