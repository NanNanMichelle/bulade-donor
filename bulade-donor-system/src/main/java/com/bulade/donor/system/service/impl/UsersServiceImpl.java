package com.bulade.donor.system.service.impl;

import com.bulade.donor.system.dao.UserMapper;
import com.bulade.donor.system.model.User;
import com.bulade.donor.system.service.UsersService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UsersServiceImpl implements UsersService {

    @Resource
    private UserMapper userMapper;

    @Override
    public User selectById(Long id) {
        return userMapper.selectById(id);
    }
}
