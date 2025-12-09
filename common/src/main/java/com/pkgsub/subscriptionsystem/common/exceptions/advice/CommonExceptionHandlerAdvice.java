package com.pkgsub.subscriptionsystem.common.exceptions.advice;

import com.pkgsub.subscriptionsystem.common.dto.response.ErrorResponse;
import com.pkgsub.subscriptionsystem.common.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CommonExceptionHandlerAdvice {
    @ExceptionHandler(value = UsernameNotFoundException.class)
    public ResponseEntity<ErrorResponse> userNameNotFoundException(UsernameNotFoundException e) {
        log.error("UsernameNotFoundException => {}", e.getMessage());
        return buildResponseEntity(ErrorResponse.of(HttpStatus.NOT_FOUND, e));
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> dataIntegrityViolationException(DataIntegrityViolationException e) {
        log.error("DataIntegrityViolationException => {}", e.getMessage());
        return buildResponseEntity(ErrorResponse.ofException(e));
    }

    @ExceptionHandler(value = DuplicatePackageNameException.class)
    public ResponseEntity<ErrorResponse> duplicatePackageNameException(DuplicatePackageNameException e) {
        log.error("DuplicatePackageNameException => {}", e.getMessage());
        return buildResponseEntity(ErrorResponse.ofException(HttpStatus.BAD_REQUEST, e));
    }

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> illegalArgumentException(IllegalArgumentException e) {
        log.error("IllegalArgumentException => {}", e.getMessage());
        return buildResponseEntity(ErrorResponse.ofException(e));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> exception(Exception e) {
        log.error("UnExpectedException => {}", e.getMessage());
        return buildResponseEntity(ErrorResponse.ofException(e));
    }

    @ExceptionHandler(value = DatabaseException.class)
    public ResponseEntity<ErrorResponse> databaseException(DatabaseException e) {
        log.error("DatabaseException => {}", e.getMessage());
        return buildResponseEntity(ErrorResponse.ofException(e));
    }

    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> entityNotFoundException(EntityNotFoundException e) {
        log.error("EntityNotFoundException => {}", e.getMessage());
        return buildResponseEntity(ErrorResponse.ofException(HttpStatus.NOT_FOUND, e));
    }

    @ExceptionHandler(value = RefundException.class)
    public ResponseEntity<ErrorResponse> refundException(RefundException e) {
        log.error("RefundException => {}", e.getMessage());
        return buildResponseEntity(ErrorResponse.ofException(HttpStatus.valueOf(e.getCode()), e));
    }

    @ExceptionHandler(value = SubscriptionException.class)
    public ResponseEntity<ErrorResponse> subscriptionException(SubscriptionException e) {
        log.error("SubscriptionException => {}", e.getMessage());
        return buildResponseEntity(ErrorResponse.ofException(HttpStatus.valueOf(e.getCode()), e));
    }

    @ExceptionHandler(value = TransactionValidationException.class)
    public ResponseEntity<ErrorResponse> transactionValidationException(TransactionValidationException e) {
        log.error("TransactionValidationException => {}", e.getMessage());
        return buildResponseEntity(ErrorResponse.ofException(HttpStatus.valueOf(e.getCode()), e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = ErrorResponse.ofStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage("Validation failed. Please review the errors and try again.");
        errorResponse.addFieldValidationErrors(e.getBindingResult().getFieldErrors());
        errorResponse.addGlobalValidationErrors(e.getBindingResult().getGlobalErrors());
        return buildResponseEntity(errorResponse);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }
}