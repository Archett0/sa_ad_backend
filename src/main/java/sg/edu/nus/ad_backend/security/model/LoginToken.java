package sg.edu.nus.ad_backend.security.model;

import lombok.Builder;
import lombok.Data;

/**
 * The token user gets after successful login
 */
@Data
@Builder
public class LoginToken {
    private final String accessToken;
}
