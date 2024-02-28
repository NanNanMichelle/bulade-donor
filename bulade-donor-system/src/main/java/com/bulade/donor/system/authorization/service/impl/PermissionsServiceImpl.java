package com.bulade.donor.system.authorization.service.impl;

import com.bulade.donor.system.authorization.bo.PermissionInsertBO;
import com.bulade.donor.system.authorization.bo.PermissionQueryBO;
import com.bulade.donor.system.authorization.bo.PermissionUpdateBO;
import com.bulade.donor.system.authorization.dao.PermissionsMapper;
import com.bulade.donor.system.authorization.enums.PermissionType;
import com.bulade.donor.system.authorization.model.Permission;
import com.bulade.donor.system.authorization.service.PermissionsService;
import com.bulade.donor.common.page.PageParam;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Slf4j
public class PermissionsServiceImpl implements PermissionsService {

    @Resource
    private PermissionsMapper permissionsMapper;

    @Override
    public Long insert(PermissionInsertBO permissionInsertBO) {
        var permission = permissionInsertBO.toPermission();
        var count = permissionsMapper.insert(permission);
        Assert.state(count == 1, "permission添加失败permissionInsertBO[%s]".formatted(permissionInsertBO));
        return permission.getId();
    }

    @Override
    public Permission selectById(Long id) {
        var permission = permissionsMapper.selectById(id);
        Assert.notNull(permission, "permission找不到id[%s]".formatted(id));
        return permission;
    }

    @Override
    public List<Permission> listAll(PermissionQueryBO permissionQueryBO, PageParam pageParam) {
        var permission = permissionQueryBO.toPermission();
        var isDeleted = permissionQueryBO.getIsDeleted();
        return permissionsMapper.listAll(permission, isDeleted, pageParam);
    }

    @Override
    public Integer countAll(PermissionQueryBO permissionQueryBO) {
        var permission = permissionQueryBO.toPermission();
        var isDeleted = permissionQueryBO.getIsDeleted();
        return permissionsMapper.countAll(permission, isDeleted);
    }

    @Override
    public Integer updateById(PermissionUpdateBO permissionUpdateBO) {
        var permission = permissionUpdateBO.toPermission();
        var count = permissionsMapper.updateById(permission);
        Assert.state(count == 1, "修改失败permissionUpdateBO[%s]".formatted(permissionUpdateBO));
        return count;
    }

    @Override
    public Integer deleteById(Long id) {
        var count = permissionsMapper.deleteById(id);
        Assert.state(count == 1, "删除失败id[%s]".formatted(id));
        return count;
    }

    @Override
    public List<Permission> listByType(PermissionType type) {
        return permissionsMapper.listByType(type);
    }

}
