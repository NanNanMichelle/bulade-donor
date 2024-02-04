package com.bulade.donor.authorization.dao;

import com.bulade.donor.authorization.model.RolePermission;
import com.bulade.donor.common.page.PageParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RolePermissionsMapper {

    Integer insert(RolePermission rolePermission);

    RolePermission selectById(Long id);

    RolePermission selectByRoleIdAndPermissionId(Long roleId, Long permissionId);

    Integer deleteById(Long id);

    List<RolePermission> listAll(RolePermission rolePermission, Boolean isDeleted, PageParam pageParam);

    Integer countAll(RolePermission rolePermission, Boolean isDeleted);

    List<Long> listPermissionIdByRoleIds(List<Long> roleIds);

}
