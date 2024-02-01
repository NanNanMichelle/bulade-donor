package com.bulade.donor.system.service;

import com.bulade.donor.system.model.Admin;

public interface AdminsService {

    Admin selectByUsername(String username);

    Admin selectById(Long id);

    void updateUserLogin(Long userId, String clientIP);
}
