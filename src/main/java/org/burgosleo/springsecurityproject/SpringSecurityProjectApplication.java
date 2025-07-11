package org.burgosleo.springsecurityproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity(debug = true)
@EnableMethodSecurity(jsr250Enabled = true, securedEnabled = true)
//@EntityScan("org.burgosleo.springsecurityproject.entity")
//@EnableJpaRepositories("org.burgosleo.springsecurityproject.repository")
public class SpringSecurityProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityProjectApplication.class, args);
    }

}
