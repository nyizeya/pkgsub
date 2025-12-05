package com.pkgsub.subscriptionsystem.common.exceptions;

import org.springframework.http.HttpStatus;

public class TransactionValidationException extends BaseException{
    public TransactionValidationException(HttpStatus status) {
        super(status);
    }

    public TransactionValidationException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public TransactionValidationException(Integer code, String message) {
        super(code, message);
    }

    public TransactionValidationException(String message) {
        super(message);
    }

    public TransactionValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
