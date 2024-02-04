package com.bulade.donor.authorization.service.impl;

import com.bulade.donor.authorization.bo.UserRoleInsertBO;
import com.bulade.donor.authorization.bo.UserRoleQueryBO;
import com.bulade.donor.authorization.dao.UserRolesMapper;
import com.bulade.donor.authorization.enums.RoleType;
import com.bulade.donor.authorization.model.UserRole;
import com.bulade.donor.authorization.service.RolesService;
import com.bulade.donor.authorization.service.UserRolesService;
import com.bulade.donor.common.page.PageParam;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Slf4j
public class UserRolesServiceImpl implements UserRolesService {

    @Resource
    private UserRolesMapper userRolesMapper;

    @Resource
    private RolesService rolesService;

    @Override
    public Long insert(UserRoleInsertBO userRoleInsertBO) {
        insertCheck(userRoleInsertBO);

        var userRole = userRoleInsertBO.toUserRole();
        var count = userRolesMapper.insert(userRole);
        Assert.state(count == 1, "userRole添加失败userRoleInsertBO[%s]".formatted(userRoleInsertBO));
        return userRole.getId();
    }

    private void insertCheck(UserRoleInsertBO userRoleInsertBO) {
        var roleId = userRoleInsertBO.getRoleId();
        var type = userRoleInsertBO.getType();
        var role = rolesService.selectById(roleId);
        var dbRoleType = role.getType();
        Assert.state(dbRoleType.equals(type), "roleId[%s]的type[%s]添加type[%s]不一致".formatted(roleId, dbRoleType, type));
        var userId = userRoleInsertBO.getUserId();
        var dbUserRole = userRolesMapper.selectByUserIdAndRoleIdAndType(userId, roleId, type);
        Assert.isNull(dbUserRole, "当前用户已存在相同的权限userRoleInsertBO[%s]".formatted(userRoleInsertBO));
    }

    @Override
    public UserRole selectById(Long id) {
        var userRole = userRolesMapper.selectById(id);
        Assert.notNull(userRole, "userRole不存在id[%s]".formatted(id));
        return userRole;
    }

    @Override
    public List<UserRole> listAll(UserRoleQueryBO userRoleQueryBO, PageParam pageParam) {
        var userRole = userRoleQueryBO.toUserRole();
        var isDeleted = userRoleQueryBO.getIsDeleted();
        return userRolesMapper.listAll(userRole, isDeleted, pageParam);
    }

    @Override
    public Integer countAll(UserRoleQueryBO userRoleQueryBO) {
        var userRole = userRoleQueryBO.toUserRole();
        var isDeleted = userRoleQueryBO.getIsDeleted();
        return userRolesMapper.countAll(userRole, isDeleted);
    }

    @Override
    public Integer deleteById(Long id) {
        var count = userRolesMapper.deleteById(id);
        Assert.state(count == 1, "删除失败id[%s]".formatted(id));
        return count;
    }

    @Override
    public List<Long> listRoleIdByUserIdAndRoleType(Long userId, RoleType type) {
        return userRolesMapper.listRoleIdByUserIdAndRoleType(userId, type);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void initialize(Long userId, RoleType type) {
        var roles = rolesService.listByTypeAndDefault(type);
        var userRoleInserts = roles.stream().map(t -> UserRoleInsertBO.of(userId, t.getId(), type)).toList();
        userRoleInserts.forEach(this::insert);
    }

}
