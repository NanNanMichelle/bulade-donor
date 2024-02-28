package com.bulade.donor.system.authorization.bo;

import com.bulade.donor.system.authorization.enums.RoleType;
import com.bulade.donor.system.authorization.model.Role;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleInsertBO {

    private String name;

    private RoleType type;

    private Boolean isDefault;

    public Role toRole() {
        return new Role()
            .setName(name)
            .setType(type)
            .setIsDefault(isDefault);
    }

}
