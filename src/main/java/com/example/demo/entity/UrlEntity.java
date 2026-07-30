package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "urls")
public class UrlEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, length = 16)
    private String code;

    @Column(nullable = false, length = 2048)
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
