package com.bulade.donor.authorization.service.impl;

import com.bulade.donor.authorization.bo.RolePermissionInsertBO;
import com.bulade.donor.authorization.bo.RolePermissionQueryBO;
import com.bulade.donor.authorization.dao.RolePermissionsMapper;
import com.bulade.donor.authorization.enums.PermissionType;
import com.bulade.donor.authorization.model.RolePermission;
import com.bulade.donor.authorization.service.PermissionsService;
import com.bulade.donor.authorization.service.RolePermissionsService;
import com.bulade.donor.authorization.service.RolesService;
import com.bulade.donor.common.page.PageParam;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class RolePermissionsServiceImpl implements RolePermissionsService {

    @Resource
    private RolePermissionsMapper rolePermissionsMapper;

    @Resource
    private RolesService rolesService;

    @Resource
    private PermissionsService permissionsService;

    @Override
    public Long insert(RolePermissionInsertBO rolePermissionInsertBO) {
        var roleId = rolePermissionInsertBO.getRoleId();
        rolesService.selectById(roleId);
        var permissionId = rolePermissionInsertBO.getPermissionId();
        var permission = permissionsService.selectById(permissionId);
        var isAuthorization = PermissionType.AUTHORIZATION.equals(permission.getType());
        Assert.state(isAuthorization, "权限[%s]的类型不是AUTHORIZATION".formatted(permissionId));

        var dbRolePermission = rolePermissionsMapper.selectByRoleIdAndPermissionId(roleId, permissionId);
        Assert.isNull(dbRolePermission, "当前角色roleId[%s]已存在相同权限permissionId[%s]".formatted(roleId, permissionId));

        var rolePermission = rolePermissionInsertBO.toRolePermission();
        var count = rolePermissionsMapper.insert(rolePermission);
        Assert.state(count == 1, "rolesPermissions添加失败rolePermissionInsertBO[%s]".formatted(rolePermissionInsertBO));
        return rolePermission.getId();
    }

    @Override
    public RolePermission selectById(Long id) {
        var rolePermission = rolePermissionsMapper.selectById(id);
        Assert.notNull(rolePermission, "rolesPermissions找不到id[%s]".formatted(id));
        return rolePermission;
    }

    @Override
    public Integer deleteById(Long id) {
        var count = rolePermissionsMapper.deleteById(id);
        Assert.state(count == 1, "删除失败角色权限id[%s]".formatted(id));
        return count;
    }

    @Override
    public List<RolePermission> listAll(RolePermissionQueryBO rolePermissionQueryBO, PageParam pageParam) {
        var rolePermission = rolePermissionQueryBO.toRolePermission();
        var isDeleted = rolePermissionQueryBO.getIsDeleted();
        return rolePermissionsMapper.listAll(rolePermission, isDeleted, pageParam);
    }

    @Override
    public Integer countAll(RolePermissionQueryBO rolePermissionQueryBO) {
        var rolePermission = rolePermissionQueryBO.toRolePermission();
        var isDeleted = rolePermissionQueryBO.getIsDeleted();
        return rolePermissionsMapper.countAll(rolePermission, isDeleted);
    }

    @Override
    public List<Long> listPermissionIdByRoleIds(List<Long> roleIds) {
        if (CollectionUtils.isEmpty(roleIds)) {
            return List.of();
        }
        return rolePermissionsMapper.listPermissionIdByRoleIds(roleIds);
    }

}
