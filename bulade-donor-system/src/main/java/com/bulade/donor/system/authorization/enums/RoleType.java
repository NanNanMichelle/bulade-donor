package com.bulade.donor.system.authorization.enums;

import com.bulade.donor.common.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum RoleType {

    /**
     * 枚举内容
     */
    PLATFORM(0, "平台管理端"),

    USER(3, "用户端v1");

    @EnumValue
    private final Integer code;

    private final String name;

    RoleType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

}
