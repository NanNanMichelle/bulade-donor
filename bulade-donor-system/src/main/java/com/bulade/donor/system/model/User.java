package com.bulade.donor.system.model;

import lombok.Data;

@Data
public class User {

    private Long id;

    private String userName;

    private String password;

    public static User ofSignIn(String username, String password) {
        var user = new User();
        user.setPassword(password);
        user.setUserName(username);
        return user;
    }

    public static User ofSignIn(Long id, String username, String password) {
        var user = new User();
        user.setId(id);
        user.setPassword(password);
        user.setUserName(username);
        return user;
    }

}
