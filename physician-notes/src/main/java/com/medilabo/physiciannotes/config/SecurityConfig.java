package com.medilabo.physiciannotes.config;

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
        logger.info("Configuring Physician Notes HTTP security");
        http
                .csrf().disable()
                .authorizeRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic();
        logger.info("Physician Notes HTTP security configuration completed");
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        logger.info("Setting up in-memory user details for Physician Notes");
        UserDetails user = User.builder()
                .username("medilabo_user")
                .password("{noop}9188")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
}
