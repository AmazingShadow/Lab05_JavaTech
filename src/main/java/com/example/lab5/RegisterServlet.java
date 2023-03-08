package com.example.lab5;

import dao.AccountDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pojo.Account;

import java.io.IOException;

@WebServlet(urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("jsp/register.jsp");
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("password-confirm");

        String error = "";
        session.setAttribute("name", name);
        session.setAttribute("email", email);
        session.setAttribute("password", password);
        session.setAttribute("passwordConfirm", passwordConfirm);
        if (name.equals("")) {
            error = "Vui lòng nhập tên";
            session.setAttribute("error", error);
            response.sendRedirect("/register");
            return;
        } else if (email.equals("")) {
            error = "Vui lòng nhập email";
            session.setAttribute("error", error);
            response.sendRedirect("/register");
            return;
        } else if (AccountDAO.getInstance().get(email) != null) {
            error = "Email đã tồn tại";
            session.setAttribute("error", error);
            response.sendRedirect("/register");
            return;
        } else if (password.equals("")) {
            error = "Vui lòng nhập password";
            session.setAttribute("error", error);
            response.sendRedirect("/register");
            return;
        } else if (password.length() < 6) {
            error = "Password Phải có tối thiểu 6 ký tự";
            session.setAttribute("error", error);
            response.sendRedirect("/register");
            return;
        }  else if (passwordConfirm.equals("")) {
            error = "Vui lòng nhập confirm password";
            session.setAttribute("error", error);
            response.sendRedirect("/register");
            return;
        } else if (!password.equals(passwordConfirm)) {
            error = "Password và Confirm password không trùng nhau";
            session.setAttribute("error", error);
            response.sendRedirect("/register");
            return;
        }  else {
            AccountDAO.getInstance().add(new Account(name, email, password));
            response.sendRedirect("/login");
        }

    }
}
