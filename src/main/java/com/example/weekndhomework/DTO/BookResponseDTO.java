package com.example.weekndhomework.DTO;

public class BookResponseDTO {
    private final String title;
    private final String status;

    public BookResponseDTO(String title, String status) {
        this.title = title;
        this.status = status;
    }

    public String getTitle() { return title; }
    public String getStatus() { return status; }
}
