package com.pkgsub.subscriptionsystem.api_gateway.config;

import com.pkgsub.subscriptionsystem.api_gateway.security.AuthEntryPoint;
import com.pkgsub.subscriptionsystem.api_gateway.security.jwt.filters.AuthTokenFilter;
import com.pkgsub.subscriptionsystem.common.enumerations.AppUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebFluxSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthEntryPoint authEntryPoint;
    private final AuthTokenFilter authTokenFilter;

    private static final String[] PUBLIC_URL = {
            "/api/auth/**",
            "/swagger-ui/**", "/swagger-ui.html", "/swagger-resources", "/swagger-resources/**", "/v3/api-docs", "/v3/api-docs/**",
            "/billing-service/swagger-ui/**", "/billing-service/swagger-ui.html", "/billing-service/swagger-resources", "/billing-service/swagger-resources/**", "/billing-service/v3/api-docs", "/billing-service/v3/api-docs/**",
            "/package-service/swagger-ui/**", "/package-service/swagger-ui.html", "/package-service/swagger-resources", "/package-service/swagger-resources/**", "/package-service/v3/api-docs", "/package-service/v3/api-docs/**",
            "/subscription-service/swagger-ui/**", "/subscription-service/swagger-ui.html", "/subscription-service/swagger-resources", "/subscription-service/swagger-resources/**", "/subscription-service/v3/api-docs", "/subscription-service/v3/api-docs/**"
    };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        http.csrf(ServerHttpSecurity.CsrfSpec::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeExchange(exchanges -> exchanges
                        .pathMatchers(HttpMethod.OPTIONS).permitAll()
                        .pathMatchers(PUBLIC_URL).permitAll()
                        .pathMatchers(HttpMethod.POST, "/package-service/api/packages").hasRole(AppUserRole.ADMIN.name())
                        .anyExchange().authenticated()
                )
                .exceptionHandling(exceptions -> exceptions.authenticationEntryPoint(authEntryPoint))
                .addFilterAt(authTokenFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsWebFilter corsWebFilter() {
        return new CorsWebFilter(corsConfigurationSource());
    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}