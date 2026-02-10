package org.example.visitme.security;

import org.example.visitme.control.exceptions.AuthenticationException;
import org.example.visitme.control.exceptions.ErrorException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    private final SecurityFilter securityFilter;

    // Temporary removed ,
    private final static String[] PUBLIC_ENDPOINTS = { "/v1/h2-console/**", "/v1/home/**", "/v1/swagger-ui/**", "/v3/api-docs/**" };

    SecurityConfiguration(SecurityFilter securityFilter) {
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityfilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            // Disabele CSRF protection for the H2 console
            .csrf(csrf -> csrf.disable())
            // Configure endpoint authorization
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers(PUBLIC_ENDPOINTS).permitAll() // Public endpoints
                .anyRequest().authenticated() // All other endpoints require authentication
            )
            // Stateless session (required for JWT)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            // Disable FrameOptionsConfigure (needed for H2 console)
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(handling -> handling
                .authenticationEntryPoint(new AuthenticationException(new ErrorException(
                "Header \"Authorization\".", "Token invalid. You must to sign in."))));
        return httpSecurity.build();
    }
}
