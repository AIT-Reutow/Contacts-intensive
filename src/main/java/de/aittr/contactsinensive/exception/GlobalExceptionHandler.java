package de.aittr.contactsinensive.exception;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFound(ApiException ex) {
        return buildErrorResponse(ex.httpStatus, ExceptionUtils.getMessage(ex));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ExceptionUtils.getMessage(ex));
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionResponse> handleBadRequest(BadRequestException ex) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ExceptionUtils.getMessage(ex));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGenericException(Exception ex) {
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred.");
    }

    private ResponseEntity<ExceptionResponse> buildErrorResponse(HttpStatus status, String message) {
        ExceptionResponse exceptionResponse = new ExceptionResponse(
                message,
                status.getReasonPhrase(),
                status.value(),
                LocalDateTime.now());

        return new ResponseEntity<>(exceptionResponse, status);
    }
}
