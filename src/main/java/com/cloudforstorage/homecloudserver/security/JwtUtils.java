package com.cloudforstorage.homecloudserver.security;

import com.cloudforstorage.homecloudserver.bean.UserBean;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private String jwtSecret="kirankodaliasdfasdfasdfasdfasdfasdfasdfasdfasdf";

    private int jwtExpirationMs = 10000000;

    private Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    // Method to generate JWT
    public String generateJwt(Authentication authentication){
        UserBean userBean = (UserBean) authentication.getPrincipal();
        return Jwts.builder()
                .setSubject(userBean.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
                .signWith(key)
                .compact();
    }

    public boolean validateJwt(String jwt) {
        return true;
    }

    // method to validate JWT token

    // MEthod to get username from jwt token
    public String getUserNameFromJwt(String jwt){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody().getSubject();
    }
}