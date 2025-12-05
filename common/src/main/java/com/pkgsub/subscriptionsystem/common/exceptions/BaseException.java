package com.pkgsub.subscriptionsystem.common.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private Integer code;
    private String message;

    public BaseException(HttpStatus status) {
        this.code = status.value();
        this.message = status.getReasonPhrase();
    }

    public BaseException(HttpStatus httpStatus, String message) {
        this.code = httpStatus.value();
        this.message = message;
    }


    public BaseException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BaseException(String message) {
        super(message);
        this.message = message;
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }
}