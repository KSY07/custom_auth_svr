package com.scarf.authsvr.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.scarf.authsvr.Service.ScarfUserDetails;

import java.util.Date;

import io.jsonwebtoken.*;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private static final long serialVersionUID = 2L;

    @Value("${jwt.secret}")
    private String jwtsecret;

    @Value("${jwt.ExpirationMs}")
    private long jwtExpriationMs;

    public String generateAuthToken(Authentication authentication) {
        ScarfUserDetails scarfUserDetails = (ScarfUserDetails) authentication.getPrincipal();

        return Jwts.builder()
        .setSubject(scarfUserDetails.getUsername())
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpriationMs))
        .signWith(SignatureAlgorithm.HS512, jwtsecret)
        .compact();
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
        .setSubject(username)
        .setIssuedAt(new Date())
        .setExpiration(new Date((new Date()).getTime() + jwtExpriationMs))
        .signWith(SignatureAlgorithm.HS512, jwtsecret)
        .compact();
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(jwtsecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtsecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT: {} ", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT Token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

}
