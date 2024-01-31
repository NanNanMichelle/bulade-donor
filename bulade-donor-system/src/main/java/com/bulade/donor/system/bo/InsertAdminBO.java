package com.bulade.donor.system.bo;

import lombok.Data;
import lombok.experimental.Accessors;



@Data
@Accessors(chain = true)
public class InsertAdminBO {

    private String username;

    private String password;

    public static InsertAdminBO of(String username, String password) {
        return new InsertAdminBO()
            .setUsername(username)
            .setPassword(password);
    }

}
