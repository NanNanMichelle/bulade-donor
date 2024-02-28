package com.bulade.donor.system.authorization.bo;

import com.bulade.donor.system.authorization.enums.RoleType;
import com.bulade.donor.system.authorization.model.UserRole;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserRoleQueryBO {

    private Long userId;

    private Long roleId;

    private RoleType type;

    private Boolean isDeleted;

    public UserRole toUserRole() {
        return new UserRole()
            .setUserId(userId)
            .setRoleId(roleId)
            .setType(type);
    }

}
