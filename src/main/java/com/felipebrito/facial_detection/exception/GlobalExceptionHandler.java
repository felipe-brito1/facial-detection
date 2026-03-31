package com.felipebrito.facial_detection.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(RuntimeException.class)
    ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException exception){
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse message = new ErrorResponse(status, exception.getMessage());
        return ResponseEntity.status(status).body(message);

    }


}
