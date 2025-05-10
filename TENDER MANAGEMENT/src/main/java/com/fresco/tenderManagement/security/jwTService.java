package com.fresco.tenderManagement.security;

import static java.lang.System.currentTimeMillis;

import java.security.Signature;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class jwTService {

    private static final Logger logger = LoggerFactory.getLogger(jwTService.class);
    

    private final String SECRET_KEY = "universal_secret_key_123456789";
    private final long EXPIRATION_TIME = 5 * 60 * 60 * 1000;

    @Autowired
    private UserDetailsService us;

    private byte[] getSignKey() {
        // Log Method Start (Green)
        logger.info("Method [getSignKey] started in jwTService class".toUpperCase());
        return SECRET_KEY.getBytes();
    }

    private Map<String, Object> createClaims(UserDetails f) {
        // Log Method Start (Green)
        logger.info("Method [createClaims] started in jwTService class for user: {}".toUpperCase(), f.getUsername());
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", f.getAuthorities());
        return claims;
    }

    public String generateToken(UserDetails s) {
        // Log Method Start (Green)
        logger.info("Method [generateToken] started in jwTService class for user: {}".toUpperCase(), s.getUsername());
        return Jwts.builder()
                .setClaims(createClaims(s))
                .setSubject(s.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public Claims extractAlClaims(String token) {
        // Log Method Start (Green)
        logger.info("Method [extractAlClaims] started in jwTService class".toUpperCase());
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaims(String token, Function<Claims, T> e) {
        // Log Method Start (Green)
        logger.info("Method [extractClaims] started in jwTService class".toUpperCase());
        return e.apply(extractAlClaims(token));
    }

    public String exctus(String Token) {
        // Log Method Start (Green)
        logger.info("Method [exctus] started in jwTService class".toUpperCase());
        return extractClaims(Token, Claims::getSubject);
    }

    public Date extractdate(String Token) {
        // Log Method Start (Green)
        logger.info(">Method [extractdate] started in jwTService class".toLowerCase());
        return extractClaims(Token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        // Log Method Start (Green)
        logger.info("Method [isTokenExpired] started in jwTService class".toUpperCase());
        return extractdate(token).before(new Date());
    }

    public boolean isTokenvalid(String token, UserDetails g) {
        // Log Method Start (Green)
        logger.info("Method [isTokenvalid] started in jwTService class for user: {}".toUpperCase(), g.getUsername().toUpperCase());
        return isTokenExpired(token) && us.loadUserByUsername(g.getUsername()).equals(exctus(token));
    }

}
