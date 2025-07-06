package org.burgosleo.springsecurityproject.config.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;
import java.util.Base64;

public class RequestValidationBeforeFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String authHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.isNotBlank(authHeader)) {
            authHeader = authHeader.trim();
            if(StringUtils.startsWithIgnoreCase(authHeader, "Basic ")) {
                String base64Credentials = StringUtils.substringAfter(authHeader, "Basic ");
                Base64.Decoder decoder = Base64.getDecoder();

                try {
                    String decodedCredentials = new String(decoder.decode(base64Credentials));
                    // Check if semicolon is present
                    if (StringUtils.contains(decodedCredentials, ":")) {
                        String username = StringUtils.substringBefore(decodedCredentials, ":");
                        if(StringUtils.contains(username, "test")) {
                            httpResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                            return;
                        }
                    }
                    else {
                        throw new BadCredentialsException("Invalid credential string: " + base64Credentials);
                    }
                }
                catch (IllegalArgumentException e) {
                    throw new BadCredentialsException("Failed to decode Basic authentication header: " + base64Credentials);
                }
            }
        }
        chain.doFilter(httpRequest, httpResponse);
    }
}
