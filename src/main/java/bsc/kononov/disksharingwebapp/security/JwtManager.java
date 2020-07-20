package bsc.kononov.disksharingwebapp.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Date;

/**
 * Управление Jwt-токенами
 */
@Component
public class JwtManager implements Serializable {

    private static final long serialVersionUID = 6252467135767561380L;
    private static final String JWT_TOKEN_PREFIX = "JWT ";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.validity}")
    private String jwtValidity;

    public String generateJwtToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + Long.valueOf(jwtValidity)))
                .signWith(SignatureAlgorithm.HS256, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(UserDetails userDetails, String token) {
        String username = getUsernameFromToken(token);

        return username.equals(userDetails.getUsername()) && !isExpired(token);
    }

    private boolean isExpired(String token) {
        Date expirationDate = getAllClaims(token).getExpiration();

        return expirationDate.before(new Date());
    }

    public String getUsernameFromToken(String token) {
        return getAllClaims(token).getSubject();
    }

    public String getUsernameFromHeader(String authHeader) {
        String token = getTokenFromRequestHeader(authHeader);

        if (!token.isEmpty())
            return getUsernameFromToken(token);

        return "";
    }

    public String getTokenFromRequestHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith(JWT_TOKEN_PREFIX))
            return authHeader.substring(JWT_TOKEN_PREFIX.length());

        return "";
    }

    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }
}
