package com.example.bookapi.book_api.service;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class RegistryClient {

    private final RestTemplate restTemplate;

    public RegistryClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retryable(
            retryFor = RestClientException.class,
            maxAttempts = 3,
            backoff = @Backoff(delay = 500)
    )
    public String searchByAuthor(String author) {
        // Mock external service URL (you can replace with your own mock server)
        String url = "http://localhost:9090/registry/search?author=" + author;
        return restTemplate.getForObject(url, String.class);
    }
}
