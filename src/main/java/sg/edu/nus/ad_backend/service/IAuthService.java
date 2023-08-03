package sg.edu.nus.ad_backend.service;

import org.springframework.http.ResponseEntity;
import sg.edu.nus.ad_backend.security.model.LoginToken;

public interface IAuthService {
    ResponseEntity<LoginToken> attemptLogin(String username, String password);
}
