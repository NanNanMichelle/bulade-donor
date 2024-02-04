package com.bulade.donor.authorization.dao;

import com.bulade.donor.authorization.enums.PermissionType;
import com.bulade.donor.authorization.model.Permission;
import com.bulade.donor.common.page.PageParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PermissionsMapper {

    Integer insert(Permission permission);

    Permission selectById(Long id);

    List<Permission> listAll(Permission permission, Boolean isDeleted, PageParam pageParam);

    Integer countAll(Permission permission, Boolean isDeleted);

    Integer updateById(Permission permission);

    Integer deleteById(Long id);

    List<Permission> listByType(PermissionType type);

}
