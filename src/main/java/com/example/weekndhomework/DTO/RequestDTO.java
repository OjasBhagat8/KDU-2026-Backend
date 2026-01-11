package com.example.weekndhomework.DTO;

import com.example.weekndhomework.constants.BookStatus;

public class RequestDTO {
    private String title;
    private BookStatus status;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public BookStatus getStatus() { return status; }
    public void setStatus(BookStatus status) { this.status = status; }
}
