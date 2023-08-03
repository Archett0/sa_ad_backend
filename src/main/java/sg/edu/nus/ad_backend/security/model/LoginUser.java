package sg.edu.nus.ad_backend.security.model;

import lombok.Data;

@Data
public class LoginUser {
    private Long userId;
    private String username;
    private String password;
    private String role;
}
