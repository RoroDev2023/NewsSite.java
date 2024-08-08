package com.example.demo.config;

import com.example.demo.config.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filter for processing JWT authentication from HTTP requests.
 * This filter extracts the JWT token from the request header,
 * validates it, and sets the authentication in the security context.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    /**
     * The HTTP header that contains the JWT token.
     */
    private static final String AUTH_HEADER = "Authorization";

    /**
     * The prefix for the JWT token in the HTTP header.
     */
    private static final String BEARER_PREFIX = "Bearer ";

    /**
     * The length of the JWT token prefix.
     */
    private static final int BEARER_PREFIX_LENGTH = BEARER_PREFIX.length();

    /**
     * Utility class for JWT operations.
     */
    private final JwtUtil jwtUtil;

    /**
     * Service for loading user details.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Filters requests and sets authentication if a valid JWT is present.
     *
     * @param request  the HTTP request.
     * @param response the HTTP response.
     * @param chain    the filter chain.
     * @throws ServletException if a servlet error occurs.
     * @throws IOException      if an I/O error occurs.
     */
    @Override
    protected void doFilterInternal(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final FilterChain chain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader(AUTH_HEADER);
        String username = null;
        String jwt = null;

        if (authorizationHeader != null
                && authorizationHeader.startsWith(BEARER_PREFIX)) {
            jwt = authorizationHeader.substring(BEARER_PREFIX_LENGTH);
            username = jwtUtil.extractUsername(jwt);
        }

        if (username != null && SecurityContextHolder.
                getContext().getAuthentication() == null) {
            final UserDetails userDetails = this.
                    userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                final UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null,
                                userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource()
                        .buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        chain.doFilter(request, response);
    }
}



