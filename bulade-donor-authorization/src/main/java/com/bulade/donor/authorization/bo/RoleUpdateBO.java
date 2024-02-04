package com.bulade.donor.authorization.bo;

import com.bulade.donor.authorization.enums.RoleType;
import com.bulade.donor.authorization.model.Role;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class RoleUpdateBO {

    private Long id;

    private String name;

    private RoleType type;

    private Boolean isDefault;

    public Role toRole() {
        return new Role()
            .setId(id)
            .setName(name)
            .setType(type)
            .setIsDefault(isDefault);
    }

}
