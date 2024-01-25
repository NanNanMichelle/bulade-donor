package com.bulade.donor.system.dao;

import com.bulade.donor.system.model.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    User selectById(Long id);
}
