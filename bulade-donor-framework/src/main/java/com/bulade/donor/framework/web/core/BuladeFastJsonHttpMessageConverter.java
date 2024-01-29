package com.bulade.donor.framework.web.core;

import com.alibaba.fastjson2.support.spring6.http.converter.FastJsonHttpMessageConverter;

import java.util.List;

public class BuladeFastJsonHttpMessageConverter extends FastJsonHttpMessageConverter {

    /**
     * byte[].class 是springdoc-openapi-starter-webmvc-ui 2.2.0中MultipleOpenApiWebMvcResource.openapiJson()返回结果
     * 与fastjson配置冲突。如果不过滤掉，会导致swagger页面打不开
     */
    private static final List<Class<?>> IGNORE_CLASSES = List.of(byte[].class);

    private static final boolean NOT_SUPPORT = false;

    @Override
    protected boolean supports(Class<?> clazz) {
        if (isIgnoreClass(clazz)) {
            return NOT_SUPPORT;
        }
        return super.supports(clazz);
    }

    private boolean isIgnoreClass(Class<?> clazz) {
        for (Class<?> ignoreClass : IGNORE_CLASSES) {
            boolean assignableFrom = clazz.isAssignableFrom(ignoreClass);
            if (assignableFrom) {
                return true;
            }
        }
        return false;
    }

}
