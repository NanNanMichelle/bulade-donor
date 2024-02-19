package com.bulade.donor.common.exception;

import com.bulade.donor.common.enums.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class SystemException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -859334348495077026L;

    private final Integer code;

    public SystemException(String message) {
        super(message);
        this.code = ResultCodeEnum.FAILED.getCode();
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResultCodeEnum.FAILED.getCode();
    }

}
