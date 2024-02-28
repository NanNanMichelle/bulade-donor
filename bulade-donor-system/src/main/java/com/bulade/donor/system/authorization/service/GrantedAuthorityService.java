package com.bulade.donor.system.authorization.service;

import com.bulade.donor.system.authorization.enums.RoleType;
import java.util.List;

public interface GrantedAuthorityService {

    List<Long> authorities(Long userId, RoleType type);

}
