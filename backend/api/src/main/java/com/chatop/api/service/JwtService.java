package com.chatop.api.service;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.chatop.api.model.UserEntity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private final String SECRET_KEY = "d0d6ddeb10060dffd644a39a25dafcec91b4a702ffdb5e06f7ff8f318b3d4217";

    /* Extracting username (email in our case) from claims */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /* Checking if token is valid, or still valid */
    public boolean isValid(String token, UserDetails userDetails) {
        String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    /* Checking if token has already expired */
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /* Extracting expiration date/time from claims */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /* Method to extract a specific claim from claims */
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    /* Extracting all claims from token */
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /* Generating token */
    public String generateToken(UserEntity user) {
        String username = user.getEmail();
        Date currentDate = new Date(System.currentTimeMillis());
        Date expireDate = new Date(System.currentTimeMillis() + 24*60*60*1000);

        String token = Jwts.builder()
            .subject(username)
            .issuedAt(currentDate)
            .expiration(expireDate)
            .signWith(getSigninKey())
            .compact();

        return token;
    }

    /* Getting secret key */
    private SecretKey getSigninKey() {
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }



}
