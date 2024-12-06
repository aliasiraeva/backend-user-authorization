package ru.itis.backend.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.itis.backend.config.DataSourceConfiguration;
import ru.itis.backend.mapper.impl.SessionRowMapper;
import ru.itis.backend.mapper.impl.UserRowMapper;
import ru.itis.backend.repository.SessionRepository;
import ru.itis.backend.repository.UserRepository;
import ru.itis.backend.repository.impl.SessionRepositoryImpl;
import ru.itis.backend.repository.impl.UserRepositoryImpl;
import ru.itis.backend.service.SessionService;
import ru.itis.backend.service.UserService;
import ru.itis.backend.service.impl.SessionServiceImpl;
import ru.itis.backend.service.impl.UserServiceImpl;

import java.io.IOException;
import java.util.Properties;


@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Properties properties = new Properties();
        try {
            properties.load(Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("application.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        DataSourceConfiguration configuration = new DataSourceConfiguration(properties);

        UserRepository userRepository =
                new UserRepositoryImpl(configuration.hikariDataSource(), new UserRowMapper());
        SessionRepository sessionRepository =
                new SessionRepositoryImpl(configuration.hikariDataSource(), new SessionRowMapper());

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        UserService userService = new UserServiceImpl(userRepository, bCryptPasswordEncoder);
        SessionService sessionService = new SessionServiceImpl(sessionRepository, userService);

        ServletContext servletContext = sce.getServletContext();
        servletContext.setAttribute("userService", userService);
        servletContext.setAttribute("sessionService", sessionService);
    }

}
