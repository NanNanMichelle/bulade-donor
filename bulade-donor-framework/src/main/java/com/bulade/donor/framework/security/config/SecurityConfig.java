package com.bulade.donor.framework.security.config;

import com.bulade.donor.framework.security.filter.JwtAuthenticationTokenFilter;
import com.bulade.donor.framework.security.handler.BuladeAccessDeniedHandler;
import com.bulade.donor.framework.security.handler.BuladeAuthenticationEntryPoint;
import jakarta.annotation.Resource;
import org.springframework.context.ApplicationContext;
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

//开启全局方法安全性
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Resource
    private SecurityProperties securityProperties;

    @Resource
    private ApplicationContext applicationContext;

    /**
     * 认证失败处理类 Bean
     */
    @Resource
    private BuladeAuthenticationEntryPoint authenticationEntryPoint;


    /**
     * 权限不够处理器 Bean
     */
    @Resource
    private BuladeAccessDeniedHandler accessDeniedHandler;

    /**
     * Token 认证过滤器 Bean
     */
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    public AuthenticationManager authenticationManagerBean(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        // 登出
        httpSecurity
            .cors((cors) -> cors.configure(httpSecurity))
            .csrf(AbstractHttpConfigurer::disable)
            .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        // 设置每个请求的权限
        httpSecurity
            .authorizeHttpRequests(c -> c
                    .requestMatchers("/swagger-ui/**", "/v3/api-docs/**")
                    .permitAll()
                    .requestMatchers(securityProperties.getPermitAllUrls().toArray(new String[0]))
                    .permitAll()

//                .requestMatchers("/api/admin/login")
//                .permitAll()

                    .requestMatchers("/error")
                    .permitAll()

                    .anyRequest().authenticated()

            )
            .exceptionHandling(c -> c.authenticationEntryPoint(authenticationEntryPoint)
                .accessDeniedHandler(accessDeniedHandler));

        // 添加 Token Filter
        httpSecurity.addFilterAt(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(securityProperties.getPasswordEncoderLength());
    }

}
