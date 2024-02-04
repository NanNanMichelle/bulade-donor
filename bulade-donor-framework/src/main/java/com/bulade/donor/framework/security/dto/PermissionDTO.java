package com.bulade.donor.framework.security.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissionDTO {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * controller地址
     */
    private String controller;

    public static PermissionDTO of(Long id, String name, String controller){
        var permission = new PermissionDTO();
        permission.setId(id);
        permission.setName(name);
        permission.setController(controller);
        return permission;
    }

}
