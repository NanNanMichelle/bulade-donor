package com.bulade.donor.system.service.impl;

import com.bulade.donor.framework.security.api.TokenApi;
import com.bulade.donor.framework.security.dto.AccessTokenCheckDTO;
import com.bulade.donor.framework.security.utils.JwtUtils;
import com.bulade.donor.common.enums.UserType;
import com.bulade.donor.system.service.AdminsService;
import com.bulade.donor.system.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenApiImpl implements TokenApi {

    @Resource
    private UserService userService;

    @Resource
    private AdminsService adminService;

    @Override
    public AccessTokenCheckDTO checkAccessToken(String accessToken) {
        var isNotValid = JwtUtils.isNotValid(accessToken);
        if (isNotValid) {
            return null;
        }

        var userId = JwtUtils.getByKey(accessToken, "id", Long.class);
        var userType = JwtUtils.getByKey(accessToken, "userType", Integer.class);
        if (UserType.ADMIN.getCode().equals(userType)) {
            adminService.selectById(userId);
        }
        if (UserType.MEMBER.getCode().equals(userType)) {
            userService.selectById(userId);
        }
        return AccessTokenCheckDTO.of(userId, userType, List.of());
    }
}
