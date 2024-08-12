package com.example.demo.config.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utility class for JWT (JSON Web Token) operations
 * validation, and claims extraction.
 */
@Component
public final class JwtUtil {

    /**
     * The secret key used for signing the JWT token.
     */
    private static final String SECRET_KEY =
            "19495372e6e6ee8c43ae3eeb0e224eb74aaa25ccb8074c1889513c543d318569";

    /**
     * The expiration time for the JWT token in milliseconds.
     */
    private static final long EXPIRATION_TIME_MS = 1000 * 60 * 60 * 10;

    /**
     * Generates a JWT token based on the provided user details.
     *
     * @param userDetails the user details for the generated token
     * @return a JWT token as a String.
     */
    public String generateToken(final UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Creates a JWT token with the given claims and subject.
     *
     * @param claims the claims to be included in the token.
     * @param subject the subject of the token.
     * @return a JWT token as a String.
     */
    private String createToken(
            final Map<String, Object> claims, final String subject) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()
                        + EXPIRATION_TIME_MS))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    /**
     * Validates a JWT token by checking if
     *      it is expired and if the username matches.
     *
     * @param token the JWT token to be validated.
     * @param userDetails the user details to
     *      be checked against the token.
     * @return true if the token is valid, false otherwise.
     */
    public Boolean validateToken(
            final String token, final UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(
                userDetails.getUsername()) && !isTokenExpired(token));
    }

    /**
     * Extracts the username from a JWT token.
     *
     * @param token the JWT token from which the username is extracted.
     * @return the username as a String.
     */
    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Checks if a JWT token is expired.
     *
     * @param token the JWT token to be checked.
     * @return true if the token is expired, false otherwise.
     */
    private Boolean isTokenExpired(final String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extracts the expiration date from a JWT token.
     *
     * @param token the JWT token from which the expiration date is extracted.
     * @return the expiration date.
     */
    public Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extracts a claim from a JWT token.
     *
     * @param token the JWT token from which the claim is extracted.
     * @param claimsResolver a function to resolve the claim from the claims.
     * @param <T> the type of the claim.
     * @return the claim value.
     */
    public <T> T extractClaim(
            final String token, final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from a JWT token.
     *
     * @param token the JWT token from which the claims are extracted.
     * @return the claims.
     */
    private Claims extractAllClaims(final String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}


