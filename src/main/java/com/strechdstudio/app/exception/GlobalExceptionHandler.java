package com.strechdstudio.app.exception;

import com.strechdstudio.app.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Void>> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiResponse<Void>> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(ex.getMessage(), HttpStatus.NOT_FOUND.value(), null));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(ex.getMessage(), HttpStatus.BAD_REQUEST.value(), null));
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse<Void>> handleAccessDeniedException(AccessDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new ApiResponse<>(ex.getMessage(), HttpStatus.FORBIDDEN.value(), null));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Void>> handleGenericException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(ex.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(), null));
    }
}
