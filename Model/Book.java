package com.example.bookapi.book_api.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Book {

    private Long id;

    @NotBlank(message = "Title cannot be Blank")
    @Size(max = 200 , message = "Title must be less than 200 characters")
    private  String title;

    @NotBlank(message = "Author name Cannot be blank")
    @Size(max = 120 , message = "Author name must be less than 120 characters")
    private String author;
}
