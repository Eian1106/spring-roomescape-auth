package roomescape.common.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider {

    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final Duration ACCESS_TOKEN_VALIDITY = Duration.ofHours(1);
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(
            "roomescape-auth-token-secret-key-for-jjwt-hs256".getBytes(StandardCharsets.UTF_8)
    );

    public String createToken(Long userId) {
        Instant now = Instant.now();
        Instant expiration = now.plus(ACCESS_TOKEN_VALIDITY);

        return Jwts.builder()
                .setSubject(String.valueOf(userId))
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expiration))
                .signWith(SECRET_KEY)
                .compact();
    }

    public Optional<Long> extractUserId(HttpServletRequest request) {
        String authorization = request.getHeader(AUTHORIZATION_HEADER);
        if (authorization == null || !authorization.startsWith(BEARER_PREFIX)) {
            return Optional.empty();
        }

        String token = authorization.substring(BEARER_PREFIX.length());
        return parseUserId(token);
    }

    private Optional<Long> parseUserId(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return Optional.of(Long.valueOf(claims.getSubject()));
        } catch (JwtException | IllegalArgumentException exception) {
            return Optional.empty();
        }
    }
}
