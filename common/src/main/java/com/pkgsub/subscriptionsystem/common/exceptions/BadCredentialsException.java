package com.pkgsub.subscriptionsystem.common.exceptions;

import org.springframework.http.HttpStatus;

public class BadCredentialsException extends BaseException {
    public BadCredentialsException(HttpStatus status) {
        super(status);
    }

    public BadCredentialsException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public BadCredentialsException(Integer code, String message) {
        super(code, message);
    }

    public BadCredentialsException(String message) {
        super(message);
    }

    public BadCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
