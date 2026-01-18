package com.example.weekndhomework.repository;

import com.example.weekndhomework.model.Book;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class BookRepository {
    Map<UUID, Book> bookStorage = new HashMap<>();

    public void save(Book book) {
        bookStorage.put(book.getId(), book);
    }

    public List<Book> getBooks(){
        return bookStorage.values().stream().toList();
    }

    public Book findById(UUID id) {
        return bookStorage.get(id);
    }
}
