package com.example.demo.security;

import com.example.demo.config.util.JwtUtil;
import com.example.demo.config.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

/**
 * Configuration class for security settings.
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    /**
     * Service to load user-specific data.
     */
    private final UserDetailsService userDetailsService;

    /**
     * Utility class for JWT operations.
     */
    private final JwtUtil jwtUtil;

    /**
     * Configures the security filter chain.
     *
     * @param http the {@link HttpSecurity} to modify
     * @return the {@link SecurityFilterChain}
     * @throws Exception if an error occurs
     */
    @Bean
    public SecurityFilterChain securityFilterChain(
            final HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(
                                new AntPathRequestMatcher("/register"),
                                new AntPathRequestMatcher("/login"),
                                new AntPathRequestMatcher("/v3/api-docs/**"),
                                new AntPathRequestMatcher("/swagger-ui/**"),
                                new AntPathRequestMatcher("/oauth2/**")
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        http.addFilterBefore(jwtRequestFilter(),
                UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * Creates a bean for the JWT request filter.
     *
     * @return the {@link JwtRequestFilter}
     */
    @Bean
    public JwtRequestFilter jwtRequestFilter() {
        return new JwtRequestFilter(jwtUtil, userDetailsService);
    }

    /**
     * Creates a bean for the password encoder.
     *
     * @return the {@link PasswordEncoder}
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates a bean for the authentication manager.
     *
     * @param authenticationConfiguration
     * the {@link AuthenticationConfiguration}
     * @return the {@link AuthenticationManager}
     * @throws Exception if an error occurs
     */
    @Bean
    public AuthenticationManager authenticationManager(
            final AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}











