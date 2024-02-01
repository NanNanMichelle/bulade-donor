package com.bulade.donor.framework.web.core.handler;

import com.bulade.donor.common.annotation.IgnoreResponseAdvice;
import com.bulade.donor.common.core.CommonResponse;
import com.bulade.donor.framework.web.utils.WebFrameworkUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * 全局响应结果（ResponseBody）处理器
 * GlobalResponseBodyHandler 本质上是 AOP，它不应该改变 Controller 返回的数据结构
 * 目前，GlobalResponseBodyHandler 的主要作用是，记录 Controller 的返回结果，
 * 方便 ApiAccessLogFilter 记录访问日志
 */
@RestControllerAdvice
public class GlobalResponseBodyHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class converterType) {
        var clazz = methodParameter.getDeclaringClass();
        var method = methodParameter.getMethod();

        if (method == null) {
            return false;
        }

        if (method.isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }

        if (clazz.isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }

        // 只拦截返回结果为 CommonResponse 类型
        return method.getReturnType() == CommonResponse.class;
    }

    @Override
    public Object beforeBodyWrite(
        Object body,
        MethodParameter methodParameter,
        MediaType mediaType,
        Class aClass,
        ServerHttpRequest request,
        ServerHttpResponse response
    ) {
        // 记录 Controller 结果
        WebFrameworkUtils.setCommonResult(((ServletServerHttpRequest) request).getServletRequest(),
            (CommonResponse<?>) body);
        return CommonResponse.data(body);
    }
}
