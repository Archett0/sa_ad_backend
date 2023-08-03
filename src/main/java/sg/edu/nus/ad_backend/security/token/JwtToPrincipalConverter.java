package sg.edu.nus.ad_backend.security.token;

import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import sg.edu.nus.ad_backend.security.principal.UserPrincipal;

import java.util.List;

@Component
public class JwtToPrincipalConverter {
    public UserPrincipal convert(DecodedJWT decodedJWT) {
        return UserPrincipal.builder()
                .userId(Long.valueOf(decodedJWT.getSubject()))
                .username(decodedJWT.getClaim("u").asString())
                .authorities(extractedAuthorities(decodedJWT))
                .build();
    }

    private List<SimpleGrantedAuthority> extractedAuthorities(DecodedJWT jwt) {
        var claim = jwt.getClaim("a");
        if (claim.isNull() || claim.isMissing()) return List.of();
        return claim.asList(SimpleGrantedAuthority.class);
    }
}
