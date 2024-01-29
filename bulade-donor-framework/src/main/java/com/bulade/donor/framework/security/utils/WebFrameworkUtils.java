package com.bulade.donor.framework.security.utils;

import jakarta.servlet.http.HttpServletRequest;

/**
 * 专属于 web 包的工具类
 */
public class WebFrameworkUtils {

    public static final String REQUEST_ATTRIBUTE_LOGIN_USER_TYPE = "login_user_type";

    public static Integer getLoginUserType(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        // 1. 优先，从 Attribute 中获取
        Integer userType = Integer.valueOf(request.getHeader(REQUEST_ATTRIBUTE_LOGIN_USER_TYPE));
        if (userType != null) {
            return userType;
        }
        return null;
    }

}

