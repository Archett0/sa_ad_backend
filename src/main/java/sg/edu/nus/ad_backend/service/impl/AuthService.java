package sg.edu.nus.ad_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import sg.edu.nus.ad_backend.security.model.LoginToken;
import sg.edu.nus.ad_backend.security.principal.UserPrincipal;
import sg.edu.nus.ad_backend.security.token.JwtIssuer;
import sg.edu.nus.ad_backend.service.IAuthService;

@Service
@RequiredArgsConstructor
public class AuthService implements IAuthService {
    private final JwtIssuer jwtIssuer;
    private final AuthenticationManager authenticationManager;
    @Override
    public ResponseEntity<LoginToken> attemptLogin(String username, String password) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        var principal = (UserPrincipal) authentication.getPrincipal();

        var roles = principal.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        var token = jwtIssuer.issue(principal.getUserId(), principal.getUsername(), roles);
        return ResponseEntity.ok(LoginToken.builder().accessToken(token).build());
    }
}
