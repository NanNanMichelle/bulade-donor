package com.bulade.donor.authorization.service;

import com.bulade.donor.authorization.bo.RoleInsertBO;
import com.bulade.donor.authorization.bo.RoleQueryBO;
import com.bulade.donor.authorization.bo.RoleUpdateBO;
import com.bulade.donor.authorization.enums.RoleType;
import com.bulade.donor.authorization.model.Role;
import com.bulade.donor.common.page.PageParam;

import java.util.List;
public interface RolesService {

    Long insert(RoleInsertBO roleInsertBO);

    Role selectById(Long id);

    List<Role> listAll(RoleQueryBO roleQueryBO, PageParam pageParam);

    Integer countAll(RoleQueryBO roleQueryBO);

    Integer updateById(RoleUpdateBO roleUpdateBO);

    Integer deleteById(Long id);

    List<Role> listByTypeAndDefault(RoleType type);

}
