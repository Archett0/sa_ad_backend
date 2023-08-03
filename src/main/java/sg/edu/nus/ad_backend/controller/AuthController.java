package sg.edu.nus.ad_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sg.edu.nus.ad_backend.security.model.LoginRequest;
import sg.edu.nus.ad_backend.security.model.LoginToken;
import sg.edu.nus.ad_backend.service.IAuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginToken> login(@RequestBody LoginRequest request) {
        return authService.attemptLogin(request.getUsername(), request.getPassword());
    }
}
