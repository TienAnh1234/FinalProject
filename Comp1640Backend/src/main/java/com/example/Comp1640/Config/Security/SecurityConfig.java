package com.example.Comp1640.Config.Security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        return http
                .cors(customizer -> customizer.configurationSource(request -> {
                    org.springframework.web.cors.CorsConfiguration config = new org.springframework.web.cors.CorsConfiguration();
                    config.setAllowCredentials(true);
                    config.addAllowedOrigin("http://localhost:4200"); // ✅ Chỉ cho phép Angular truy cập
                    config.addAllowedHeader("*"); // ✅ Chấp nhận tất cả headers
                    config.addAllowedMethod("*"); // ✅ Chấp nhận tất cả methods (GET, POST, ...)
                    return config;
                }))
                .csrf(customizer -> customizer
                        .ignoringRequestMatchers("/ws/**")
                        .disable()
                )
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .requestMatchers("/api/classroom/**").permitAll()
                        .requestMatchers("/api/major/**").permitAll()
                        .requestMatchers("/api/student/**").permitAll()
                        .requestMatchers("/api/tutor/**").permitAll()
                        .requestMatchers("/api/user/**").permitAll()
                        .requestMatchers("/api/blog/**").permitAll()
                        .requestMatchers("/api/schedule/**").permitAll()
                        .requestMatchers("/api/dashboard/**").permitAll()
                        .requestMatchers("/api/blogLikes/**").permitAll()
                        .requestMatchers("/api/comment/**").permitAll()
                        .requestMatchers("/api/district/**").permitAll()
                        .requestMatchers("/api/grade/**").permitAll()
                        .requestMatchers("/api/majorGrade/**").permitAll()
                        .requestMatchers("/api/majorgradeTutor/**").permitAll()
                        .requestMatchers("/api/admin/**").permitAll()
                        .requestMatchers("/api/vnpay/**").permitAll()
                        .requestMatchers("/api/payment/**").permitAll()





                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
