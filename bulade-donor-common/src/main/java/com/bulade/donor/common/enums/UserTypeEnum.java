package com.bulade.donor.common.enums;

import com.bulade.donor.common.core.IntArrayValuable;
import com.bulade.donor.common.exception.SystemException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 全局用户类型枚举
 */
@AllArgsConstructor
@Getter
public enum UserTypeEnum implements IntArrayValuable {

    MEMBER(1, "会员"), // 面向 c 端，普通用户
    ADMIN(2, "管理员"); // 面向 b 端，管理后台

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(UserTypeEnum::getValue).toArray();

    /**
     * 类型
     */
    private final Integer value;
    /**
     * 类型名
     */
    private final String name;

    public static UserTypeEnum valueOf(Integer value) {
        for (var item : UserTypeEnum.values()) {
            if (item.value.equals(value)) {
                return item;
            }
        }
        throw new SystemException("code is illegal");
    }

    @Override
    public int[] array() {
        return ARRAYS;
    }
}
