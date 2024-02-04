package com.bulade.donor.authorization.bo;

import com.bulade.donor.authorization.model.RolePermission;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RolePermissionQueryBO {

    private Long roleId;

    private Long permissionId;

    private Boolean isDeleted;

    public RolePermission toRolePermission() {
        return new RolePermission()
            .setRoleId(roleId)
            .setPermissionId(permissionId);
    }

}
