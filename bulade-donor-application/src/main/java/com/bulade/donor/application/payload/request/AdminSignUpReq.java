package com.bulade.donor.application.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class AdminSignUpReq {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    public static AdminSignUpReq of(String username, String password) {
        return new AdminSignUpReq()
            .setUsername(username)
            .setPassword(password);
    }

}
