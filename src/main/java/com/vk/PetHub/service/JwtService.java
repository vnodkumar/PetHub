package com.vk.PetHub.service;



import com.vk.PetHub.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Service
public class JwtService {

    @Value("${JWT_SECRET}")
    private String SECRET;

    public String generateToken(String email) {

        Map<String,Object> claims = new HashMap<>();

        return createToken(claims, email);
    }

    private String createToken(Map<String, Object> claims,String email) {
        return Jwts
                .builder()
                .claims(claims)
                .subject(email)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*10))
                .signWith(getSigningKey())
                .compact();
    }

    private SecretKey getSigningKey() {
        if (SECRET == null || SECRET.isBlank()) {
            throw new IllegalStateException("JWT_SECRET environment variable is missing or empty!");
        }
        byte[] byteKey = Decoders.BASE64URL.decode(SECRET);
        return Keys.hmacShaKeyFor(byteKey);
    }

    public String extractEmail(String token){
        return extractClaim(token,Claims::getSubject);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token,Claims::getExpiration);
    }

    private <T> T extractClaim(String token, Function<Claims,T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

}
