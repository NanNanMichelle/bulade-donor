package com.bulade.donor.application.configuration;

import com.bulade.donor.common.annotation.IgnoreResponseAdvice;
import com.bulade.donor.common.core.CommonResponse;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = "com.bulade.donor.application.controller.admin")
public class GlobalResponseBodyAdvice implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        var clazz = methodParameter.getDeclaringClass();
        var method = methodParameter.getMethod();

        if (clazz.isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }

        if (method.isAnnotationPresent(IgnoreResponseAdvice.class)) {
            return false;
        }

        return true;
    }

    @Override
    public Object beforeBodyWrite(
        Object o,
        MethodParameter methodParameter,
        MediaType mediaType,
        Class<? extends HttpMessageConverter<?>> aClass,
        ServerHttpRequest request,
        ServerHttpResponse response
    ) {
        if (o instanceof CommonResponse) {
            return o;
        }

        return CommonResponse.data(o);
    }
}
