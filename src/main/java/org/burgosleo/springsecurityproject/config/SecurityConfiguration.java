package org.burgosleo.springsecurityproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(request -> request.requestMatchers(
                "/api/v1/myAccount",
                "/api/v1/myBalance",
                "/api/v1/myCards",
                "/api/v1/myLoans").authenticated());

        http.authorizeHttpRequests(requests -> requests.requestMatchers(
                "/api/v1/notices",
                "/api/v1/contact",
                "/login",
                "/logout",
                "/error").permitAll());

        http.formLogin(AbstractHttpConfigurer::disable);
        http.httpBasic(Customizer.withDefaults());

        return http.build();
    }

}
