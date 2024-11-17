package com.thihahtetkyaw.user.config;

import com.bastiaanjansen.otp.HMACAlgorithm;
import com.bastiaanjansen.otp.SecretGenerator;
import com.bastiaanjansen.otp.TOTPGenerator;
import com.thihahtetkyaw.user.filters.TokenGenerateFilter;
import com.thihahtetkyaw.user.filters.JwtFilter;
import com.thihahtetkyaw.user.service.AuthUserManager;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    @Value("${reset.token.duration:500}")
    private long duration;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public TOTPGenerator totpGenerator() {
        byte[] secret = SecretGenerator.generate();

        return new TOTPGenerator.Builder(secret)
                .withHOTPGenerator(builder -> {
                    builder.withPasswordLength(6);
                    builder.withAlgorithm(HMACAlgorithm.SHA256); // SHA256 and SHA512 are also supported
                })
                .withPeriod(Duration.ofSeconds(duration))
                .build();
    }
    @Bean
    public SecurityFilterChain requestMatchers(
            HttpSecurity httpSecurity,
            JwtFilter jwtFilter,
            TokenGenerateFilter tokenGenerateFilter) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.authorizeHttpRequests(
                reg -> reg.requestMatchers(
                                "/api/accounts/register",
                                "/login",
                                "/swagger-ui.html",
                                "/swagger-ui/**",
                                "/swagger-ui",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-resources",
                                "/swagger-resources/**").permitAll()
                        .anyRequest().authenticated());
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.addFilterAt(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAfter(tokenGenerateFilter, BasicAuthenticationFilter.class);
        return httpSecurity.build();

    }

    @Bean
    public AuthenticationManager manager(HttpSecurity security,AuthUserManager service) throws Exception {
        var builder = security.getSharedObject(AuthenticationManagerBuilder.class);
        builder.userDetailsService(service);
        return builder.build();
    }
}
