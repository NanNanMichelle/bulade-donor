package com.bulade.donor.application.manage.impl;

import com.bulade.donor.application.manage.AdminAuthenticationService;
import com.bulade.donor.common.enums.*;
import com.bulade.donor.common.exception.BusinessException;
import com.bulade.donor.common.utils.monitor.TracerUtils;
import com.bulade.donor.common.utils.servlet.ServletUtils;
import com.bulade.donor.framework.security.utils.JwtUtils;
import com.bulade.donor.system.bo.LoginLogCreateBO;
import com.bulade.donor.system.service.LoginLogService;
import com.bulade.donor.system.model.Admin;
import com.bulade.donor.system.service.AdminsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class AdminAuthenticationServiceImpl implements AdminAuthenticationService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AdminsService adminsService;

    @Resource
    private LoginLogService loginLogService;

    @Override
    public String signIn(String username, String password) {
        var admin = authenticate(username, password);
        var claims = Map.of("id", admin.getId(), "userType", UserType.ADMIN.getCode());
        return JwtUtils.generateToken(claims);
    }

    public Admin authenticate(String username, String password) {
        final LoginLogTypeEnum logTypeEnum = LoginLogTypeEnum.LOGIN_USERNAME;
        // 校验账号是否存在
        var user = adminsService.selectByUsername(username);
        if (Objects.isNull(user)) {
            createLoginLog(null, username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw BusinessException.ofLogin(ResultCodeEnum.INVALID_USERNAME);
        }
        if (!passwordEncoder.matches(password, user.getPassword())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.BAD_CREDENTIALS);
            throw BusinessException.ofLogin(ResultCodeEnum.INVALID_USERNAME_PASSWORD);
        }
        // 校验是否禁用
        if (Objects.nonNull(user.getDeletedAt())) {
            createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.USER_DISABLED);
            throw BusinessException.ofLogin(ResultCodeEnum.AUTH_LOGIN_USER_DISABLED);
        }
        createLoginLog(user.getId(), username, logTypeEnum, LoginResultEnum.SUCCESS);
        return user;
    }

    private void createLoginLog(Long userId, String username,
                                LoginLogTypeEnum logTypeEnum, LoginResultEnum loginResult) {
        // 插入登录日志
        var loginLogCreateBO = new LoginLogCreateBO();
        loginLogCreateBO.setLogType(logTypeEnum.getType());
        loginLogCreateBO.setTraceId(TracerUtils.getTraceId());
        loginLogCreateBO.setUserId(userId);
        loginLogCreateBO.setUserType(UserType.ADMIN.getCode());
        loginLogCreateBO.setUsername(username);
        loginLogCreateBO.setUserAgent(ServletUtils.getUserAgent());
        loginLogCreateBO.setUserIp(ServletUtils.getClientIP());
        loginLogCreateBO.setResult(loginResult.getResult());
        loginLogService.createLoginLog(loginLogCreateBO);
        // 更新最后登录时间
        if (userId != null && Objects.equals(LoginResultEnum.SUCCESS.getResult(), loginResult.getResult())) {
            adminsService.updateUserLogin(userId, ServletUtils.getClientIP());
        }
    }

}
