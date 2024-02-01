package com.bulade.donor.framework.security.filter;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.bulade.donor.framework.security.LoginUser;
import com.bulade.donor.framework.security.api.TokenApi;
import com.bulade.donor.framework.security.config.SecurityProperties;
import com.bulade.donor.common.exception.UnAuthorizedException;
import com.bulade.donor.framework.security.utils.SecurityFrameworkUtils;
import com.bulade.donor.framework.security.utils.WebFrameworkUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;

@Slf4j
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private HandlerExceptionResolver handlerExceptionResolver;

    @Resource
    private TokenApi tokenApi;

    @Override
    protected void doFilterInternal(
        @NonNull HttpServletRequest request,
        @NonNull HttpServletResponse response,
        @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        String token = SecurityFrameworkUtils.obtainAuthorization(request,
            securityProperties.getTokenHeader(), securityProperties.getTokenParameter());
        if (StrUtil.isNotEmpty(token)) {
            Integer userType = WebFrameworkUtils.getLoginUserType(request);
            LoginUser user = null;
            if (StrUtil.isNotEmpty(token)) {
                // 1.1 基于 token 构建登录用户
                user = buildLoginUserByToken(token, userType);
                // 1.2 模拟 Login 功能，方便日常开发调试
                if (user == null) {
                    user = mockLoginUser(token, userType);
                }
            }
            if (Objects.nonNull(user)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                    user, null, Collections.emptyList());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            } else {
                renderException(request, response, "未找到有效的管理员");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private void renderException(HttpServletRequest request, HttpServletResponse response, String message) {
        handlerExceptionResolver.resolveException(request, response, null, new UnAuthorizedException(message));
    }

    private LoginUser buildLoginUserByToken(String token, Integer userType) {
        var accessToken = tokenApi.checkAccessToken(token);
        if (accessToken == null) {
            return null;
        }

        if (userType != null && ObjectUtil.notEqual(accessToken.getUserType(), userType)) {
            return null;
        }

        var loginUser = new LoginUser();
        loginUser.setId(accessToken.getUserId());
        loginUser.setUserType(userType);
        loginUser.setScopes(accessToken.getScopes());
        return loginUser;
    }

    private LoginUser mockLoginUser(String token, Integer userType) {
        if (!securityProperties.getMockEnable()) {
            return null;
        }
        if (!token.startsWith(securityProperties.getMockSecret())) {
            return null;
        }
        Long userId = Long.valueOf(token.substring(securityProperties.getMockSecret().length()));
        var loginUser = new LoginUser();
        loginUser.setId(userId);
        loginUser.setUserType(userType);
        return loginUser;
    }
}
