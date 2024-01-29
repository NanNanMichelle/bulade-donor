package com.bulade.donor.application.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AdminSignInReq {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public static AdminSignInReq of(String username, String password) {
        return new AdminSignInReq()
                .setUsername(username)
                .setPassword(password);
    }

}
