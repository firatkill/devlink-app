package com.worksOnLocal.DevLink.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;

@Service
public class JWTService {

    @Value("${app.jwt.secret_key}")
    private String key;

    @Value("${app.jwt.expiration}")
    private String expiration;


    // generateToken, isTokenValid , isTokenExpired, extractUsername, extractAllClaims, getSigningKey

    public String generateToken(Map<String,Object> extraClaims, String username) {
        return Jwts.builder().claims(extraClaims)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+Long.parseLong(expiration)))
                .subject(username)
                .signWith(getSigningKey())
                .compact();
    }
    public String generateToken(String username){
        return generateToken(Map.of(),username);
    }

    public Claims extractAllClaims(String token){
        return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload();
    }
    public boolean isTokenExpired(String token){
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    public String extractUsername(String token){
        return extractAllClaims(token).getSubject();
    }

    public boolean isTokenValid(String token){
        String username=extractUsername(token);

        return !username.isEmpty() && !isTokenExpired(token);

    }

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(key.getBytes());

    }


}
