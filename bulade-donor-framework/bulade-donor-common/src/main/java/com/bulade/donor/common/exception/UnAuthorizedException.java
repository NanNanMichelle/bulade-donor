package com.bulade.donor.common.exception;

import lombok.Data;

@Data
public class UnAuthorizedException extends RuntimeException {

    private int code;

    public UnAuthorizedException() {
        super("unauthorized");
        this.code = 401;
    }

    public UnAuthorizedException(String message) {
        super(message);
        this.code = 401;
    }

}
