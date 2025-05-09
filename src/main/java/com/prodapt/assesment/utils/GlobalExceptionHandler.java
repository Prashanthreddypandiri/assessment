//package com.prodapt.assesment.utils;
//
//import java.time.ZonedDateTime;
//import java.util.HashMap;
//import java.util.Map;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.server.ResponseStatusException;
//
//import jakarta.servlet.http.HttpServletRequest;
//
//@ControllerAdvice
//public class GlobalExceptionHandler {
//
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex, HttpServletRequest request) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors().forEach(error -> {
//            errors.put(error.getField(), error.getDefaultMessage());
//        });
//
//        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Validation Failed", errors, request.getRequestURI());
//    }
//
//    @ExceptionHandler(ResponseStatusException.class)
//    public ResponseEntity<ErrorResponse> handleResponseStatusException(ResponseStatusException ex, HttpServletRequest request) {
//        Map<String, String> errorMessage = new HashMap<>();
//        errorMessage.put("error", ex.getReason());
//
//        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Error Occurred", errorMessage, request.getRequestURI());
//    }
//
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex, HttpServletRequest request) {
//        Map<String, String> errorMessage = new HashMap<>();
//        errorMessage.put("error", ex.getMessage());
//
//        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", errorMessage, request.getRequestURI());
//    }
//
//    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String error, Map<String, String> message, String path) {
//        ErrorResponse response = new ErrorResponse(ZonedDateTime.now(), status.value(), error, message, path);
//        return new ResponseEntity<>(response, status);
//    }
//}