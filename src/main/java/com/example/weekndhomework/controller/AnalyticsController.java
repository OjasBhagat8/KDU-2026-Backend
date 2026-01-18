package com.example.weekndhomework.controller;

import com.example.weekndhomework.service.BookService;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/analytics")
public class AnalyticsController {
    private final BookService bookService;

    public AnalyticsController(BookService bookService) {
        this.bookService = bookService;
    }


    // Endpoint to get library audit information based on book statuses
    @GetMapping("/audit")
    public Map<String,Long> audit(){
        return bookService.getLibraryAudit();
    }
}
