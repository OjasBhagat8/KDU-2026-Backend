package com.library.libraryprod.libraryservice;

import com.library.libraryprod.entities.Book;
import com.library.libraryprod.entities.Loan;
import com.library.libraryprod.enums.BookStatus;
import com.library.libraryprod.entities.User;
import com.library.libraryprod.exceptions.BookNotAvailableException;
import com.library.libraryprod.exceptions.ResourceNotFoundException;
import com.library.libraryprod.repository.BookRepository;
import com.library.libraryprod.repository.LoanRepository;
import com.library.libraryprod.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service for managing loan operations (borrow and return) and related analytics.
 * Ensures valid book state transitions between AVAILABLE and CHECKED_OUT.
 */
@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Records a borrow action for the given user and book.
     * Transitions the book from AVAILABLE to CHECKED_OUT.
     *
     * @param userId the borrower ID
     * @param bookId the book ID
     * @return the persisted loan record
     * @throws ResourceNotFoundException if the user or book does not exist
     * @throws com.library.libraryprod.exceptions.BookNotAvailableException if the book is not AVAILABLE
     */
    @Transactional
    public Loan addLoanBorrower(UUID userId, UUID bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book with id "+bookId+ " was not found"));
        if(book.getBookStatus() == BookStatus.AVAILABLE){
            book.setBookStatus(BookStatus.CHECKED_OUT);
        }else{
            throw new BookNotAvailableException("Book Not Available");
        }
        bookRepository.save(book);
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with id " +userId + " was not found"));
        Loan loan = Loan.builder().book(book).borrower(user).build();
        return loanRepository.save(loan);
    }

    /**
     * Records a return action for the given user and book.
     * Transitions the book from CHECKED_OUT back to AVAILABLE and stamps returnedAt.
     *
     * @param userId the borrower ID
     * @param bookId the book ID
     * @return the persisted loan record
     * @throws ResourceNotFoundException if the user or book does not exist
     * @throws com.library.libraryprod.exceptions.BookNotAvailableException if the book is not CHECKED_OUT
     */
    @Transactional
    public Loan addLoanReturn(UUID userId,UUID bookId){
        Book book = bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book with id "+bookId+ " was not found"));
        if(book.getBookStatus() == BookStatus.CHECKED_OUT){
            book.setBookStatus(BookStatus.AVAILABLE);

        }else{
            throw new BookNotAvailableException("Book Not Available");
        }
        bookRepository.save(book);
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with id " +userId + " was not found"));
        Loan loan = Loan.builder().book(book).borrower(user).returnedAt(Instant.now()).build();
        return loanRepository.save(loan);
    }

    /**
     * Aggregates counts of books by {@link com.library.libraryprod.enums.BookStatus}.
     *
     * @return a map of BookStatus to count of books
     */
    public Map<BookStatus, Long> getAnalytics() {
        List<Book> books = bookRepository.findAll();
        return books.stream().collect(Collectors.groupingBy(Book::getBookStatus,Collectors.counting()));
    }
}
