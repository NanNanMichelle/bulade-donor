package com.bulade.donor.authorization.dao;

import com.bulade.donor.authorization.enums.RoleType;
import com.bulade.donor.authorization.model.Role;
import com.bulade.donor.common.page.PageParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
@Mapper
public interface RolesMapper {

    Integer insert(Role role);

    Role selectById(Long id);

    List<Role> listAll(Role role, Boolean isDeleted, PageParam pageParam);

    Integer countAll(Role role, Boolean isDeleted);

    Integer updateById(Role role);

    Integer deleteById(Long id);

    List<Role> listByTypeAndDefault(RoleType type);

}
