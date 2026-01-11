package com.example.weekndhomework.controller;

import com.example.weekndhomework.DTO.BookResponseDTO;
import com.example.weekndhomework.constants.BookStatus;
import com.example.weekndhomework.model.Book;
import com.example.weekndhomework.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.weekndhomework.DTO.RequestDTO;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;


@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;


    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Endpoint to get all books
    @GetMapping
    public List<BookResponseDTO> getAllBooks() {
        return bookService.getAllBooks();

    }

    // Endpoint to add a new book in the local repository
    @Operation(summary = "Starts the async book processing")
    @PostMapping
    public ResponseEntity<BookResponseDTO> addBook(@RequestBody RequestDTO req){


        BookResponseDTO book = bookService.addBook(req.getTitle());
        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

}
