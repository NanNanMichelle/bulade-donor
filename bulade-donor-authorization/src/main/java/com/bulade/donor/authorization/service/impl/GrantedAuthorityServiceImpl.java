package com.bulade.donor.authorization.service.impl;

import com.bulade.donor.authorization.enums.RoleType;
import com.bulade.donor.authorization.service.GrantedAuthorityService;
import com.bulade.donor.authorization.service.RolePermissionsService;
import com.bulade.donor.authorization.service.UserRolesService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class GrantedAuthorityServiceImpl implements GrantedAuthorityService {

    @Resource
    private UserRolesService userRolesService;

    @Resource
    private RolePermissionsService rolePermissionsService;

    @Override
    public List<Long> authorities(Long userId, RoleType type) {
        var roleIds = userRolesService.listRoleIdByUserIdAndRoleType(userId, type);
        return rolePermissionsService.listPermissionIdByRoleIds(roleIds);
    }
}
