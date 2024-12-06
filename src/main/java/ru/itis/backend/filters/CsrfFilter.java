package ru.itis.backend.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;


@WebFilter("/*")
public class CsrfFilter extends HttpFilter {
    private static final Set<String> ALLOWED_URLS = Set.of("/login", "/registration");

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String url = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        if (ALLOWED_URLS.contains(url)) {

            super.doFilter(request, response, chain);
        } else {

            HttpSession session = request.getSession();

            if (request.getMethod().equals("GET")) {

                String csrfToken = UUID.randomUUID().toString();

                session.setAttribute("csrf_token", csrfToken);
                request.setAttribute("csrf_token", csrfToken);

                chain.doFilter(request, response);

            } else if (request.getMethod().equals("POST")) {

                String expectedCsrfToken = (String) session.getAttribute("csrf_token");
                String actualCsrfToken = (String) request.getAttribute("csrf_token");

                if (expectedCsrfToken.equals(actualCsrfToken)) {

                    chain.doFilter(request, response);
                } else {

                    response.setStatus(403);
                    response.getWriter().println("Csrf not match");
                }
            } else {

                chain.doFilter(request, response);
            }
        }
    }
}
