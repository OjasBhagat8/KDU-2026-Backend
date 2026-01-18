package com.library.libraryprod.security;

import com.library.libraryprod.constants.AppConstants;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.
                csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers(HttpMethod.POST, "/books/v1")
                                .hasRole(AppConstants.Librarian_ROLE)

                                .requestMatchers(HttpMethod.PATCH, "/books/v1/*/catalog/*")
                                .hasRole(AppConstants.Librarian_ROLE)

                                .requestMatchers(HttpMethod.GET, "/books/v1")
                                .hasAnyRole(AppConstants.Librarian_ROLE, AppConstants.Member_ROLE)

                                .requestMatchers(HttpMethod.POST, "/loans/v1/*/borrow/*")
                                .hasRole(AppConstants.Member_ROLE)

                                .requestMatchers(HttpMethod.POST, "/loans/v1/*/return/*")
                                .hasRole(AppConstants.Member_ROLE)

                                .requestMatchers(HttpMethod.GET, "/loans/v1/analytics/audit")
                                .hasRole(AppConstants.Member_ROLE)

                                .requestMatchers(HttpMethod.GET, "/analytics/v1/audit")
                                .hasRole(AppConstants.Librarian_ROLE)


                                .requestMatchers(
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**"
                                ).permitAll()

                                .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
