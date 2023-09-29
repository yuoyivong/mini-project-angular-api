package com.example.library_management.config;

import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final AuthTokenFilter jwtAuthFilter;

    private final AuthenticationProvider authenticationProvider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.
//                .cors(Customizer.withDefaults()).
                csrf(AbstractHttpConfigurer::disable)
//                .csrf((csrf) -> csrf.disable())
                .sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .cors(c -> c.configurationSource(
                        request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                            config.setAllowedMethods(Collections.singletonList("*"));
                            config.setAllowCredentials(true);
                            config.setAllowedHeaders(Collections.singletonList("*"));
                            config.setExposedHeaders(List.of("Authorization"));
                            config.setMaxAge(3600L);
                            return config;
                        }))
                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/api/v1/auth/**","/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
                                .requestMatchers("/api/v1/author/**", "/api/v1/book/**").hasAuthority("AUTHOR")
                                .requestMatchers("/api/v1/reader/**").hasAnyAuthority("READER", "AUTHOR")
                                .requestMatchers("/api/v1/file/**", "/api/v1/files", "/api/v1/auth/**", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
//                                .requestMatchers("/api/v1/auth/**","/api/v1/reader/allBooks", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html")
                                .permitAll()
                                .anyRequest().authenticated()
                )
//                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    //    @Bean
//    public CorsFilter corsFilter() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        final CorsConfiguration config = new CorsConfiguration();
//        config.setAllowCredentials(true);
//        config.setAllowedOriginPatterns(Collections.singletonList("*")); // Allows all origin
//        config.setAllowedHeaders(Arrays.asList("X-Requested-With", "Origin", "Content-Type", "Accept",
//                "Authorization", "Access-Control-Allow-Credentials", "Access-Control-Allow-Headers", "Access-Control-Allow-Methods",
//                "Access-Control-Allow-Origin", "Access-Control-Expose-Headers", "Access-Control-Max-Age",
//                "Access-Control-Request-Headers", "Access-Control-Request-Method", "Age", "Allow", "Alternates",
//                "Content-Range", "Content-Disposition", "Content-Description"));
//        config.setAllowedMethods(Arrays.asList("*"));
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
//        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
//        configuration.setAllowedHeaders(List.of("Authorization"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//
//    }
}
