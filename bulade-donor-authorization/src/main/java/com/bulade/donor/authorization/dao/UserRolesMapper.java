package com.bulade.donor.authorization.dao;

import com.bulade.donor.authorization.enums.RoleType;
import com.bulade.donor.authorization.model.UserRole;
import com.bulade.donor.common.page.PageParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserRolesMapper {

    Integer insert(UserRole userRole);

    UserRole selectById(Long id);

    List<UserRole> listAll(UserRole userRole, Boolean isDeleted, PageParam pageParam);

    Integer countAll(UserRole userRole, Boolean isDeleted);

    Integer deleteById(Long id);

    UserRole selectByUserIdAndRoleIdAndType(Long userId, Long roleId, RoleType type);

    List<Long> listRoleIdByUserIdAndRoleType(Long userId, RoleType type);

}
