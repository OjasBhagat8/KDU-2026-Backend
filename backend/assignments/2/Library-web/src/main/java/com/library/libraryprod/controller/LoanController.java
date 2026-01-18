package com.library.libraryprod.controller;

import com.library.libraryprod.entities.Loan;
import com.library.libraryprod.libraryservice.LoanService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/loans/v1")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @Operation(
            summary = "Borrow a book from library",
            description = "Borrows a book and marks it as checked out."
    )
    @PostMapping("/{bookId}/borrow/{userId}")
    public ResponseEntity<Loan> addBorrowLoan(@PathVariable("userId") UUID userId, @PathVariable("bookId") UUID bookId){
        return new ResponseEntity<>(loanService.addLoanBorrower(userId,bookId), HttpStatus.CREATED);
    }
    @Operation(
            summary = "Return a book to library",
            description = "Returns a book and marks it as available."
    )
    @PostMapping("/{bookId}/return/{userId}")
    public ResponseEntity<Loan> returnLoan(@PathVariable("userId") UUID userId, @PathVariable("bookId") UUID bookId){
        return new ResponseEntity<>(loanService.addLoanReturn(userId,bookId),HttpStatus.CREATED);
    }
}
