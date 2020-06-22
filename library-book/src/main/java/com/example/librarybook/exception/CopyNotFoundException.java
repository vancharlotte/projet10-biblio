package com.example.librarybook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CopyNotFoundException extends RuntimeException {

    public CopyNotFoundException(String message) {
        super(message);
    }
}
