package su.morevq.pkmn.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtService {

    private final UserDetailsService userDetailsService;

    @Value("${token.secret}")
    private String SECRET_KEY;

    @Value("${token.expiration}")
    private long TOKEN_EXPIRATION_MINUTES;

    private Algorithm algorithm;

    @PostConstruct
    public void init() {
        algorithm = Algorithm.HMAC256(SECRET_KEY);
    }

    public String createJwt(String username, GrantedAuthority authority) {
        log.info("Creating JWT for user: {}", username);

        return JWT.create()
                .withIssuer("pkmn")
                .withSubject(username)
                .withClaim("authority", authority.getAuthority())
                .withExpiresAt(Date.from(Instant.now().plus(TOKEN_EXPIRATION_MINUTES, ChronoUnit.MINUTES)))
                .sign(algorithm);
    }

    public DecodedJWT verify(String jwt) {
        try {
            JWTVerifier verifier = JWT
                    .require(algorithm)
                    .withIssuer("pkmn")
                    .build();

            log.info("JWT info: {}", jwt);

            DecodedJWT decodedJWT = verifier.verify(jwt);

            log.info("Decoded JWT subject: {}", decodedJWT.getSubject());

            if (Objects.isNull(userDetailsService.loadUserByUsername(decodedJWT.getSubject()))) {
                log.error("User not found");
                return null;
            }

            return decodedJWT;
        } catch (JWTVerificationException e) {
            log.error("JWT verification failed: {}", e.getMessage());
            return null;
        }
    }
}
