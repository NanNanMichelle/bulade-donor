package com.bulade.donor.authorization.bo;

import com.bulade.donor.authorization.enums.PermissionType;
import com.bulade.donor.authorization.model.Permission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PermissionInsertBO {

    @NotEmpty
    private String name;

    @NotEmpty
    private String controller;

    @NotNull
    private Integer priority;

    @NotNull
    private PermissionType type;

    public Permission toPermission() {
        return new Permission()
            .setName(name)
            .setController(controller)
            .setPriority(priority)
            .setType(type);
    }

}
