package com.example.project.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionController {
    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void handleNotFoundException(NotFoundException e) {
        log.error("Resource not found", e);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void handleException(Exception e) {
        log.error("Bad request", e);
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public void handleException(Throwable e) {
        log.error("Internal server error", e);
    }
}
