package com.medilabo.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring HTTP security");
        http
                .csrf().disable() // Disable CSRF to avoid 403 on POST/PUT/DELETE
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic();
        logger.info("HTTP security configuration completed");
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        logger.info("Setting up in-memory user details");
        UserDetails user = User.builder()
                .username("medilabo_user") // keep your username here
                .password("{noop}9188") // match your password (use {noop} for no encoding)
                .roles("USER")
                .build();
        logger.info("UserDetailsService configured with user '{}'", user.getUsername());
        return new InMemoryUserDetailsManager(user);
    }
}