package com.bulade.donor.system.service.impl;

import com.bulade.donor.authorization.enums.RoleType;
import com.bulade.donor.authorization.service.GrantedAuthorityService;
import com.bulade.donor.framework.security.api.TokenApi;
import com.bulade.donor.framework.security.dto.AccessTokenCheckDTO;
import com.bulade.donor.framework.security.utils.JwtUtils;
import com.bulade.donor.common.enums.UserType;
import com.bulade.donor.framework.web.utils.WebFrameworkUtils;
import com.bulade.donor.system.service.AdminsService;
import com.bulade.donor.system.service.UsersService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TokenApiImpl implements TokenApi {

    @Resource
    private UsersService userService;

    @Resource
    private AdminsService adminService;

    @Resource
    private GrantedAuthorityService grantedAuthorityService;

    @Override
    public AccessTokenCheckDTO checkAccessToken(String accessToken) {
        var isNotValid = JwtUtils.isNotValid(accessToken);
        if (isNotValid) {
            return null;
        }
        var userId = WebFrameworkUtils.getLoginUserId(accessToken);
        var userType = WebFrameworkUtils.getLoginUserType(accessToken);
        var scopes = new ArrayList<Long>();
        if (UserType.ADMIN.getCode().equals(userType)) {
            adminService.selectById(userId);
            scopes.addAll(grantedAuthorityService.authorities(userId, RoleType.PLATFORM));
        }
        if (UserType.MEMBER.getCode().equals(userType)) {
            userService.selectById(userId);
            scopes.addAll(grantedAuthorityService.authorities(userId, RoleType.USER));
        }
        return AccessTokenCheckDTO.of(userId, userType, scopes);
    }
}
