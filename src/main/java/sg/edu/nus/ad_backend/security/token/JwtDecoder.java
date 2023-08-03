package sg.edu.nus.ad_backend.security.token;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtDecoder {
    private final JwtProperties jwtProperties;

    public DecodedJWT decode(String token) {
        return JWT
                // get secret
                .require(Algorithm.HMAC256(jwtProperties.getSecretKey()))
                // more requirements could be done here
                .build()
                .verify(token);
    }
}