package ru.itis.backend.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.backend.service.SessionService;

import java.io.IOException;
import java.util.Set;

import static ru.itis.backend.constants.Constants.USER_AUTHORISED_ATTR;


@WebFilter("/*")
public class AuthorizationFilter extends HttpFilter {

    private static final Set<String> ALLOWED_URLS = Set.of("/login", "/registration", "/error");

    private SessionService sessionService;


    @Override
    public void init(FilterConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();

        sessionService = (SessionService) servletContext.getAttribute("sessionService");

        super.init(config);
    }


    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String url = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        if (ALLOWED_URLS.contains(url)) {
            System.out.println("Запрос не требует авторизации: " + request.getRequestURI());

            super.doFilter(request, response, chain);
        } else {
            System.out.println("Авторизация запроса: " + request.getRequestURI());

            HttpSession session = request.getSession();
            Boolean isAuthorised = (Boolean) session.getAttribute(USER_AUTHORISED_ATTR);
            if (Boolean.TRUE.equals(isAuthorised)) {
                System.out.println("Успешная авторизация");

                super.doFilter(request, response, chain);
            } else {
                if (sessionService.existSessionByCookie(request.getCookies())) {
                    System.out.println("Сессия восстановлена из куки");

                    request.setAttribute(USER_AUTHORISED_ATTR, true);
                    super.doFilter(request, response, chain);
                } else {
                    System.out.println("Не авторизованный запрос: " + request.getRequestURI());

                    response.sendRedirect("login");
                }
            }
        }
    }


}
