package com.example.lab5;

import dao.AccountDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pojo.Account;

import java.io.IOException;

@WebServlet(urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String message = request.getParameter("message");

        if (message != null) {
            session.removeAttribute("account");
            response.sendRedirect("/login");
        } else if (session.getAttribute("account") != null) {
            response.sendRedirect("/");
        }
        else {
            request.getRequestDispatcher("jsp/login.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String email = request.getParameter("username");
        String password = request.getParameter("password");
        String savePassWord = request.getParameter("saveAccount");
        Account check = AccountDAO.getInstance().get(email);
        Cookie cookie = null;


        if (savePassWord != null) {
            cookie = new Cookie("account", email + ":" + password);
            cookie.setMaxAge(30 * 24 * 60 * 60);
            response.addCookie(cookie);
        }
        if (check != null && check.getPassword().equals(password)) {
            session.setAttribute("account", email + ":" + password);
            response.sendRedirect("/");
        } else {
            response.sendRedirect("/login?login=fail");
        }
    }
}
