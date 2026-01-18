package com.library.libraryprod.libraryservice;

import com.library.libraryprod.entities.Book;
import com.library.libraryprod.enums.BookStatus;
import com.library.libraryprod.exceptions.ResourceNotFoundException;
import com.library.libraryprod.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service for managing books in the catalog.
 * Handles creation, state transitions, retrieval, and filtered listings.
 */
@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Adds a new book and initializes its status to PROCESSING.
     *
     * @param book the book to persist
     * @return the saved book with status set to PROCESSING
     */
    public Book addBook(Book book){
        book.setBookStatus(BookStatus.PROCESSING);
        return bookRepository.save(book);
    }

    /**
     * Marks a book as AVAILABLE if it is currently in PROCESSING.
     *
     * @param id the book identifier
     * @return the updated book
     * @throws ResourceNotFoundException if the book does not exist
     * @throws IllegalStateException if the current status is not PROCESSING
     */
    public Book updateBook(UUID id){
        Book book = bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Resource with id " + id +" not found"));
        if(book.getBookStatus() != BookStatus.PROCESSING){
            throw new IllegalStateException("Illegal State");
        }
        book.setBookStatus(BookStatus.AVAILABLE);
        return bookRepository.save(book);
    }

    /**
     * Retrieves a book by its identifier.
     *
     * @param id the book identifier
     * @return the found book
     * @throws ResourceNotFoundException if the book does not exist
     */
    public Book getBook(UUID id){
       return bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Resource with id " + id +" not found"));
    }

    /**
     * Retrieves a page of books filtered by status and title substring (case-insensitive).
     * If a filter is null or blank, it is ignored.
     *
     * @param bookStatus optional status filter
     * @param titleContains optional substring to search within titles
     * @param pageable pagination and sorting information
     * @return a page of matching books
     */
    public Page<Book> getAllBooks(BookStatus bookStatus, String titleContains, Pageable pageable) {
        Specification<Book> spec = Specification
                .where(BookSpecifications.hasStatus(bookStatus))
                .and(BookSpecifications.titleContains(titleContains));

        return bookRepository.findAll(spec,pageable);
    }
}
