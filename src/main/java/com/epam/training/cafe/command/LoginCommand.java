package com.epam.training.cafe.command;

import com.epam.training.cafe.entity.User;
import com.epam.training.cafe.exception.ServiceException;
import com.epam.training.cafe.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

public class LoginCommand implements Command {
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        UserService service = new UserService();
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        Optional<User> user = service.login(login, password);
        if (user.isPresent()) {
            request.setAttribute("user",user.get());
        }
        return "/WEB-INF/main.jsp";
    }
}
