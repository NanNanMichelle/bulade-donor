package com.bulade.donor.application.service;

import com.bulade.donor.framework.security.utils.JwtUtils;
import com.bulade.donor.system.enums.UserType;
import com.bulade.donor.system.service.AdminsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Map;

@Service
@Slf4j
public class AdminAuthenticationServiceImpl implements AdminAuthenticationService {

    @Resource
    private PasswordEncoder passwordEncoder;

    @Resource
    private AdminsService adminsService;

    @Override
    public String signIn(String username, String password) {
        var admin = adminsService.selectByUsername(username);
        Assert.state(passwordEncoder.matches(password, admin.getPassword()), "密码不正确");

        var claims = Map.of("id", admin.getId(), "userType", UserType.ADMIN.getCode());
        return JwtUtils.generateToken(claims);
    }

}
