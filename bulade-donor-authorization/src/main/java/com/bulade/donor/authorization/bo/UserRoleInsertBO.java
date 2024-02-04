package com.bulade.donor.authorization.bo;

import com.bulade.donor.authorization.enums.RoleType;
import com.bulade.donor.authorization.model.UserRole;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserRoleInsertBO {

    private Long userId;

    private Long roleId;

    private RoleType type;

    public UserRole toUserRole() {
        return new UserRole()
            .setUserId(userId)
            .setRoleId(roleId)
            .setType(type);
    }

    public static UserRoleInsertBO of(Long userId, Long roleId, RoleType type) {
        return new UserRoleInsertBO()
            .setUserId(userId)
            .setRoleId(roleId)
            .setType(type);
    }

}
