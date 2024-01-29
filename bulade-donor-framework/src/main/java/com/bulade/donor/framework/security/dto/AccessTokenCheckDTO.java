package com.bulade.donor.framework.security.dto;

import lombok.Data;

import java.util.List;

@Data
public class AccessTokenCheckDTO {

    /**
     * 用户编号
     */
    private Long userId;
    /**
     * 用户类型
     */
    private Integer userType;

    /**
     * 授权范围的数组
     */
    private List<String> scopes;

    public static AccessTokenCheckDTO of(Long userId, Integer userType, List<String> scopes) {
        var accessTokenCheck = new AccessTokenCheckDTO();
        accessTokenCheck.setUserId(userId);
        accessTokenCheck.setUserType(userType);
        accessTokenCheck.setScopes(scopes);
        return accessTokenCheck;
    }
}
