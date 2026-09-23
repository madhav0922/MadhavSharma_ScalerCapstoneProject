package com.spendwise.common.exception;

import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.time.Instant;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    ResponseEntity<?> notFound(ResourceNotFoundException e) {
        return body(404, e.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    ResponseEntity<?> bad(BadRequestException e) {
        return body(400, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<?> validation(MethodArgumentNotValidException e) {
        Map<String, String> errors = new LinkedHashMap<>();
        e.getBindingResult().getFieldErrors().forEach(x -> errors.put(x.getField(), x.getDefaultMessage()));
        return ResponseEntity.badRequest().body(Map.of("timestamp", Instant.now(), "status", 400, "errors", errors));
    }

    private ResponseEntity<?> body(int status, String msg) {
        return ResponseEntity.status(status).body(Map.of("timestamp", Instant.now(), "status", status, "message", msg));
    }
}