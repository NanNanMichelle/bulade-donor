package com.bulade.donor.authorization.service.impl;

import com.bulade.donor.authorization.bo.RoleInsertBO;
import com.bulade.donor.authorization.bo.RoleQueryBO;
import com.bulade.donor.authorization.bo.RoleUpdateBO;
import com.bulade.donor.authorization.dao.RolesMapper;
import com.bulade.donor.authorization.enums.RoleType;
import com.bulade.donor.authorization.model.Role;
import com.bulade.donor.authorization.service.RolesService;
import com.bulade.donor.common.page.PageParam;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Service
@Slf4j
public class RolesServiceImpl implements RolesService {

    @Resource
    private RolesMapper rolesMapper;

    @Override
    public Long insert(RoleInsertBO roleInsertBO) {
        Role role = roleInsertBO.toRole();
        Integer count = rolesMapper.insert(role);
        Assert.state(count == 1, "role添加失败roleInsertBO[%s]".formatted(roleInsertBO));
        return role.getId();
    }

    @Override
    public Role selectById(Long id) {
        var role = rolesMapper.selectById(id);
        Assert.notNull(role, "role不存在id[%s]".formatted(id));
        return role;
    }

    @Override
    public List<Role> listAll(RoleQueryBO roleQueryBO, PageParam pageParam) {
        var role = roleQueryBO.toRole();
        var isDeleted = roleQueryBO.getIsDeleted();
        return rolesMapper.listAll(role, isDeleted, pageParam);
    }

    @Override
    public Integer countAll(RoleQueryBO roleQueryBO) {
        var role = roleQueryBO.toRole();
        var isDeleted = roleQueryBO.getIsDeleted();
        return rolesMapper.countAll(role, isDeleted);
    }

    @Override
    public Integer updateById(RoleUpdateBO roleUpdateBO) {
        var role = roleUpdateBO.toRole();
        var count = rolesMapper.updateById(role);
        Assert.state(count == 1, "修改失败roleUpdateBO[%s]".formatted(roleUpdateBO));
        return count;
    }

    @Override
    public Integer deleteById(Long id) {
        var count = rolesMapper.deleteById(id);
        Assert.state(count == 1, "删除失败id[%s]".formatted(id));
        return count;
    }

    @Override
    public List<Role> listByTypeAndDefault(RoleType type) {
        return rolesMapper.listByTypeAndDefault(type);
    }

}
