package com.project.poshmaal_task2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(@Lazy CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/h2/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/**").authenticated() // Authentication for GET
                        .requestMatchers(HttpMethod.POST, "/artist/**", "/artwork/**").hasAnyRole("MANAGER", "BUYER")
                        .requestMatchers(HttpMethod.PUT, "artwork/changePrice/**").hasAnyRole("MANAGER","BUYER")
                        .requestMatchers(HttpMethod.PUT, "/artwork/**", "artist/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/artist/**", "/artwork/**").hasAnyRole("MANAGER", "BUYER")
                        .requestMatchers("/employee/**").hasRole("MANAGER")
                        .anyRequest().authenticated()
                )
                .formLogin(withDefaults()) // Use default login form
                .httpBasic(withDefaults()); // Enable HTTP Basic authentication

        return http.build();
    }

//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
//    }
}
