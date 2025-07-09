package org.burgosleo.springsecurityproject.config.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.burgosleo.springsecurityproject.constants.AppConstants;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
public class JwtTokenGeneratorFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(Objects.nonNull(authentication)) {
            log.info("Attempting JWT token generation");
            Environment environment = getEnvironment();
            String secret = environment.getProperty(AppConstants.JWT_SECRET_KEY, AppConstants.JWT_SECRET_DEFAULT_VALUE);
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));

            String token = Jwts.builder().issuer("Spring Security Project")
                    .subject("JWT Token")
                    .claim("username", authentication.getName())
                    .claim("authorities", authentication.getAuthorities()
                            .stream()
                            .map(GrantedAuthority::getAuthority)
                            .collect(Collectors.joining(",")))
                    .issuedAt(new Date())
                    .expiration(new Date(System.currentTimeMillis() + 3_600_000))
                    .signWith(secretKey)
                    .compact();

            response.setHeader(AppConstants.JWT_HEADER, token);
            log.info("JWT token generated successfully: {}", token);
        }
        filterChain.doFilter(request, response);
    }

    // FALSE: when the method returns false, the filter would be executed
    // TRUE: when the method returns true, the filter would not be executed

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String currentPath = request.getServletPath();
        return !StringUtils.equals(currentPath, "/api/v1/users");
    }
}
