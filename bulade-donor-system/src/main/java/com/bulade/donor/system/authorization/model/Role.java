package com.bulade.donor.system.authorization.model;

import com.bulade.donor.system.authorization.enums.RoleType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Role {

    /**
     * 主键
     */
    private Long id;

    /**
     * 名称
     */
    private String name;

    /**
     * 类型(0: 平台管理端、1: 商户管理端、2: 供应商管理端、3: 用户端)
     */
    private RoleType type;

    /**
     * 是否默认角色(0: 否、1: 是) 是默认角色的数据，会在用户注册时自动分配角色
     */
    private Boolean isDefault;

    /**
     * 创建时间
     */
    private LocalDateTime createdAt;

    /**
     * 修改时间
     */
    private LocalDateTime updatedAt;

    /**
     * 删除时间
     */
    private LocalDateTime deletedAt;

}
