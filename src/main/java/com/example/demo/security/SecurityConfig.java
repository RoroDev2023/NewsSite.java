package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configurer ->
                        configurer
                                .requestMatchers(HttpMethod.GET, "/users").hasAnyRole("EMPLOYEE", "ADMIN")
                                .requestMatchers(HttpMethod.GET, "/users/{id}").hasAnyRole("EMPLOYEE", "MANAGER", "ADMIN")
                                .requestMatchers(HttpMethod.POST, "/users/create").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.PUT, "/users/**").hasRole("MANAGER")
                                .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                )
                .httpBasic(Customizer.withDefaults())  // Using HTTP Basic Authentication
                .csrf(csrf -> csrf.disable());         // Disable CSRF for stateless APIs

        return http.build();
    }
}




/*.requestMatchers(HttpMethod.GET, "/users").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/users/{id}").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/users/create/{username}/{password}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/users/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/users/**").hasRole("ADMIN")
                        .requestMatchers("/swagger-ui/**").permitAll()
                        .anyRequest().authenticated()

 */










