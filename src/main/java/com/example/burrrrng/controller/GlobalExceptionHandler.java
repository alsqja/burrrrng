package com.example.burrrrng.controller;

import com.example.burrrrng.exception.MenuNotFoundException;
import com.example.burrrrng.exception.NameAndPriceException;
import com.example.burrrrng.exception.SameMenuException;
import com.example.burrrrng.exception.StoreLimitException;
import com.example.burrrrng.exception.StoreNotFoundException;
import com.example.burrrrng.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {

        Map<String, Object> errorDetails = new HashMap<>();

        errorDetails.put("status", ex.getStatusCode().value());
        errorDetails.put("error", ex.getStatusCode());
        errorDetails.put("message", ex.getReason());
        errorDetails.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(errorDetails, ex.getStatusCode());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Map<String, Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {

        Map<String, Object> err = getStringObjectMap(HttpStatus.BAD_REQUEST, "check your data type");

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidException(MethodArgumentNotValidException ex) {

        Map<String, Object> err = getStringObjectMap(HttpStatus.BAD_REQUEST, Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(UnauthorizedException ex) {

        Map<String, Object> errorDetails = getStringObjectMap(HttpStatus.UNAUTHORIZED, ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(StoreLimitException.class)
    public ResponseEntity<Map<String, Object>> handleStoreLimitExceededException(StoreLimitException ex) {

        Map<String, Object> err = getStringObjectMap(HttpStatus.BAD_REQUEST, ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(SameMenuException.class)
    public ResponseEntity<Map<String, Object>> handleSameMenuException(SameMenuException ex) {

        Map<String, Object> err = getStringObjectMap(HttpStatus.BAD_REQUEST, ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NameAndPriceException.class)
    public ResponseEntity<Map<String, Object>> handleSameMenuException(NameAndPriceException ex) {

        Map<String, Object> err = getStringObjectMap(HttpStatus.BAD_REQUEST, ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(StoreNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStoreNotFoundException(StoreNotFoundException ex) {
        Map<String, Object> errorDetails = getStringObjectMap(HttpStatus.NOT_FOUND, ex.getMessage());

        return new ResponseEntity<>(errorDetails, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MenuNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleMenuNotFoundException(MenuNotFoundException ex) {
        Map<String, Object> err = getStringObjectMap(HttpStatus.NOT_FOUND, ex.getMessage());

        return new ResponseEntity<>(err, HttpStatus.NOT_FOUND);
    }

    private static Map<String, Object> getStringObjectMap(HttpStatus status, String ex) {
        Map<String, Object> err = new HashMap<>();

        err.put("status", status.value());
        err.put("error", status);
        err.put("message", ex);
        err.put("timestamp", System.currentTimeMillis());
        return err;
    }

}
