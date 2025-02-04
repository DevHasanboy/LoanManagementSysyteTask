package com.example.file.task.exception;

import com.example.file.task.dto.ErrorDto;
import com.example.file.task.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(value = {ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
        return makeExceptionResponse(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(UserNotFoundException ex) {
        return makeExceptionResponse(ex, HttpStatus.NOT_FOUND);
    }

    private ResponseEntity<Object> makeExceptionResponse(Exception ex, HttpStatus status) {
        return new ResponseEntity<>(
                new ApiException(
                        ex.getMessage(),
                        status,
                        LocalDateTime.now()),
                status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Void>> methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest()
                .body(
                        ApiResponse.<Void>builder()
                                .message(String.valueOf(HttpStatus.BAD_REQUEST))
                                .message("Validation Failed")
                                .errorsList(
                                        ex.getBindingResult().getFieldErrors().stream().map(fieldError -> {
                                            String field = fieldError.getField();
                                            String rejectionValue = String.valueOf(fieldError.getRejectedValue());
                                            String defaultMessage = fieldError.getDefaultMessage();
                                            return new ErrorDto(String.format("%s, %s", defaultMessage, rejectionValue), field);
                                        }).toList()
                                )
                                .build()
                );
    }

}
