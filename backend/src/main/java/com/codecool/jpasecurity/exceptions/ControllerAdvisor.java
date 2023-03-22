package com.codecool.jpasecurity.exceptions;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(ExpenseNotFoundException.class)
    public ResponseEntity<Object> taskNotFoundException(ExpenseNotFoundException exception) {
        return getObjectResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(RevenueNotFoundException.class)
    public ResponseEntity<Object> revenueNotFoundException(RevenueNotFoundException exception) {
        return getObjectResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public Map<String, Object> constraintViolationException(ConstraintViolationException constraintViolationException) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("valid", false);
        body.put("Timestamp", LocalDateTime.now());
        body.put("Exception Message", constraintViolationException.getMessage());

        return body;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, Object> body = new HashMap<>();

        body.put("valid", false);
        methodArgumentNotValidException
                .getBindingResult()
                .getFieldErrors()
                .forEach(error -> body.put(error.getField(), error.getDefaultMessage()));
        body.put("status", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> httpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        Map<String, Object> body = new HashMap<>();

        body.put("valid", false);
        body.put("Timestamp", LocalDateTime.now());
        body.put("Exception Message", httpMessageNotReadableException.getMessage());
        body.put("status", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> getObjectResponseEntity(String message) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("valid", false);
        body.put("Timestamp", LocalDateTime.now());
        body.put("Exception Message", message);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}