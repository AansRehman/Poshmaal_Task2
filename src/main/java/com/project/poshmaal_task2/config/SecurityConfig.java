//package com.project.poshmaal_task2.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/findAllEmployees").permitAll()
//                .antMatchers("/getEmployee/**").permitAll()
//                .antMatchers("/deleteEmployee/**").hasRole("MANAGER")
//                .antMatchers("/addArtwork", "/deleteArtwork", "/addArtist", "/deleteArtist").hasAnyRole("MANAGER", "BUYER")
//                .antMatchers("/editArtwork/**", "/editArtist/**").authenticated()
//                .anyRequest().authenticated()
//                .and()
//                .formLogin()
//                .permitAll()
//                .and()
//                .logout()
//                .permitAll();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("manager").password(passwordEncoder().encode("password")).roles("MANAGER")
//                .and()
//                .withUser("buyer").password(passwordEncoder().encode("password")).roles("BUYER")
//                .and()
//                .withUser("staff").password(passwordEncoder().encode("password")).roles("STAFF");
//    }
//}
