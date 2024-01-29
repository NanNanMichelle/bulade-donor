package com.bulade.donor.application.payload.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthResp {

    @Schema(title = "认证信息")
    private String token;

    public static JwtAuthResp of(String token) {
        return JwtAuthResp.builder().token(token).build();
    }

}
