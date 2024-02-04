package com.bulade.donor.common.exception;

import com.bulade.donor.common.enums.ResultCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = -6328534022168802630L;

    private final Integer code;

    public BusinessException(String message) {
        super(message);
        this.code = ResultCodeEnum.FAILED.getCode();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = ResultCodeEnum.FAILED.getCode();
    }

    public static BusinessException ofLogin(ResultCodeEnum codeEnum) {
        return new BusinessException(codeEnum.getMessage());
    }

    public static BusinessException of(ResultCodeEnum codeEnum) {
        return new BusinessException(codeEnum.getMessage());
    }

}
