package ru.itis.backend.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.itis.backend.service.SessionService;

import java.io.IOException;

import static ru.itis.backend.constants.Constants.SESSION_TOKEN_ATTR;
import static ru.itis.backend.constants.Constants.USER_AUTHORISED_ATTR;


@WebServlet(name = "logout", value = "/logout")
public class LogoutServlet extends HttpServlet {

    private SessionService sessionService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();

        sessionService = (SessionService) servletContext.getAttribute("sessionService");

        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.setAttribute(USER_AUTHORISED_ATTR, false);
        String sessionToken = (String) session.getAttribute(SESSION_TOKEN_ATTR);

        sessionService.deleteSessionByToken(sessionToken);

        response.sendRedirect("login");
    }
}
