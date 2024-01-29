package com.bulade.donor.system.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class Admin {

    private Long id;

    private String username;

    private String password;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public static Admin ofSignIn(String username, String password) {
        return new Admin()
            .setUsername(username)
            .setPassword(password);
    }

    public static Admin ofInsert(String username, String password) {
        return new Admin()
            .setUsername(username)
            .setPassword(password);
    }

}
