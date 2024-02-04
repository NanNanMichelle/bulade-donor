package com.bulade.donor.framework.security.filter;

import cn.hutool.core.text.CharSequenceUtil;
import cn.hutool.core.util.ObjectUtil;
import com.bulade.donor.framework.security.LoginUser;
import com.bulade.donor.framework.security.api.TokenApi;
import com.bulade.donor.framework.security.config.SecurityProperties;
import com.bulade.donor.common.exception.UnAuthorizedException;
import com.bulade.donor.framework.security.utils.SecurityFrameworkUtils;
import com.bulade.donor.framework.web.utils.WebFrameworkUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
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
        if (CharSequenceUtil.isNotEmpty(token)) {
            LoginUser user = null;
            if (CharSequenceUtil.isNotEmpty(token)) {
                var userType = WebFrameworkUtils.getLoginUserType(token);

                user = buildLoginUserByToken(token, userType);
                log.info("基于 token 构建登录用户: " + user);
                // 1.2 模拟 Login 功能，方便日常开发调试
                if (user == null) {
                    user = mockLoginUser(token, userType);
                }
            }
            if (Objects.nonNull(user)) {
                setLoginUser(user, request);
            } else {
                renderException(request, response, "未找到有效用户");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    public static void setLoginUser(LoginUser user, HttpServletRequest request) {
        // 创建 Authentication，并设置到上下文
        var authorities = user.getScopes().stream().map(t -> new SimpleGrantedAuthority(t.toString())).toList();
        var authentication = new UsernamePasswordAuthenticationToken(
            user, null, authorities);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 额外设置到 request 中，用于 ApiAccessLogFilter 可以获取到用户编号；
        // 原因是，Spring Security 的 Filter 在 ApiAccessLogFilter 后面，在它记录访问日志时，线上上下文已经没有用户编号等信息
        WebFrameworkUtils.setLoginUserId(request, user.getId());
        WebFrameworkUtils.setLoginUserType(request, user.getUserType());
    }

    private void renderException(HttpServletRequest request, HttpServletResponse response, String message) {
        handlerExceptionResolver.resolveException(request, response, null, new UnAuthorizedException(message));
    }

    private LoginUser buildLoginUserByToken(String token, Integer userType) {
        var accessToken = tokenApi.checkAccessToken(token);
        log.info("login token: " + accessToken);
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
        if (Boolean.FALSE.equals(securityProperties.getMockEnable())) {
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
