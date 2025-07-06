package org.burgosleo.springsecurityproject.config.filter;

import jakarta.servlet.*;
import org.springframework.security.web.csrf.CsrfToken;

import java.io.IOException;
import java.util.Objects;

public class CsrfCookieFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());

        if(Objects.nonNull(csrf)) {
            csrf.getToken();
        }

        chain.doFilter(request, response);
    }
}
