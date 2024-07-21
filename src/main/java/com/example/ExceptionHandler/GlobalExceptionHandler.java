package com.example.ExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorObject> handleNotFoundException(EntityNotFoundException ex) {
        System.out.println("GlobalExceptionHandler: EntityNotFoundException handled - " + ex.getMessage());
        ErrorObject error = new ErrorObject(HttpStatus.NOT_FOUND.value(), ex.getMessage(), new Date());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorObject>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        List<ErrorObject> raisedErrorsList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            raisedErrorsList.add(new ErrorObject(HttpStatus.BAD_REQUEST.value(), error.getDefaultMessage() , new Date()));
        });
        return new ResponseEntity<>(raisedErrorsList, HttpStatus.BAD_REQUEST);
    }
}
