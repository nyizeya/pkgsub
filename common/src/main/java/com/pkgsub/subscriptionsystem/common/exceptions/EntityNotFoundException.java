package com.pkgsub.subscriptionsystem.common.exceptions;

import org.springframework.http.HttpStatus;

public class EntityNotFoundException extends BaseException {
    public EntityNotFoundException(HttpStatus status) {
        super(status);
    }

    public EntityNotFoundException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public EntityNotFoundException(Integer code, String message) {
        super(code, message);
    }

    public EntityNotFoundException(String message) {
        super(message);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
