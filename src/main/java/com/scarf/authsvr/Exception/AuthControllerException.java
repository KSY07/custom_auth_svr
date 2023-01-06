package com.scarf.authsvr.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class AuthControllerException {
    public AuthControllerException() {
        super(String.format(null, null, null);
    }
}
