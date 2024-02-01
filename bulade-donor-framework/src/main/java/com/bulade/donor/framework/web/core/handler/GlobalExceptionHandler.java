package com.bulade.donor.framework.web.core.handler;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import com.bulade.donor.common.core.CommonResponse;
import com.bulade.donor.common.enums.ResultCodeEnum;
import com.bulade.donor.common.exception.BusinessException;
import com.bulade.donor.common.utils.json.JsonUtils;
import com.bulade.donor.common.utils.monitor.TracerUtils;
import com.bulade.donor.common.utils.servlet.ServletUtils;
import com.bulade.donor.framework.web.apilog.bo.ApiErrorLogCreateBO;
import com.bulade.donor.framework.web.apilog.service.ApiErrorLogFrameworkService;
import com.bulade.donor.framework.web.utils.WebFrameworkUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.util.Assert;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 全局异常处理器，将 Exception 翻译成 CommonResponse + 对应的异常编号
 */
@RestControllerAdvice
@AllArgsConstructor
@Slf4j
public class GlobalExceptionHandler {

    private final String applicationName;

    private final ApiErrorLogFrameworkService apiErrorLogFrameworkService;

    /**
     * 处理 SpringMVC 请求参数缺失
     * 例如说，接口上设置了 @RequestParam("xx") 参数，结果并未传递 xx 参数
     */
    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public CommonResponse<?> missingServletRequestParameterExceptionHandler(
        MissingServletRequestParameterException ex
    ) {
        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
        return CommonResponse.error(ResultCodeEnum.ILLEGAL_REQUEST, ex,
            String.format("请求参数类型错误:%s", ex.getMessage()));
    }

    /**
     * 处理 SpringMVC 请求参数类型错误
     * 例如说，接口上设置了 @RequestParam("xx") 参数为 Integer，结果传递 xx 参数类型为 String
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public CommonResponse<?> methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.warn("[missingServletRequestParameterExceptionHandler]", ex);
        return CommonResponse.error(ResultCodeEnum.PARAM_TYPE_MISMATCH, ex,
            String.format("请求参数类型错误:%s", ex.getMessage()));
    }

    /**
     * 处理 SpringMVC 参数校验不正确
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public CommonResponse<?> methodArgumentNotValidExceptionExceptionHandler(MethodArgumentNotValidException ex) {
        log.warn("[methodArgumentNotValidExceptionExceptionHandler]", ex);
        FieldError fieldError = ex.getBindingResult().getFieldError();
        assert fieldError != null; // 断言，避免告警
        return CommonResponse.error(ResultCodeEnum.PARAM_VALID_ERROR,
            String.format("请求参数不正确:%s", fieldError.getDefaultMessage()));
    }

    /**
     * 处理 Validator 校验不通过产生的异常
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public CommonResponse<?> constraintViolationExceptionHandler(ConstraintViolationException ex) {
        log.warn("[constraintViolationExceptionHandler]", ex);
        ConstraintViolation<?> constraintViolation = ex.getConstraintViolations().iterator().next();
        return CommonResponse.error(ResultCodeEnum.PARAM_VALID_ERROR,
            String.format("请求参数不正确:%s", constraintViolation.getMessage()));
    }

    /**
     * 请求地址不存在
     */
    @ExceptionHandler(value = {NoHandlerFoundException.class, NoResourceFoundException.class})
    public CommonResponse<?> noHandlerFoundExceptionHandler(HttpServletRequest req, NoHandlerFoundException ex) {
        log.warn("[noHandlerFoundExceptionHandler]", ex);
        return CommonResponse.error(ResultCodeEnum.NOT_FOUND,
            String.format("请求地址不存在:%s", ex.getRequestURL()));
    }

    /**
     * 处理 SpringMVC 请求方法不正确
     * 例如说，A 接口的方法为 GET 方式，结果请求方法为 POST 方式，导致不匹配
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public CommonResponse<?> httpRequestMethodNotSupportedExceptionHandler(HttpRequestMethodNotSupportedException ex) {
        log.warn("[httpRequestMethodNotSupportedExceptionHandler]", ex);
        return CommonResponse.error(ResultCodeEnum.METHOD_NOT_ALLOWED, String.format("请求方法不正确:%s", ex.getMessage()));
    }

    /**
     * 处理 Resilience4j 限流抛出的异常
     */
    public CommonResponse<?> requestNotPermittedExceptionHandler(HttpServletRequest req, Throwable ex) {
        log.warn("[requestNotPermittedExceptionHandler][url({}) 访问过于频繁]", req.getRequestURL(), ex);
        return CommonResponse.error(ResultCodeEnum.TOO_MANY_REQUESTS);
    }

