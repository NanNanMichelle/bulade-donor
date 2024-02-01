package com.bulade.donor.framework.web.utils;

import com.bulade.donor.common.core.CommonResponse;
import com.bulade.donor.framework.security.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;

public class WebFrameworkUtils {

    private WebFrameworkUtils() {
    }

    private static final String REQUEST_ATTRIBUTE_COMMON_RESULT = "common_result";

    public static Long getLoginUserId(String jwtToken) {
        return JwtUtils.getByKey(jwtToken, "id", Long.class);
    }

    public static Integer getLoginUserType(String jwtToken) {
        return JwtUtils.getByKey(jwtToken, "userType", Integer.class);
    }

    public static CommonResponse<?> getCommonResult(HttpServletRequest request) {
        return (CommonResponse<?>) request.getAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT);
    }
}
