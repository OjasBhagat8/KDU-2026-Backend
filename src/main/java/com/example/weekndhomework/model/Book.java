package com.example.weekndhomework.model;

import com.example.weekndhomework.constants.BookStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@AllArgsConstructor
@Data
public class Book {
    private final UUID id;
    private final String title;
    private final BookStatus status;
}
