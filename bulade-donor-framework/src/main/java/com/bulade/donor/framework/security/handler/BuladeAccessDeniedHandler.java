package com.bulade.donor.framework.security.handler;

import com.alibaba.fastjson2.JSONObject;
import com.bulade.donor.common.core.CommonResponse;
import com.bulade.donor.common.enums.ResultCodeEnum;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class BuladeAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception) throws IOException, ServletException {
        response.setStatus(403);
        response.setHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        var resp = CommonResponse.error(ResultCodeEnum.NO_PERMISSION, "access denied");
        response.getWriter().write(JSONObject.toJSONString(resp));
    }

}
