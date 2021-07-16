package com.example.clientui.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Exception decode(String methodKey, Response response) {
        String message;
        if (response.status() == 401) {
            message = "user is not authenticate";
        } else if (response.status() == 403) {
            message = "user is not authorized";
        } else if (response.status() == 404) {
            message = "resource not found";
        } else if (response.status() == 405) {
            message = "resource not found";
        } else {
            message = response.reason();
        }

        logger.error("Status code : " + response.status() + ", methodKey = " + methodKey + ", message = " + message);
        return new ResponseStatusException(HttpStatus.valueOf(response.status()), message);
    }
}
