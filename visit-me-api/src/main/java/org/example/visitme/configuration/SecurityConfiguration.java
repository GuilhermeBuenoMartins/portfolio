package org.example.visitme.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private final String HEALTH_ENDPOINT = "/v1/health";

    private final String SIGN_UP_ENDPOINT = "/v1/users/sign-up";

    // private final String ERROR_ENDPOINT = "/error";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrf -> csrf.disable())
            .cors(cors -> cors.disable())
            .authorizeHttpRequests((authorize) -> authorize.requestMatchers(
                HEALTH_ENDPOINT, SIGN_UP_ENDPOINT).permitAll().anyRequest().authenticated());
        return httpSecurity.build();
    }
}
