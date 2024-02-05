package com.bulade.donor.framework.security.handler;

import com.alibaba.fastjson2.JSONObject;
import com.bulade.donor.common.core.CommonResponse;
import com.bulade.donor.common.enums.ResultCodeEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class BuladeAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(401);
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        var resp = CommonResponse.error(ResultCodeEnum.UNAUTHORIZED, "login required");
        response.getWriter().write(JSONObject.toJSONString(resp));
    }

}

