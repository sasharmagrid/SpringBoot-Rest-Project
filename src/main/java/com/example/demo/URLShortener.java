package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class URLShortener {

    private final Map<String, String> urls = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong(1);

    @PostMapping("/api/shorten")
    public Map<String, String> shorten(@RequestBody Map<String, String> body) {
        String longUrl = body.get("url");

        long num = counter.getAndIncrement();
        String shortCode = makeCode(num);

        urls.put(shortCode, longUrl);

        Map<String, String> resp = new HashMap<>();
        resp.put("shortUrl", "http://localhost:8080/" + shortCode);
        resp.put("code", shortCode);

        return resp;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> goToUrl(@PathVariable String code) {
        String originalUrl = urls.get(code);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

    private String makeCode(long num) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();

        while (num > 0) {
            int rem = (int) (num % 62);
            sb.append(chars.charAt(rem));
            num = num / 62;
        }

        return sb.reverse().toString();
    }

    @GetMapping("/getMap")
    public Map<String, String> getMap() {
        return urls;
    }
}
