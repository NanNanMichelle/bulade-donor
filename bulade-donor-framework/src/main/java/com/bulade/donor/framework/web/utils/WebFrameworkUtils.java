package com.bulade.donor.framework.web.utils;

import com.bulade.donor.common.core.CommonResponse;
import com.bulade.donor.framework.security.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;

public class WebFrameworkUtils {

    private WebFrameworkUtils() {
    }

    private static final String REQUEST_ATTRIBUTE_LOGIN_USER_ID = "login_user_id";

    private static final String REQUEST_ATTRIBUTE_COMMON_RESULT = "common_result";

    private static final String REQUEST_ATTRIBUTE_LOGIN_USER_TYPE = "login_user_type";

    public static Long getLoginUserId(String jwtToken) {
        return JwtUtils.getByKey(jwtToken, "id", Long.class);
    }

    public static Integer getLoginUserType(String jwtToken) {
        return JwtUtils.getByKey(jwtToken, "userType", Integer.class);
    }

    public static Integer getLoginUserType(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return (Integer) request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_TYPE);
    }

    public static CommonResponse<?> getCommonResult(HttpServletRequest request) {
        return (CommonResponse<?>) request.getAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT);
    }

    public static void setCommonResult(HttpServletRequest request, CommonResponse<?> commonResult) {
        request.setAttribute(REQUEST_ATTRIBUTE_COMMON_RESULT, commonResult);
    }

    /**
     * 获得当前用户的编号，从请求中
     * 注意：该方法仅限于 framework 框架使用！！！
     *
     * @param request 请求
     * @return 用户编号
     */
    public static Long getLoginUserId(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        return (Long) request.getAttribute(REQUEST_ATTRIBUTE_LOGIN_USER_ID);
    }

}
