package com.bulade.donor.authorization.enums;

import com.bulade.donor.common.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum PermissionType {

    /**
     * AUTHORIZATION.ENUMS.PERMISSIONTYPE
     */
    AUTHORIZATION(0, "需要角色授权url"),

    NO_SIGN_IN(1, "无需登录的url");

    @EnumValue
    private final Integer code;

    private final String name;

    PermissionType(Integer code, String name) {
        this.code = code;
        this.name = name;
    }

}
