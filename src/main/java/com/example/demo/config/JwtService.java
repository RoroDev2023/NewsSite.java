package com.example.demo.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.function.Function;

/**
 * Service for handling JSON Web Token (JWT) operations such as extracting
 * claims and usernames from tokens.
 */
@Service
public final class JwtService {

    /**
     * Extracts the username from the given JWT token.
     *
     * @variable SECRET_KEY the S
     */

    private static final String SECRET_KEY =
            "3f8573a94489c3edfe67df14f7f0e6150cd56b7538fb9c302fae355ccfd120b7";

    /**
     * Extracts the username from the given JWT token.
     *
     * @param token the JWT token from which to extract the username
     * @return the extracted username
     */
    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extracts a specific claim from the given JWT token.
     *
     * @param token the JWT token from which to extract the claim
     * @param claimsResolver a function to extract the claim
     *                       from the Claims object
     * @param <T> the type of the claim to be extracted
     * @return the extracted claim
     */
    public <T> T extractClaim(final String token,
                              final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extracts all claims from the given JWT token.
     *
     * @param token the JWT token from which to extract all claims
     * @return the extracted claims
     */
    private Claims extractAllClaims(final String token) {
        return Jwts
                .parser()
                .setSigningKey(getSigninKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Retrieves the signing key used to validate JWT tokens.
     *
     * @return the signing key
     */
    private Key getSigninKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
