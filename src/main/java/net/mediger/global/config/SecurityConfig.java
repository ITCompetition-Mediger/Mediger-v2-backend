package net.mediger.global.config;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;
import net.mediger.auth.jwt.JwtFilter;
import net.mediger.global.exception.handler.GlobalAccessDeniedHandler;
import net.mediger.global.exception.handler.GlobalAuthenticationEntryPoint;
import net.mediger.global.properties.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final SecurityProperties securityProperties;
    private final GlobalAuthenticationEntryPoint globalAuthenticationEntryPoint;
    private final GlobalAccessDeniedHandler globalAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeHttpRequests(
                        auth -> auth.requestMatchers(securityProperties.getWhiteListMatchers()).permitAll()
                                .anyRequest().authenticated())
                .exceptionHandling(
                        exception -> exception.authenticationEntryPoint(globalAuthenticationEntryPoint)
                                .accessDeniedHandler(globalAccessDeniedHandler))

                .build();
    }
}
