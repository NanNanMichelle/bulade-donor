package com.bulade.donor.framework.web.apilog.filter;

import cn.hutool.core.date.LocalDateTimeUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.bulade.donor.common.core.CommonResponse;
import com.bulade.donor.common.enums.ResultCodeEnum;
import com.bulade.donor.common.utils.monitor.TracerUtils;
import com.bulade.donor.common.utils.servlet.ServletUtils;
import com.bulade.donor.framework.security.config.SecurityProperties;
import com.bulade.donor.framework.security.utils.SecurityFrameworkUtils;
import com.bulade.donor.framework.web.apilog.bo.ApiAccessLogCreateBO;
import com.bulade.donor.framework.web.apilog.service.ApiAccessLogFrameworkService;
import com.bulade.donor.framework.web.utils.WebFrameworkUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import static com.bulade.donor.common.utils.json.JsonUtils.toJsonString;

/**
 * API 访问日志 Filter
 */
@Slf4j
public class ApiAccessLogFilter extends OncePerRequestFilter {

    private final String applicationName;

    private final ApiAccessLogFrameworkService apiAccessLogFrameworkService;

    private final SecurityProperties securityProperties;

    public ApiAccessLogFilter(String applicationName, ApiAccessLogFrameworkService apiAccessLogFrameworkService,
                              SecurityProperties securityProperties) {
        this.applicationName = applicationName;
        this.apiAccessLogFrameworkService = apiAccessLogFrameworkService;
        this.securityProperties = securityProperties;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        // 只过滤 API 请求的地址
        return !CharSequenceUtil.startWithAny(request.getRequestURI(), "/api");
    }

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain
    ) throws ServletException, IOException {
        // 获得开始时间
        LocalDateTime beginTime = LocalDateTime.now();
        // 提前获得参数，避免 XssFilter 过滤处理
        Map<String, String> queryString = ServletUtils.getParamMap(request);
        String requestBody = ServletUtils.isJsonRequest(request) ? ServletUtils.getBody(request) : null;
        try {
            // 继续过滤器
            filterChain.doFilter(request, response);
            // 正常执行，记录日志
            createApiAccessLog(request, beginTime, queryString, requestBody, null);
        } catch (Exception ex) {
            // 异常执行，记录日志
            createApiAccessLog(request, beginTime, queryString, requestBody, ex);
            throw ex;
        }
    }

    private void createApiAccessLog(HttpServletRequest request, LocalDateTime beginTime,
                                    Map<String, String> queryString, String requestBody, Exception ex) {
        var accessLogBO = new ApiAccessLogCreateBO();
        try {
            this.buildApiAccessLogDTO(accessLogBO, request, beginTime, queryString, requestBody, ex);
            apiAccessLogFrameworkService.createApiAccessLog(accessLogBO);
        } catch (Throwable th) {
            log.error("[createApiAccessLog][url({}) log({}) 发生异常]", request.getRequestURI(),
                toJsonString(accessLogBO), th);
        }
    }

    private void buildApiAccessLogDTO(ApiAccessLogCreateBO accessLog, HttpServletRequest request, LocalDateTime beginTime,
                                      Map<String, String> queryString, String requestBody, Exception ex) {
        var token = SecurityFrameworkUtils.obtainAuthorization(request,
            securityProperties.getTokenHeader(), securityProperties.getTokenParameter());
        // 处理用户信息
        accessLog.setUserId(WebFrameworkUtils.getLoginUserId(token));
        accessLog.setUserType(WebFrameworkUtils.getLoginUserType(token));
        // 设置访问结果
        CommonResponse<?> result = WebFrameworkUtils.getCommonResult(request);
        if (result != null) {
            accessLog.setResultCode(result.getCode());
            accessLog.setResultMsg(result.getMessage());
        } else if (ex != null) {
            accessLog.setResultCode(ResultCodeEnum.ERROR.getCode());
            accessLog.setResultMsg(ExceptionUtil.getRootCauseMessage(ex));
        } else {
            accessLog.setResultCode(0);
            accessLog.setResultMsg("");
        }
        // 设置其它字段
        accessLog.setTraceId(TracerUtils.getTraceId());
        accessLog.setApplicationName(applicationName);
        accessLog.setRequestUrl(request.getRequestURI());
        var requestParams = MapUtil.<String, Object>builder()
            .put("query", queryString)
            .put("body", requestBody)
            .build();
        accessLog.setRequestParams(toJsonString(requestParams));
        accessLog.setRequestMethod(request.getMethod());
        accessLog.setUserAgent(ServletUtils.getUserAgent(request));
        accessLog.setUserIp(ServletUtils.getClientIP(request));
        // 持续时间
        accessLog.setBeginTime(beginTime);
        accessLog.setEndTime(LocalDateTime.now());
        accessLog.setDuration((int) LocalDateTimeUtil.between(accessLog.getBeginTime(),
            accessLog.getEndTime(), ChronoUnit.MILLIS));
    }

}
