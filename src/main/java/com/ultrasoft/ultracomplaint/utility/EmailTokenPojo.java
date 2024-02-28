package com.ultrasoft.ultracomplaint.utility;

import com.ultrasoft.ultracomplaint.enums.UserRoles;

public class EmailTokenPojo {
    private String email;
    private UserRoles type;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserRoles getType() {
        return type;
    }

    public void setType(UserRoles type) {
        this.type = type;
    }
}
