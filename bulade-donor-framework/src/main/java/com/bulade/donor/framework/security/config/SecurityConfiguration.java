package com.bulade.donor.framework.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    /**
     * 自定义全局过滤链
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .cors((cors) -> cors.configure(http))
                // Do not use form login
                .csrf(AbstractHttpConfigurer::disable)
                // Set session management to stateless
                .sessionManagement((session) -> {
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })

                .authorizeHttpRequests((authorize) -> {
                    authorize
                            .requestMatchers("/swagger-ui/**", "/doc.html")
                            .permitAll()

                            .requestMatchers("/api/dict/**")
                            .permitAll()

                            .requestMatchers("/api/v1/open/login")
                            .permitAll()

                            .anyRequest()
                            .permitAll();
                })

                // Exception handling
//            .exceptionHandling((configurer) -> {
//                configurer.authenticationEntryPoint(authenticationEntryPoint);
//                configurer.accessDeniedHandler(accessDeniedHandler);
//            })

                // Add JWT token filter
//            .addFilterAt(userJwtFilter, UsernamePasswordAuthenticationFilter.class)
//            .addFilterAfter(adminJwtFilter, UserJwtFilter.class)
//            .addFilterAfter(donateQueueFilter, AdminJwtFilter.class)

        // build the SecurityFilterChain
            .build();
    }

    /**
     * 密码编码器
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }


    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     */
    @Bean
    AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(daoAuthenticationProvider);
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        var configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("*"));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        var source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
