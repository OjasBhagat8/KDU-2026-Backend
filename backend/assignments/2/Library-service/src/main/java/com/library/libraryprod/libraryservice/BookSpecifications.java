package com.library.libraryprod.libraryservice;

import com.library.libraryprod.entities.Book;
import com.library.libraryprod.enums.BookStatus;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specification helpers for dynamically filtering {@link com.library.libraryprod.entities.Book} queries.
 * Each method returns null when the filter is not provided to allow composition without applying that predicate.
 */
public class BookSpecifications {

    /**
     * Filters by exact {@link com.library.libraryprod.enums.BookStatus}.
     *
     * @param bookStatus the status to match; if null, the predicate is not applied
     * @return a Specification for the status, or null if bookStatus is null
     */
    public static Specification<Book> hasStatus(BookStatus bookStatus) {
        return (root, query, cb) ->
                bookStatus == null ? null : cb.equal(root.get("bookStatus"), bookStatus);
    }

    /**
     * Filters by title containing the given text, case-insensitive.
     *
     * @param title the substring to search for; ignored if null or blank
     * @return a Specification for the title predicate, or null if title is null/blank
     */
    public static Specification<Book> titleContains(String title) {
        return (root, query, cb) ->
                (title == null || title.isBlank()) ? null : cb.like(cb.lower(root.get("title")),
                        "%" + title.toLowerCase() + "%"
                );
    }

}
