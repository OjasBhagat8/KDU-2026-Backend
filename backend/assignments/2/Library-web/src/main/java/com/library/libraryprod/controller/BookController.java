package com.library.libraryprod.controller;

import com.library.libraryprod.entities.Book;
import com.library.libraryprod.enums.BookStatus;
import com.library.libraryprod.libraryservice.BookService;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/books/v1")
public class BookController {

    @Autowired
    private BookService bookService;

    @Operation(
            summary = "Creates a new book",
            description = "Book is created in a processing state and librarian can access the api."
    )
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book){
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    @Operation(
            summary = "Changes the status of book from processing to avaliable",
            description = "Explicit catalog transition. Handles optimistic locking."
    )
    @PatchMapping("/{id}/catalog/")
    public ResponseEntity<Book> updateBook(@PathVariable UUID id){
        return new ResponseEntity<>(bookService.updateBook(id),HttpStatus.CREATED);
    }
    @Operation(
            summary = "Get all books with filters."
    )
    @GetMapping
    public ResponseEntity<Page<Book>> getBooks(
            @RequestParam(name = "bookStatus", required = false ) BookStatus bookStatus,
            @RequestParam(name = "titleContains", required = false) String titleContains,
            @PageableDefault(size = 5)
            Pageable pageable
            ){
        return new ResponseEntity<>(bookService.getAllBooks(bookStatus,titleContains,pageable),HttpStatus.OK);
    }

}