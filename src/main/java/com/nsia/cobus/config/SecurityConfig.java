package com.nsia.cobus.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.nsia.cobus.api.filter.JwtsFilter;
import com.nsia.cobus.domain.service.UserDetailsServiceCustom;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceCustom userDetailsServiceCustom;
    private final JwtUtils jwtUtils;

    @Bean
    public PasswordEncoder passwordEncoder() throws Exception {
        // return new BCryptPasswordEncoder();
        // pour ne pas utiliser de mot de passe encrypté
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        try {
            AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(
                    AuthenticationManagerBuilder.class);

            // authenticationManagerBuilder.userDetailsService(userDetailsServiceCustom).passwordEncoder(passwordEncoder());
            authenticationManagerBuilder.userDetailsService(userDetailsServiceCustom)
                    .passwordEncoder(passwordEncoder());
            return authenticationManagerBuilder.build();
        } catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http.csrf(AbstractHttpConfigurer::disable)
                .cors(c -> c.configurationSource(configurationSource()))
                .authorizeHttpRequests(
                        request -> request.requestMatchers("/api/v1/kobus/auth/*", "/api/v1/kobus/parameters/**")
                                .permitAll()
                                .requestMatchers("/api/v1/docs/**", "api/v1/swagger-ui/**", "swagger-ui/**",
                                        "/swagger-ui/index.html",
                                        "/v3/api-docs/**")
                                .permitAll()

                                .anyRequest().authenticated())

                .addFilterBefore(new JwtsFilter(userDetailsServiceCustom, jwtUtils),
                        UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedOriginPattern("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}
