package com.ultrasoft.ultracomplaint.responsebody;

import com.ultrasoft.ultracomplaint.enums.UserRoles;
import java.time.LocalDateTime;

public class Login_Response_Body {

    private String authToken;

    private LocalDateTime loginDateTime;

    private String name;

    private UserRoles role;

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public LocalDateTime getLoginDateTime() {
        return loginDateTime;
    }

    public void setLoginDateTime(LocalDateTime loginDateTime) {
        this.loginDateTime = loginDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserRoles getRole() {
        return role;
    }

    public void setRole(UserRoles role) {
        this.role = role;
    }
}
