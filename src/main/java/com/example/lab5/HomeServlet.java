package com.example.lab5;

import dao.ProductDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import pojo.Product;

import java.io.IOException;

@WebServlet(urlPatterns = {""})
public class HomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String account = (String) session.getAttribute("account");

        if (account != null) {
            request.getRequestDispatcher("jsp/index.jsp").forward(request, response);
        } else {
            response.sendRedirect("/login");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String name = request.getParameter("name");
        String price = request.getParameter("price");

        session.setAttribute("check", name);

        if (request.getParameter("delete") != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            ProductDAO.getInstance().remove(id);
            response.sendRedirect("/?delete=true");
        } else {
            if (!name.equals("") && !price.equals("")) {
                ProductDAO.getInstance().add(new Product(name, Double.parseDouble(price)));
                session.removeAttribute("check");
                response.sendRedirect("/");
            } else {
                response.sendRedirect("/?method=add");
            }
        }

    }
}
