package com.example.bookapi.book_api.service;

import com.example.bookapi.book_api.Exception.ResourceNotFound;
import com.example.bookapi.book_api.Model.Book;
import com.example.bookapi.book_api.bookRepository.BookRepository;
import com.example.bookapi.book_api.controller.BookController;
import com.example.bookapi.book_api.exception.BookNotFoundException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book addBook(Book book) {
        // normalize
        book.setTitle(book.getTitle().trim());
        book.setAuthor(book.getAuthor().trim());

        // business rule: no duplicates by title+author
        if (bookRepository.checkBookExists(book.getTitle(), book.getAuthor())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Book already exists");
        }

        return bookRepository.saveBook(book);
    }

    public List<Book> getBooks(String author, String sort, int page) {
        int pageSize = 10;
        if (page < 1) page = 1;

        List<Book> books = bookRepository.findAll();

        // filtering by author
        if (author != null && !author.trim().isEmpty()) {
            String a = author.trim().toLowerCase();
            books = books.stream()
                    .filter(b -> b.getAuthor() != null &&
                            b.getAuthor().trim().toLowerCase().equals(a))
                    .toList();
        }

        // sorting by title asc/desc
        Comparator<Book> byTitle = Comparator.comparing(
                b -> b.getTitle() == null ? "" : b.getTitle().trim().toLowerCase()
        );

        if ("desc".equalsIgnoreCase(sort)) {
            byTitle = byTitle.reversed();
        }
        books = books.stream().sorted(byTitle).toList();

        // pagination
        int startIndex = (page - 1) * pageSize;
        if (startIndex >= books.size()) return List.of();

        int endIndex = Math.min(startIndex + pageSize, books.size());
        return books.subList(startIndex, endIndex);
    }

    public EntityModel<Book> getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(id));

        return EntityModel.of(book,
                linkTo(methodOn(BookController.class).getBookById(id)).withSelfRel(),
                linkTo(methodOn(BookController.class).getBooks(null, "asc", 1)).withRel("allBooks")
        );
    }

    public Book updateBook(long id, Book book) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(id));

        String newTitle = book.getTitle().trim();
        String newAuthor = book.getAuthor().trim();

        boolean exists = bookRepository.checkBookExists(newTitle, newAuthor);
        boolean sameAsCurrent =
                existingBook.getTitle() != null && existingBook.getAuthor() != null &&
                        existingBook.getTitle().trim().equalsIgnoreCase(newTitle) &&
                        existingBook.getAuthor().trim().equalsIgnoreCase(newAuthor);

        if (exists && !sameAsCurrent) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Another book with same title and author exists");
        }

        existingBook.setTitle(newTitle);
        existingBook.setAuthor(newAuthor);

        return bookRepository.saveBook(existingBook);
    }

    public void deleteBook(long id) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFound(id));

        bookRepository.deleteById(existingBook.getId());
    }
}
