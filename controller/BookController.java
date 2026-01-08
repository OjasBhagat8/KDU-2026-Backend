package com.example.bookapi.book_api.controller;

import com.example.bookapi.book_api.Model.Book;
import com.example.bookapi.book_api.bookRepository.BookRepository;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    private final  BookRepository bookRepository;

    public BookController(BookRepository bookRepository){
        this.bookRepository = bookRepository;
    }
    @PostMapping
    public ResponseEntity<?> AddBook( @Valid @RequestBody Book book){
        if(bookRepository.checkBookExists(book.getTitle(),book.getAuthor())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Book Already exists");
        }
        book.setTitle(book.getTitle().trim());
        book.setAuthor(book.getAuthor().trim());

        return ResponseEntity.status(HttpStatus.CREATED).body(bookRepository.saveBook(book));
    }

    @GetMapping
    public ResponseEntity<List<Book>> getBooks(@Valid
            @RequestParam(required = false) String author,
            @RequestParam(defaultValue = "title") String sort,
            @RequestParam(defaultValue = "1") int page
    ){
        int pageSize = 10;
        if(page<1) page =1;

        List<Book> books = bookRepository.findAll();

        if(author != null && !author.trim().isEmpty()){
            String lowerAuthor = author.trim().toLowerCase();
            books = books.stream()
                    .filter(book -> book.getAuthor() != null
                            && book.getAuthor().trim().toLowerCase().equals(lowerAuthor))
                    .toList();
        }

        Comparator<Book> byTitle = Comparator.comparing(book -> book.getTitle() == null
                ? "" :
                book.getTitle().trim().toLowerCase());

        if("desc".equalsIgnoreCase(sort)){
            byTitle = byTitle.reversed();
        }
        books = books.stream().sorted(byTitle).toList();

        int startIndex = (page -1)* pageSize;
        if(startIndex >= books.size()){
            return ResponseEntity.ok(List.of());
        }
        int endIndex = Math.min(startIndex +pageSize, books.size());

        return ResponseEntity.ok(books.subList(startIndex,endIndex));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Book>> getBookById(@PathVariable Long id){

        Book book = bookRepository.findById(id).orElse(null);
        if (book == null) return ResponseEntity.notFound().build();

        EntityModel<Book> model = EntityModel.of(book,
                linkTo(methodOn(BookController.class).getBookById(id)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks(null, "asc", 1)).withRel("allBooks")
        );

        return ResponseEntity.ok(model);

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBook(@PathVariable long id ,@Valid @RequestBody Book book){
        Book existingBook = bookRepository.findById(id).orElse(null);

        if(existingBook == null){
            return ResponseEntity.notFound().build();
        }

        boolean duplicate = bookRepository.checkBookExists(book.getTitle().trim().toLowerCase()
                ,book.getAuthor().trim().toLowerCase())
                && !(existingBook.getTitle().trim().equalsIgnoreCase(book.getTitle()))
                &&!(existingBook.getAuthor().trim().equalsIgnoreCase(book.getAuthor()));
        if(duplicate){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Another book with same title and author exists");
        }
        existingBook.setTitle(book.getTitle().trim());
        existingBook.setAuthor(book.getAuthor().trim());
        return ResponseEntity.ok(bookRepository.saveBook(existingBook));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable long id){
        Book existingBook = bookRepository.findById(id).orElse(null);
        if(existingBook == null ) return ResponseEntity.notFound().build();

        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}