package org.burgosleo.springsecurityproject.config;

import jakarta.servlet.http.HttpServletRequest;
import org.burgosleo.springsecurityproject.config.filter.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Collections;
import java.util.List;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(AbstractHttpConfigurer::disable);

        // Config required when using SESSION_ID cookie.
        // http.securityContext(contextConfig -> contextConfig.requireExplicitSave(false));

        http.cors(corsConfig -> corsConfig.configurationSource(this::corsConfiguration));

        // require always HTTPS connections
        http.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure());

        http.sessionManagement(
                sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
            .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
            .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
            .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
            .addFilterAfter(new JwtTokenGeneratorFilter(), BasicAuthenticationFilter.class)
            .addFilterBefore(new JwtTokenValidationFilter(), BasicAuthenticationFilter.class);

        http.authorizeHttpRequests(request -> request
                .requestMatchers("/api/v1/myAccount").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/v1/myBalance").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/api/v1/myCards").hasRole("USER")
                .requestMatchers("/api/v1/myLoans").hasAnyRole("USER", "ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/v1/users").authenticated()
                .requestMatchers("/api/v1/notices",
                        "/api/v1/users/login",
                        "/api/v1/contact",
                        "/api/v1/users",
                        "/login",
                        "/logout",
                        "/error").permitAll());

        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

    @Bean
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        UsernamePasswordAuthenticationProvider authenticationProvider = new UsernamePasswordAuthenticationProvider(userDetailsService, passwordEncoder);
        ProviderManager providerManager = new ProviderManager(authenticationProvider);
        providerManager.setEraseCredentialsAfterAuthentication(false);
        return providerManager;
    }

    private CorsConfiguration corsConfiguration(HttpServletRequest request) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowCredentials(true);
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setExposedHeaders(List.of("Authorization"));
        corsConfiguration.setMaxAge(3600L);
        return corsConfiguration;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}
