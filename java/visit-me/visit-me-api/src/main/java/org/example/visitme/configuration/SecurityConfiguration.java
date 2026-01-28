package org.example.visitme.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    private final String H2_CONSOLE_ENDPOINT = "/v1/h2-console/**";

    private final String HOME_ENDPOINTS = "/v1/home/**";
    
    private final String HEALTH_ENDPOINT = "/v1/health";

    private final String SIGN_IN_ENDPOINT = "/v1/users/sign-in";
    
    private final String SIGN_UP_ENDPOINT = "/v1/users/sign-up";

    // private final String ERROR_ENDPOINT = "/error";

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrf -> csrf.ignoringRequestMatchers(H2_CONSOLE_ENDPOINT, HOME_ENDPOINTS, SIGN_IN_ENDPOINT, SIGN_UP_ENDPOINT))
            .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
            .cors(cors -> cors.disable())
            .authorizeHttpRequests((authorize) -> authorize.requestMatchers(
                H2_CONSOLE_ENDPOINT, HOME_ENDPOINTS, HEALTH_ENDPOINT, SIGN_IN_ENDPOINT, SIGN_UP_ENDPOINT).permitAll().anyRequest().authenticated());
        return httpSecurity.build();
    }
}
