package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "urls")
public class UrlEntity {

    private Long id;
    private String code;
    private String originalUrl;

    protected UrlEntity() {
    }

    public UrlEntity(String code, String originalUrl) {
        this.code = code;
        this.originalUrl = originalUrl;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }
}
