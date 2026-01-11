package com.example.weekndhomework.service;

import com.example.weekndhomework.DTO.BookResponseDTO;
import com.example.weekndhomework.constants.BookStatus;
import com.example.weekndhomework.model.Book;
import com.example.weekndhomework.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ExecutorService executor = Executors.newFixedThreadPool(3);


    // Add a new book and start async processing to change book's status from PROCESSING to AVAILABLE
    public BookResponseDTO addBook(String title ) {

        String normalizedTitle = title.trim().toLowerCase();

        Book existing = bookRepository.getBooks().stream()
                .filter(book -> book.getTitle().trim().toLowerCase().equals(normalizedTitle))
                .findFirst()
                .orElse(null);

        if(existing != null){
            return new BookResponseDTO(existing.getTitle(), existing.getStatus().name());
        }

        Book book = new Book(
                java.util.UUID.randomUUID(),
                title,
                BookStatus.PROCESSING

        );

        bookRepository.save(book);

        // Simulate async processing and coverts book status from processing to available after 3 seconds
        executor.submit(()->{
           try{
               Thread.sleep(3000);
               Book existingBook = bookRepository.findById(book.getId());
               if(existingBook != null){
                   Book updatedBook = new Book(
                           existingBook.getId(),
                           existingBook.getTitle(),
                           BookStatus.AVAILABLE
                   );
                   bookRepository.save(updatedBook);
               }
           } catch(InterruptedException e){
               throw new RuntimeException(e);

            }
        });
        return new BookResponseDTO(book.getTitle(), book.getStatus().name());
    }

    // Get all books
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.getBooks().stream().map(book -> new BookResponseDTO(
                book.getTitle(),
                book.getStatus().name()
        )).toList();

    }
    // Get library audit gives a map of book status and their counts
    public Map<String , Long> getLibraryAudit(){
        return bookRepository.getBooks().stream().collect(Collectors.groupingBy(
                book -> book.getStatus().name(),
                Collectors.counting()
        ));
    }


}
