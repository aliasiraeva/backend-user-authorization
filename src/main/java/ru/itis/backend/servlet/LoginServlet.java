package ru.itis.backend.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import ru.itis.backend.service.SessionService;
import ru.itis.backend.service.UserService;

import java.io.IOException;
import java.util.UUID;

import static ru.itis.backend.constants.Constants.SESSION_TOKEN_ATTR;
import static ru.itis.backend.constants.Constants.USER_AUTHORISED_ATTR;


@WebServlet(name = "login", value = "/login")
public class LoginServlet extends HttpServlet {

    private UserService userService;
    private SessionService sessionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();

        userService = (UserService) servletContext.getAttribute("userService");
        sessionService = (SessionService) servletContext.getAttribute("sessionService");

        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        Boolean rememberUser = Boolean.valueOf(request.getParameter("rememberUser"));

        if (username == null || password == null) {
            response.sendRedirect("login");
        }

        if (userService.isAuthorized(username, password)) {
            System.out.println("Успешная авторизация");

            String sessionToken = UUID.randomUUID().toString();

            sessionService.addSession(username, sessionToken);

            HttpSession session = request.getSession();
            session.setAttribute(USER_AUTHORISED_ATTR, true);
            session.setAttribute(SESSION_TOKEN_ATTR, sessionToken);

            if (Boolean.TRUE.equals(rememberUser)) {
                Cookie cookie = new Cookie(SESSION_TOKEN_ATTR, sessionToken);
                response.addCookie(cookie);
            }

            response.sendRedirect("index");
        } else {
            System.out.println("Ошибка авторизации");

            response.sendRedirect("error");
        }
    }

}
