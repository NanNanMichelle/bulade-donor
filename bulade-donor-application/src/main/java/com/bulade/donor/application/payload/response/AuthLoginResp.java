package com.bulade.donor.application.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "管理后台 - 登录 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginResp {

    @Schema(description = "访问令牌", requiredMode = Schema.RequiredMode.REQUIRED, example = "happy")
    private String token;

    @Schema(description = "用户类型")
    private Integer userType;

    public static AuthLoginResp of(String token, Integer userType) {
        var resp = new AuthLoginResp();
        resp.setToken(token);
        resp.setUserType(userType);
        return resp;
    }
}
