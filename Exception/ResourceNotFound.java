package com.example.bookapi.book_api.Exception;

public class ResourceNotFound extends RuntimeException{

    public ResourceNotFound(Long id){
        super("Book not found with id:" +id);
    }
}
