package com.anwar.ecommerce_api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ✅ Handle resource not found
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    // ✅ Handle wrong password / email
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException ex) {
        return buildResponse("Invalid email or password!", HttpStatus.UNAUTHORIZED);
    }

    // ✅ Handle access denied (wrong role)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        return buildResponse("You do not have permission!", HttpStatus.FORBIDDEN);
    }

    // ✅ Handle all other runtime errors
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    // ✅ Handle any other exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        return buildResponse("Something went wrong!", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Helper method to build error response
    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status", status.value());
        error.put("error", message);
        return ResponseEntity.status(status).body(error);
    }
}