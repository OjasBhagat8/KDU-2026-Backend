package com.library.libraryprod.repository;

import com.library.libraryprod.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> , JpaSpecificationExecutor<Book> {

}
