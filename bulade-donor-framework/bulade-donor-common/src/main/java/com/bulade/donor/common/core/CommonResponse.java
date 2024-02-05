package com.bulade.donor.common.core;

import com.bulade.donor.common.enums.ResultCodeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;

@Getter
@ToString
@NoArgsConstructor
public class CommonResponse<T> {

    private static final String SUCCESS_MESSAGE = "操作成功";

    private static final String FAILED_MESSAGE = "操作失败";

    private boolean success;

    private int code;

    private String message;

    private T data;

    private CommonResponse(ResultCodeEnum resultCode, String message, T data) {
        this.success = ResultCodeEnum.SUCCESS == resultCode;
        this.code = resultCode.getCode();
        this.message = message;
        this.data = data;
    }

    private CommonResponse(ResultCodeEnum resultCode, String message) {
        this.success = ResultCodeEnum.SUCCESS == resultCode;
        this.code = resultCode.getCode();
        this.message = message;
    }

    public static <T> CommonResponse<T> data(T object) {
        return new CommonResponse<>(ResultCodeEnum.SUCCESS, SUCCESS_MESSAGE, object);
    }

    public static <T> CommonResponse<T> success() {
        return new CommonResponse<>(ResultCodeEnum.SUCCESS, SUCCESS_MESSAGE);
    }

    public static <T> CommonResponse<T> success(String message) {
        return new CommonResponse<>(ResultCodeEnum.SUCCESS, message);
    }

    public static <T> CommonResponse<T> error(ResultCodeEnum resultCode) {
        return new CommonResponse<>(resultCode, resultCode.getMessage());
    }

    public static <T> CommonResponse<T> error(String message) {
        return new CommonResponse<>(ResultCodeEnum.ERROR, message);
    }

    public static <T> CommonResponse<T> error(ResultCodeEnum resultCode, String message) {
        return new CommonResponse<>(resultCode, message);
    }

    public static <T> CommonResponse<T> error(ResultCodeEnum resultCodeEnum, T object) {
        return new CommonResponse<>(resultCodeEnum, FAILED_MESSAGE, object);
    }

    public static CommonResponse<List<String>> error(ResultCodeEnum resultCode, Exception exception, String message) {
        var stacks = Arrays.stream(exception.getStackTrace()).map(StackTraceElement::toString).toList();
        return new CommonResponse<>(resultCode, message, stacks);
    }

    public static <T> CommonResponse<T> result(boolean flag) {
        return flag ? success(SUCCESS_MESSAGE) : error(FAILED_MESSAGE);
    }

}
