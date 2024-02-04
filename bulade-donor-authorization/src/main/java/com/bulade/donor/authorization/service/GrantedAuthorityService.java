package com.bulade.donor.authorization.service;

import com.bulade.donor.authorization.enums.RoleType;
import java.util.List;

public interface GrantedAuthorityService {

    List<Long> authorities(Long userId, RoleType type);

}
