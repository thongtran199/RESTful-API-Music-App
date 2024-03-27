package com.example.demo.authentication;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;



import java.util.Calendar;
import java.util.Date;

@Component
@Slf4j
@Log
public class JwtProviderToken {
    private final String JWT_SECRET = "ngocthuong2022";

    private final long JWT_EXPIRATION = 604800000L;


    public String generateToken(CustomUserDetails userDetails) {
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        Date expiryDate = new Date(now.getTime() + JWT_EXPIRATION);

        return Jwts.builder()
                .setSubject(Long.toString(userDetails.getUser().getId()))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
    }

    public Long getUserIdFromJWT(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody();

        return Long.parseLong(claims.getSubject());
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException ex) {
            log.info("Invalid JWT token");
        } catch (ExpiredJwtException ex) {
            log.info("Expired JWT token");
        } catch (UnsupportedJwtException ex) {
            log.info("Unsupported JWT token");
        } catch (IllegalArgumentException ex) {
            log.info("JWT claims string is empty.");
        }
        return false;
    }
    }