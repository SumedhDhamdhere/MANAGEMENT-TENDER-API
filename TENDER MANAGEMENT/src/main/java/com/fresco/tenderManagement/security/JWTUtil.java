package com.fresco.tenderManagement.security;

import com.fresco.tenderManagement.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.function.Function;

@Component
public class JWTUtil {

    private static final long serialVersionUID = 654352132132L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5 hours

    private final String secretKey = "randomkey123";
      private static final Logger logger = LoggerFactory.getLogger(JWTUtil.class);
    

    @Autowired
    private UserService userService;

    public String getUsernameFromToken(String token) {
        // Log Method Start (Green)
        logger.info("Method [getSUsernameFromToken] started in JWTUtil class".toUpperCase());
          return getClaimFromToken(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        // Log Method Start (Green)
        logger.info("Method [getExpirationDateFromToken] started in JWTUtil class".toUpperCase());
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        // Log Method Start (Green)
        logger.info("Method [getClaimFromToken] started in JWTUtil class".toUpperCase());
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        // Log Method Start (Green)
        logger.info("Method [getAllClaimsFromToken] started in JWTUtil class".toUpperCase());
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        // Log Method Start (Green)
        logger.info("Method [isTokenExpired] started in JWTUtil class<".toUpperCase());
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        // Log Method Start (Green)
        logger.info("Method [generateToken] started in JWTUtil class for user: ".toUpperCase() + userDetails.getUsername().toUpperCase());
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities());
        return doGenerateToken(claims, userDetails.getUsername());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject) {
        // Log Method Start (Green)
        logger.info("Method [doaGenerateToken] started in JWTUtil class for subject: ".toUpperCase() + subject.toUpperCase());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        // Log Method Start (Green)
        logger.info("Method [validateToken] started in JWTUtil class for user: ".toUpperCase() + userDetails.getUsername().toUpperCase());
        final String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
}
