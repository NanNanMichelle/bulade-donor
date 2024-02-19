package com.bulade.donor.common.enums;

import com.bulade.donor.common.core.IntArrayValuable;
import com.bulade.donor.common.exception.SystemException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum UserType implements IntArrayValuable {

    MEMBER(1, "会员"),

    ADMIN(2, "管理员");

    public static final int[] ARRAYS = Arrays.stream(values()).mapToInt(UserType::getCode).toArray();

    /**
     * 类型
     */
    private final Integer code;

    /**
     * 类型名
     */
    private final String name;

    public static UserType valueOf(Integer value) {
        for (var item : UserType.values()) {
            if (item.code.equals(value)) {
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
