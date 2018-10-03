package com.epam.training.cafe;

import com.epam.training.cafe.command.Command;
import com.epam.training.cafe.command.CommandFactory;
import com.epam.training.cafe.exception.ServiceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@WebServlet("/controller")
public class Controller extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter("command");
        Command action = CommandFactory.create(command);
        String page = null;

        try {
            page = action.execute(request, response);
        } catch (ServiceException e) {
            page = "/WEB-INF/error.jsp";
        }
        dispatch(request,response,page);

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String command = request.getParameter("command");
        Command action = CommandFactory.create(command);
        String page = null;

        try {
            page = action.execute(request, response);
        } catch (ServiceException e) {
            page = "/WEB-INF/error.jsp";
        }
        dispatch(request,response,page);
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }
}
