package com.chatop.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    private final JwtAuthFilter jwtAuthenticationFilter;

    private final CustomAuthEntryPoint authEntryPoint;

    private final CustomAccessDeniedHandler accessDeniedHandler;


    public SecurityConfig(CustomUserDetailsService userDetailsService, JwtAuthFilter jwtAuthenticationFilter,
            CustomAuthEntryPoint authEntryPoint, CustomAccessDeniedHandler accessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.jwtAuthenticationFilter = jwtAuthenticationFilter;
        this.authEntryPoint = authEntryPoint;
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .csrf(csrf -> csrf.disable())          
            .sessionManagement(sessionManagement -> 
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(auth -> {
                auth.requestMatchers("/api/pictures/**").anonymous();
                auth.requestMatchers("/api/auth/register", "/api/auth/login").permitAll();
                auth.anyRequest().authenticated();
            }).userDetailsService(userDetailsService)
            .exceptionHandling(exception -> {
                exception.authenticationEntryPoint(authEntryPoint);
                exception.accessDeniedHandler(accessDeniedHandler);
            })
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
