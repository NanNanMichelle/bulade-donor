package com.bulade.donor.authorization.bo;

import com.bulade.donor.authorization.model.Permission;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class PermissionUpdateBO {

    @NotNull
    private Long id;

    @NotEmpty
    private String name;

    @NotEmpty
    private String controller;

    @NotNull
    private Integer priority;

    public Permission toPermission() {
        return new Permission()
            .setId(id)
            .setName(name)
            .setController(controller)
            .setPriority(priority);
    }

}
