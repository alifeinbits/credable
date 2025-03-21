package com.credable.lms.exception;

import com.credable.lms.model.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(LoanInProgressException.class)
    public ResponseEntity<ErrorResponse> handleLoanInProgressException(LoanInProgressException ex, WebRequest request) {
        log.error("Loan in progress exception: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCustomerNotFoundException(CustomerNotFoundException ex, WebRequest request) {
        log.error("Customer not found: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(InvalidLoanRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidLoanRequestException(InvalidLoanRequestException ex, WebRequest request) {
        log.error("Invalid loan request: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(KycServiceException.class)
    public ResponseEntity<ErrorResponse> handleKycServiceException(KycServiceException ex, WebRequest request) {
        log.error("KYC service exception: {}", ex.getMessage(), ex);
        return buildErrorResponse("Error accessing KYC service", HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    @ExceptionHandler(TransactionServiceException.class)
    public ResponseEntity<ErrorResponse> handleTransactionServiceException(TransactionServiceException ex, WebRequest request) {
        log.error("Transaction service exception: {}", ex.getMessage(), ex);
        return buildErrorResponse("Error accessing Transaction service", HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    @ExceptionHandler(ScoringServiceException.class)
    public ResponseEntity<ErrorResponse> handleScoringServiceException(ScoringServiceException ex, WebRequest request) {
        log.error("Scoring service exception: {}", ex.getMessage(), ex);
        return buildErrorResponse("Error accessing Scoring service", HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    @ExceptionHandler(MaxRetriesExceededException.class)
    public ResponseEntity<ErrorResponse> handleMaxRetriesExceededException(MaxRetriesExceededException ex, WebRequest request) {
        log.error("Max retries exceeded: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.GATEWAY_TIMEOUT, request);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex, WebRequest request) {
        log.error("Business exception: {}", ex.getMessage());
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(IntegrationException.class)
    public ResponseEntity<ErrorResponse> handleIntegrationException(IntegrationException ex, WebRequest request) {
        log.error("Integration exception: {}", ex.getMessage(), ex);
        return buildErrorResponse("An error occurred with an external service", HttpStatus.SERVICE_UNAVAILABLE, request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex, WebRequest request) {
        log.error("Validation error: {}", ex.getMessage());

        // Extract validation errors
        Map<String, String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(
                        FieldError::getField,
                        fieldError -> fieldError.getDefaultMessage() != null ? fieldError.getDefaultMessage() : "Invalid value",
                        (error1, error2) -> error1
                ));

        String message = "Validation failed: " + errors;
        return buildErrorResponse(message, HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleAllUncaughtExceptions(Exception ex, WebRequest request) {
        log.error("Unexpected error occurred", ex);
        return buildErrorResponse(
                "An unexpected error occurred. Please try again later or contact support.",
                HttpStatus.INTERNAL_SERVER_ERROR,
                request
        );
    }

    private ResponseEntity<ErrorResponse> buildErrorResponse(String message, HttpStatus status, WebRequest request) {
        String path = ((ServletWebRequest) request).getRequest().getRequestURI();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .path(path)
                .build();

        return new ResponseEntity<>(errorResponse, status);
    }
}
