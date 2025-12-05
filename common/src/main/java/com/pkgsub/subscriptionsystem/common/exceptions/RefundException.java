package com.pkgsub.subscriptionsystem.common.exceptions;

import org.springframework.http.HttpStatus;

public class RefundException extends BaseException{
    public RefundException(HttpStatus status) {
        super(status);
    }

    public RefundException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public RefundException(Integer code, String message) {
        super(code, message);
    }

    public RefundException(String message) {
        super(message);
    }

    public RefundException(String message, Throwable cause) {
        super(message, cause);
    }
}
