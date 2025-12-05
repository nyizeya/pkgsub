package com.pkgsub.subscriptionsystem.common.exceptions;

import org.springframework.http.HttpStatus;

public class UsernameNotFoundException extends BaseException {
    public UsernameNotFoundException(HttpStatus status) {
        super(status);
    }

    public UsernameNotFoundException(HttpStatus httpStatus, String message) {
        super(httpStatus, message);
    }

    public UsernameNotFoundException(Integer code, String message) {
        super(code, message);
    }

    public UsernameNotFoundException(String message) {
        super(message);
    }
}