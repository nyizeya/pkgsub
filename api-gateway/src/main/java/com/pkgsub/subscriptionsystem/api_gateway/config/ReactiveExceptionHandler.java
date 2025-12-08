package com.pkgsub.subscriptionsystem.api_gateway.config;

import com.pkgsub.subscriptionsystem.common.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@Slf4j
@RestControllerAdvice
public class ReactiveExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<ErrorResponse> handleWebExchangeBindException(WebExchangeBindException e) {
        log.error("WebExchangeBindException => {}", e.getMessage());
        ErrorResponse errorResponse = ErrorResponse.ofStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setMessage("Validation failed for request arguments. Please review the errors and try again.");
        errorResponse.addFieldValidationErrors(e.getBindingResult().getFieldErrors());
        errorResponse.addGlobalValidationErrors(e.getBindingResult().getGlobalErrors());
        return buildResponseEntity(errorResponse);
    }

    private ResponseEntity<ErrorResponse> buildResponseEntity(ErrorResponse errorResponse) {
        return new ResponseEntity<>(errorResponse, HttpStatus.valueOf(errorResponse.getStatus()));
    }

}
