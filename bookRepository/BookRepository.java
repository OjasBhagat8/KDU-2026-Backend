package com.example.bookapi.book_api.bookRepository;


import com.example.bookapi.book_api.Model.Book;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
@org.springframework.stereotype.Repository
public class BookRepository{
    private final Map<Long , Book> storage = new HashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public Book saveBook(Book book){
        if(book.getId() == null){
            book.setId(idGenerator.getAndIncrement());
        }
        storage.put(book.getId(),book);
        return book;
    }

    public List<Book> findAll(){
        return new ArrayList<>(storage.values());
    }
    public Optional<Book>findById(Long id){
        return Optional.ofNullable(storage.get(id));
    }
    public void deleteById(Long id){
        storage.remove(id);
    }
    public boolean checkBookExists(String title , String author){
        String lowerTitle = title.trim().toLowerCase();
        String lowerAuthor = author.trim().toLowerCase();

        return storage.values().stream().anyMatch(book ->
                book.getTitle().trim().toLowerCase().equals(lowerTitle)
                        && book.getAuthor().trim().toLowerCase().equals(lowerAuthor));
    }
}
