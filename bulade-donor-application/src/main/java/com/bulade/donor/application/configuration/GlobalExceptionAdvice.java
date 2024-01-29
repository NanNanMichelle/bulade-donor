package com.bulade.donor.application.configuration;

import com.bulade.donor.common.core.CommonResponse;
import com.bulade.donor.common.enums.ResultCodeEnum;
import com.bulade.donor.common.exception.BusinessException;
import com.bulade.donor.common.exception.SystemException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.List;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

    /**
     * 全局异常捕捉处理
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<List<String>> handlerException(Exception exception, HttpServletRequest request) {
        // todo 暂时先放开错误信息，前后端调试方便
        log.error("请求路径 {}，系统内部出现异常：{}", request.getRequestURI(), exception.getMessage(), exception);
        return CommonResponse.error(ResultCodeEnum.ERROR, exception, exception.getMessage());
    }

    /**
     * 非法请求异常
     */
    @ExceptionHandler(value = {
        HttpMediaTypeNotAcceptableException.class,
        HttpMediaTypeNotSupportedException.class,
        HttpRequestMethodNotSupportedException.class,
        MissingServletRequestParameterException.class,
        NoHandlerFoundException.class,
        MissingPathVariableException.class,
        HttpMessageNotReadableException.class
    })
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponse<List<String>> handlerSpringAOPException(Exception exception) {
        return CommonResponse.error(ResultCodeEnum.ILLEGAL_REQUEST, exception, exception.getMessage());
    }

    /**
     * 非法请求异常-参数类型不匹配
     */
    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponse<List<String>> handlerSpringAOPException(MethodArgumentTypeMismatchException exception) {
        return CommonResponse.error(
            ResultCodeEnum.PARAM_TYPE_MISMATCH,
            exception,
            "参数类型错误: " + exception.getValue() + " 不是一个合法的 " + exception.getPropertyName() + " 值"
        );
    }

    /**
     * 非法请求-参数校验
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponse<List<Map<String, String>>> handlerMethodArgumentNotValidException(
        MethodArgumentNotValidException exception
    ) {
        var fieldErrors = exception.getBindingResult().getFieldErrors().stream().map((fieldError) -> {
            return Map.of("field", fieldError.getField(), "error", fieldError.getDefaultMessage());
        }).toList();
        return CommonResponse.error(ResultCodeEnum.PARAM_VALID_ERROR, fieldErrors);
    }

    /**
     * 非法请求异常-参数校验
     */
    @ExceptionHandler(value = {ConstraintViolationException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponse<List<String>> handlerConstraintViolationException(
        ConstraintViolationException exception
    ) {
        String errorMessage = exception.getLocalizedMessage();
        return CommonResponse.error(ResultCodeEnum.PARAM_VALID_ERROR, exception, errorMessage);
    }

    @ExceptionHandler(value = {IllegalArgumentException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponse<List<String>> handlerIllegalArgumentException(IllegalArgumentException exception) {
        return CommonResponse.error(ResultCodeEnum.ERROR, exception, exception.getMessage());
    }

    @ExceptionHandler(value = {IllegalStateException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponse<List<String>> handlerIllegalStateException(IllegalStateException exception) {
        return CommonResponse.error(ResultCodeEnum.ERROR, exception, exception.getMessage());
    }

    /**
     * 自定义业务异常-BusinessException
     */
    @ExceptionHandler(value = {BusinessException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponse<List<String>> handlerCustomException(BusinessException exception) {
        String errorMessage = exception.getMessage();
        return CommonResponse.error(ResultCodeEnum.ERROR, exception, errorMessage);
    }

    /**
     * 自定义系统异常-SystemException
     */
    @ExceptionHandler(value = {SystemException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public CommonResponse<List<String>> handlerCustomException(SystemException exception) {
        String errorMessage = exception.getMessage();
        return CommonResponse.error(ResultCodeEnum.ERROR, exception, errorMessage);
    }

}
