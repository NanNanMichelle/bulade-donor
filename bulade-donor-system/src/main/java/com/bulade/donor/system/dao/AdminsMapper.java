package com.bulade.donor.system.dao;

import com.bulade.donor.system.model.Admin;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminsMapper {

    Integer insert(Admin admin);

    Admin selectByUsername(String username);

    Admin selectById(Long id);

}
