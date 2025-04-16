package com.borayuret.fxapi.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;

/**
 * Handles exceptions globally and returns standardized ApiErrorResponse objects.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles IllegalArgumentException from the service layer.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiErrorResponse> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), request.getRequestURI(), "ERR_INVALID_PARAMS");
    }

    /**
     * Handles JSON parsing errors.
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ApiErrorResponse> handleUnreadableJson(HttpMessageNotReadableException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, "Malformed or unreadable JSON payload.", request.getRequestURI(), "ERR_MALFORMED_JSON");
    }

    /**
     * Handles invalid query param types (e.g. invalid UUID format).
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        String message = "Invalid parameter format: " + ex.getName();
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message, request.getRequestURI(), "ERR_INVALID_TYPE");
    }

    /**
     * Catch-all handler for any unhandled exception.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiErrorResponse> handleGeneric(Exception ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.", request.getRequestURI(), "ERR_INTERNAL_SERVER");
    }

    /**
     * Helper to construct a consistent ApiErrorResponse.
     */
    private ResponseEntity<ApiErrorResponse> buildErrorResponse(HttpStatus status, String message, String path, String code) {
        ApiErrorResponse error = new ApiErrorResponse(
                status.value(),
                status.getReasonPhrase(),
                message,
                path,
                code
        );
        return ResponseEntity.status(status).body(error);
    }
}
