package sg.edu.nus.ad_backend.security.model;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
