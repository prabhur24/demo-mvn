package com.example.demo_mvn.infrastructure.spring.advice;


import com.example.demo_mvn.application.dto.ExpenseDTO;
import com.example.demo_mvn.presentation.rest.ApiResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalControllerExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        // Handle unexpected exceptions
        ApiResponse<ExpenseDTO> errorResponse =
                new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiResponse> handleDataViolation(DataIntegrityViolationException e) {
        ApiResponse<ExpenseDTO> errorResponse =
                new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error: " + e.getMessage(), null);
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponse);
    }
}
