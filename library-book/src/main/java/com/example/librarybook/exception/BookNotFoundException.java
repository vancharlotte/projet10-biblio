package com.example.librarybook.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "book not found")
public class BookNotFoundException extends RuntimeException {

    private Logger logger = LoggerFactory.getLogger(BookNotFoundException.class);


    public BookNotFoundException(String message) {
        super(message);
        logger.error("error 404 : book not found");

    }
}
