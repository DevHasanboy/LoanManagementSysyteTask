package com.example.file.task.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ApiException extends Throwable {
    private final String message;
    private final HttpStatus status;
    private final LocalDateTime timestamp;

    public ApiException(String message, HttpStatus status, LocalDateTime timestamp) {
        this.message = message;
        this.status = status;
        this.timestamp = timestamp;
    }

    public ApiException(HttpStatus httpStatus, String message) {
        this.message = message;
        this.status = httpStatus;
        this.timestamp = LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}

