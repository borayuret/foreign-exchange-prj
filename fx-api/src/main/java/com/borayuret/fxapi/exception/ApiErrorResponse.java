package com.borayuret.fxapi.exception;

import java.time.LocalDateTime;

/**
 * A standardized error response returned by all exceptions in the API.
 */
public class ApiErrorResponse {

    private int status;
    private String error;
    private String message;
    private String path;
    private String code; // custom error code (like: ERR_INVALID_PARAMS)
    private LocalDateTime timestamp;

    public ApiErrorResponse() {}

    public ApiErrorResponse(int status, String error, String message, String path, String code) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.code = code;
        this.timestamp = LocalDateTime.now();
    }

    // --- Getters & Setters ---

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
