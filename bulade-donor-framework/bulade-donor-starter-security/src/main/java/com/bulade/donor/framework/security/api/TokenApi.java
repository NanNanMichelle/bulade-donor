package com.bulade.donor.framework.security.api;

import com.bulade.donor.framework.security.dto.AccessTokenCheckDTO;

public interface TokenApi {

    /**
     * 校验访问令牌
     *
     * @param accessToken 访问令牌
     * @return 访问令牌的信息
     */
    AccessTokenCheckDTO checkAccessToken(String accessToken);

}
