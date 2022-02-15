package com.example.javatechassignment.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@ControllerAdvice
class ExceptionsHandler {

    @ExceptionHandler(UnsupportedExtensionException.class)
    ResponseEntity<Error> handleUnsupportedFormatException(UnsupportedExtensionException ex) {
        Error error = new Error(HttpStatus.UNSUPPORTED_MEDIA_TYPE, ex.getMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @ExceptionHandler(ReplacingFileException.class)
    ResponseEntity<Error> handleReplacingFileException(ReplacingFileException ex) {
        Error error = new Error(HttpStatus.NOT_ACCEPTABLE, ex.getMessage());
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @Data
    @RequiredArgsConstructor
    private static class Error {
        private final HttpStatus httpStatus;
        private final String message;
    }
}
