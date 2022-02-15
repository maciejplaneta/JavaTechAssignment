package com.example.javatechassignment.domain.exceptions;

public class ReplacingFileException extends RuntimeException {
    public ReplacingFileException(Throwable cause) {
        super(cause);
    }

    public ReplacingFileException(String message) {
        super(message);
    }
}
