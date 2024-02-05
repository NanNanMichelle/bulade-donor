package com.bulade.donor.common.exception;

public class DecryptException extends RuntimeException {

    public DecryptException(String message, Exception e) {
        super("Could not decrypt <" + message + ">", e);
    }

}
