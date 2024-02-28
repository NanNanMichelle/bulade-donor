package com.bulade.donor.system.authorization.service;

import com.bulade.donor.system.authorization.bo.UserRoleInsertBO;
import com.bulade.donor.system.authorization.bo.UserRoleQueryBO;
import com.bulade.donor.system.authorization.enums.RoleType;
import com.bulade.donor.system.authorization.model.UserRole;
import com.bulade.donor.common.page.PageParam;

import java.util.List;

public interface UserRolesService {

    Long insert(UserRoleInsertBO userRoleInsertBO);

    UserRole selectById(Long id);

    List<UserRole> listAll(UserRoleQueryBO userRoleQueryBO, PageParam pageParam);

    Integer countAll(UserRoleQueryBO userRoleQueryBO);

    Integer deleteById(Long id);

    List<Long> listRoleIdByUserIdAndRoleType(Long userId, RoleType type);

    void initialize(Long userId, RoleType type);

}
