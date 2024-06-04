package com.chatop.api.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.chatop.api.exception.CustomAccessDeniedHandler;
import com.chatop.api.exception.CustomAuthEntryPoint;

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
            .csrf(AbstractHttpConfigurer::disable)  
            .authorizeHttpRequests(permitted -> 
                permitted.requestMatchers(
                    /* ChaTop App */
                    "/api/auth/register",
                    "/api/auth/login",
                    "/api/pictures/**",
                    /* Swagger */
                    "/v2/api-docs",
                    "/v3/api-docs",
                    "/v3/api-docs/**",
                    "/swagger-ressources",
                    "/swagger-ressources/**",
                    "/configuration/ui",
                    "/configuration/security",
                    "/swagger-ui/**",
                    "/webjars/**",
                    "/swagger-ui.html"
                    ).permitAll())
            .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())        
            .userDetailsService(userDetailsService)
            .sessionManagement(sessionManagement -> 
                sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .exceptionHandling(exception -> {
                exception.authenticationEntryPoint(authEntryPoint);
                exception.accessDeniedHandler(accessDeniedHandler);
            })
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
