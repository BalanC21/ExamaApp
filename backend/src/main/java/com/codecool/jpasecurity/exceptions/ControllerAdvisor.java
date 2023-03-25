package com.codecool.jpasecurity.exceptions;

import org.springframework.dao.DataIntegrityViolationException;
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
    public ResponseEntity<Map<String, Object>> taskNotFoundException(ExpenseNotFoundException exception) {
        return getObjectResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundCustomException.class)
    public ResponseEntity<Map<String, Object>> taskNotFoundException(UsernameNotFoundCustomException usernameNotFoundCustomException) {
        return getObjectResponseEntity(usernameNotFoundCustomException.getMessage());
    }

    @ExceptionHandler(RevenueNotFoundException.class)
    public ResponseEntity<Map<String, Object>> revenueNotFoundException(RevenueNotFoundException exception) {
        return getObjectResponseEntity(exception.getMessage());
    }

    @ExceptionHandler(AuthenticationCustomException.class)
    public ResponseEntity<Map<String, Object>> authenticationCustomException(AuthenticationCustomException authenticationCustomException) {
        return getObjectResponseEntity(authenticationCustomException.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, Object> body = new HashMap<>();

        body.put("valid", false);
        methodArgumentNotValidException
                .getBindingResult()
                .getFieldErrors()
                .forEach(error -> body.put(error.getField(), error.getField() + " field " + error.getDefaultMessage()));
        body.put("status", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> httpMessageNotReadableException(HttpMessageNotReadableException httpMessageNotReadableException) {
        return getObjectResponseEntity(httpMessageNotReadableException.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> dataIntegrityViolationException(DataIntegrityViolationException dataIntegrityViolationException) {
        Map<String, Object> body = new HashMap<>();

        body.put("valid", false);
        body.put("Timestamp", LocalDateTime.now());
        body.put("Most Specific Cause Message", dataIntegrityViolationException.getMostSpecificCause().getMessage());
        body.put("status", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Map<String, Object>> getObjectResponseEntity(String message) {
        Map<String, Object> body = new LinkedHashMap<>();

        body.put("valid", false);
        body.put("Timestamp", LocalDateTime.now());
        body.put("Exception Message", message);
        body.put("status", HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}