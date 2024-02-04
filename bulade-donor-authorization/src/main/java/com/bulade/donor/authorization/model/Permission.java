package com.bulade.donor.authorization.model;

import com.bulade.donor.authorization.enums.PermissionType;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Permission {

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

    /**
     * 优先级(数字越小优先级越高)
     */
    private Integer priority;

    /**
     * 类型(0: 需要角色授权url、1: 无需登录的url)
     */
    private PermissionType type;

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
