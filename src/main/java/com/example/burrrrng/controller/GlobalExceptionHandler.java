package com.example.burrrrng.controller;

import com.example.burrrrng.exception.StoreLimitException;
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
        Map<String, Object> err = new HashMap<>();
        err.put("status", HttpStatus.BAD_REQUEST.value());
        err.put("error", HttpStatus.BAD_REQUEST);
        err.put("message", "check your data type");
        err.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidException(MethodArgumentNotValidException ex) {
        Map<String, Object> err = new HashMap<>();
        err.put("status", HttpStatus.BAD_REQUEST.value());
        err.put("error", HttpStatus.BAD_REQUEST);
        err.put("message", Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage());
        err.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(err, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedException(UnauthorizedException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", HttpStatus.UNAUTHORIZED.value());
        errorDetails.put("error", HttpStatus.UNAUTHORIZED);
        errorDetails.put("message", "관리자만 작성 가능합니다.");
        errorDetails.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(errorDetails, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(StoreLimitException.class)
    public ResponseEntity<Map<String, Object>> handleStoreLimitExceededException(StoreLimitException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", HttpStatus.BAD_REQUEST.value());
        errorDetails.put("error", HttpStatus.BAD_REQUEST);
        errorDetails.put("message", "폐업 상태가 아닌 가게를 3개 이상 운영할 수 없습니다.");
        errorDetails.put("timestamp", System.currentTimeMillis());

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

}
