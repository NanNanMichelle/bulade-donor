package com.bulade.donor.system.authorization.bo;

import com.bulade.donor.system.authorization.model.RolePermission;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RolePermissionInsertBO {

    @NotNull
    private Long roleId;

    @NotNull
    private Long permissionId;

    public RolePermission toRolePermission() {
        return new RolePermission()
            .setRoleId(roleId)
            .setPermissionId(permissionId);
    }

}
