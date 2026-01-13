package org.neurogine.librarymanagementbackend.config;



import lombok.RequiredArgsConstructor;
import org.neurogine.librarymanagementbackend.security.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                .cors(Customizer.withDefaults())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        // public endpoints
                        .requestMatchers(
                                "/api/users/login",
                                "/images/**"
                        ).permitAll()


                        // ✅ BOOK APIs - ALL require authentication
                        .requestMatchers(HttpMethod.GET, "/api/books").authenticated()
                        .requestMatchers(HttpMethod.GET, "/api/books/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/books").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/api/books/**").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/api/books/**").authenticated()

                        // everything else
                        .anyRequest().authenticated()
                )


                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
