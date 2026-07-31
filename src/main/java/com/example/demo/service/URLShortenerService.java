package com.example.demo.service;

import com.example.demo.dto.ShortenUrlResponse;
import com.example.demo.entity.UrlEntity;
import com.example.demo.repository.UrlRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class URLShortenerService {

    private static final Logger log = LoggerFactory.getLogger(URLShortenerService.class);
    private static final String BASE62_CHARS = "abcdefghijklmnopqrstuvwxyz";

    private final UrlRepository urlRepository;

    @Value("${app.base-url}")
    private String baseUrl;

    public URLShortenerService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    @Transactional
    public ShortenUrlResponse shortenUrl(String url) {

        UrlEntity entity = urlRepository.saveAndFlush(new UrlEntity(null, url.trim()));
        String code = makeCode(entity.getId());
        entity.setCode(code);
        urlRepository.save(entity);

        String shortUrl = baseUrl + "/api/urls/" + code;
        return new ShortenUrlResponse(code, shortUrl);
    }

    @Transactional(readOnly = true)
    public String resolveOriginalUrl(String code) {

        UrlEntity entity = urlRepository.findByCode(code)
                .orElseThrow(() -> {
                    log.warn("Short code '{}' not found", code);
                    return new RuntimeException("Short code not found");
                });
                
        return entity.getOriginalUrl();
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
