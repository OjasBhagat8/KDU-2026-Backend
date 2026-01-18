package com.library.libraryprod.libraryservice;

import com.library.libraryprod.entities.Book;
import com.library.libraryprod.enums.BookStatus;
import com.library.libraryprod.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provides read-only analytics over the library catalog.
 * Exposes aggregated counts of books grouped by their status.
 */
@Service
public class AnalyticsService {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Computes an audit of book counts grouped by {@link com.library.libraryprod.enums.BookStatus}.
     *
     * @return a map where the key is the BookStatus and the value is the number of books in that status
     */
    public Map<BookStatus, Long> auditCountsByStatus() {
        return bookRepository.findAll().stream()
                .collect(Collectors.groupingBy(Book::getBookStatus, Collectors.counting()));
    }
}