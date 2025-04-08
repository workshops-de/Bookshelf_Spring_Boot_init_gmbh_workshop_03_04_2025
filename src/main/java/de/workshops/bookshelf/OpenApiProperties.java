package de.workshops.bookshelf;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.net.URL;

@ConfigurationProperties(prefix = "openapi")
public record OpenApiProperties(
        String title,
        String version,
        Integer capacity,
        License license) {

    public record License(String name, URL url) {}
}
