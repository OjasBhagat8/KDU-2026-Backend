package com.library.libraryprod.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(
        name = "loans",
        indexes = {
                @Index(name = "idx_loans_books",columnList = "book_id")
        }
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Loan {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id",nullable = false)
    private Book book;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name = "borrower_id",nullable = false)
    private User borrower;

    @CreatedDate
    @Column(nullable = false)
    private Instant borrowedAt;

    @Column
    private Instant returnedAt;
}