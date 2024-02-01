package com.bulade.donor.system.service.impl;

import com.bulade.donor.system.dao.AdminsMapper;
import com.bulade.donor.system.model.Admin;
import com.bulade.donor.system.service.AdminsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Slf4j
@Service
public class AdminsServiceImpl implements AdminsService {

    @Resource
    private AdminsMapper adminsMapper;

    @Override
    public Admin selectByUsername(String username) {
        return adminsMapper.selectByUsername(username);
    }

    @Override
    public Admin selectById(Long id) {
        var admin = adminsMapper.selectById(id);
        Assert.notNull(admin, "admin不存在id[%s]".formatted(id));
        return admin;
    }

    @Override
    public void updateUserLogin(Long userId, String clientIP) {

    }

}