    /**
     * 处理 Spring Security 权限不足的异常
     *
     * 来源是，使用 @PreAuthorize 注解，AOP 进行权限拦截
     */
    @ExceptionHandler(value = AccessDeniedException.class)
    public CommonResponse<?> accessDeniedExceptionHandler(HttpServletRequest req, AccessDeniedException ex) {
        log.warn("[accessDeniedExceptionHandler][userId({}) 无法访问 url({})]",
                WebFrameworkUtils.getLoginUserId(req), req.getRequestURL(), ex);
        return CommonResponse.error(ResultCodeEnum.NO_PERMISSION);
    }

    /**
     * 处理业务异常 BusinessException
     */
    @ExceptionHandler(value = {BusinessException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public CommonResponse<List<String>> businessExceptionHandler(BusinessException exception) {
        String errorMessage = exception.getMessage();
        return CommonResponse.error(ResultCodeEnum.ERROR, exception, errorMessage);
    }

    /**
     * 处理系统异常，兜底处理所有的一切
     */
    @ExceptionHandler(value = Exception.class)
    public CommonResponse<?> defaultExceptionHandler(HttpServletRequest req, Throwable ex) {
        // 情况一：处理表不存在的异常
        CommonResponse<?> tableNotExistsResult = handleTableNotExists(ex);
        if (tableNotExistsResult != null) {
            return tableNotExistsResult;
        }

        // 情况二：部分特殊的库的处理
        if (Objects.equals("io.github.resilience4j.ratelimiter.RequestNotPermitted", ex.getClass().getName())) {
            return requestNotPermittedExceptionHandler(req, ex);
        }
        // 情况三：处理异常
        log.error("[defaultExceptionHandler]", ex);
        // 插入异常日志
        this.createExceptionLog(req, ex);
        // 返回 ERROR CommonResponse
        return CommonResponse.error(ResultCodeEnum.ERROR);
    }

    private void createExceptionLog(HttpServletRequest req, Throwable e) {
        // 插入错误日志
        var errorLog = new ApiErrorLogCreateBO();
        try {
            // 初始化 errorLog
            initExceptionLog(errorLog, req, e);
            // 执行插入 errorLog
            apiErrorLogFrameworkService.createApiErrorLog(errorLog);
        } catch (Throwable th) {
            log.error("[createExceptionLog][url({}) log({}) 发生异常]", req.getRequestURI(),
                JsonUtils.toJsonString(errorLog), th);
        }
    }

    private void initExceptionLog(ApiErrorLogCreateBO errorLog, HttpServletRequest request, Throwable e) {
        // 处理用户信息
        errorLog.setUserId(WebFrameworkUtils.getLoginUserId(request));
        errorLog.setUserType(WebFrameworkUtils.getLoginUserType(request));
        // 设置异常字段
        errorLog.setExceptionName(e.getClass().getName());
        errorLog.setExceptionMessage(ExceptionUtil.getMessage(e));
        errorLog.setExceptionRootCauseMessage(ExceptionUtil.getRootCauseMessage(e));
        errorLog.setExceptionStackTrace(ExceptionUtils.getStackTrace(e));
        StackTraceElement[] stackTraceElements = e.getStackTrace();
        Assert.notEmpty(stackTraceElements, "异常 stackTraceElements 不能为空");
        StackTraceElement stackTraceElement = stackTraceElements[0];
        errorLog.setExceptionClassName(stackTraceElement.getClassName());
        errorLog.setExceptionFileName(stackTraceElement.getFileName());
        errorLog.setExceptionMethodName(stackTraceElement.getMethodName());
        errorLog.setExceptionLineNumber(stackTraceElement.getLineNumber());
        // 设置其它字段
        errorLog.setTraceId(TracerUtils.getTraceId());
        errorLog.setApplicationName(applicationName);
        errorLog.setRequestUrl(request.getRequestURI());
        Map<String, Object> requestParams = MapUtil.<String, Object>builder()
                .put("query", ServletUtils.getParamMap(request))
                .put("body", ServletUtils.getBody(request)).build();
        errorLog.setRequestParams(JsonUtils.toJsonString(requestParams));
        errorLog.setRequestMethod(request.getMethod());
        errorLog.setUserAgent(ServletUtils.getUserAgent(request));
        errorLog.setUserIp(ServletUtils.getClientIP(request));
        errorLog.setExceptionTime(LocalDateTime.now());
    }

    /**
     * 处理 Table 不存在的异常情况
     *
     * @param ex 异常
     * @return 如果是 Table 不存在的异常，则返回对应的 CommonResponse
     */
    private CommonResponse<?> handleTableNotExists(Throwable ex) {
        String message = ExceptionUtil.getRootCauseMessage(ex);
        if (!message.contains("doesn't exist")) {
            return null;
        }
        // 1. 系统模块（待定）
        if (message.contains("sys_")) {
            log.error("[系统模块-表结构未导入");
            return CommonResponse.error(ResultCodeEnum.NOT_IMPLEMENTED, "[统模块-表结构未导入]");
        }
        return null;
    }

}
