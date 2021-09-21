package com.example.librarybook.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code=HttpStatus.NOT_FOUND, reason = "Copy Not Found")
public class CopyNotFoundException extends RuntimeException {

    private Logger logger = LoggerFactory.getLogger(CopyNotFoundException.class);

    public CopyNotFoundException(String message) {
        super(message);
        logger.error("error 404 : copy not found");

    }
}
