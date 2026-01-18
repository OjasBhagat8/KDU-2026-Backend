package com.library.libraryprod.dto.response;

import lombok.Data;

import java.time.Instant;

@Data
public class BookResponse {
    Long id;
    String title;
    String author;
    String isbn;
    String catalogStatus;
    Instant createdAt;
    Instant updatedAt;
}
