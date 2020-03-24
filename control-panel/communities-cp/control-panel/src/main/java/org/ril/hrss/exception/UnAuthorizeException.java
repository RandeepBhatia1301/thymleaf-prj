package org.ril.hrss.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
public class UnAuthorizeException extends RuntimeException {

    public UnAuthorizeException(String exception) {
        super(exception);
    }
}
