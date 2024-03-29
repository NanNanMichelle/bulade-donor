package com.bulade.donor.system.authorization.bo;

import com.bulade.donor.system.authorization.model.Permission;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PermissionQueryBO {

    private String name;

    private String controller;

    private Boolean isDeleted;

    public Permission toPermission() {
        return new Permission()
            .setName(name)
            .setController(controller);
    }

}
