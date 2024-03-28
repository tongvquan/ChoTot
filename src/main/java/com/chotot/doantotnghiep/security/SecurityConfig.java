package com.chotot.doantotnghiep.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Set;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Bean
    public SecurityFilterChain applicationSecurity(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests((auth)->auth.requestMatchers("/sell-product","/manage-product","/edit-product/**","/delete-product/**").authenticated()
                .requestMatchers("/login").permitAll()
                .requestMatchers("/register").permitAll()
                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().permitAll()
                ).formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler((request, response, authentication) -> {
                            Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
                            if (roles.contains("ADMIN")) {
                                response.sendRedirect("/admin/home");
                            } else if (roles.contains("USER")) {
                                response.sendRedirect("/");
                            } else {
                                response.sendRedirect("/default");
                            }
                        }))
                .logout(logout -> logout.logoutUrl("/logout").logoutSuccessUrl("/login"));
        return http.build();
    }

    @Bean
    WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring().requestMatchers("/static/**","/plugin/**","/assets/**","/uploads/**");
    }
    @Bean
    BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
