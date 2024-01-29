package com.bulade.donor.system.dao;

import com.bulade.donor.system.model.Admin;

public interface AdminsMapper {

    Integer insert(Admin admin);

    Admin selectByUsername(String username);

    Admin selectById(Long id);

}
