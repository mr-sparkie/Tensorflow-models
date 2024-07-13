package com.hightech.controllers;

import com.hightech.service.UserService;
import com.hightech.service.AdminService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class login extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserService();
    private final AdminService adminService = new AdminService();

    public login() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int accNo = Integer.parseInt(request.getParameter("username"));
            String password = request.getParameter("password");

            // Check if it's an admin login
            boolean isAdmin = userService.isAdmin(accNo);

            if (isAdmin) {
                boolean isValidAdmin = adminService.adminLogin(accNo, password);
                if (isValidAdmin) {
                    HttpSession session = request.getSession();
                    session.setAttribute("accNo", accNo);
                    session.setAttribute("role", "admin");
                    session.setAttribute("isLoggedIn", true);
                    response.sendRedirect("admin.jsp");
                } else {
                    request.setAttribute("accNo", accNo); // Retain accNo
                    request.setAttribute("errorMessage", "Invalid credentials. Please try again.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            } else {
                boolean isValidClient = userService.login(accNo, password);
                if (isValidClient) {
                    HttpSession session = request.getSession();
                    session.setAttribute("accNo", accNo);
                    session.setAttribute("role", "client");
                    session.setAttribute("isLoggedIn", true);
                    response.sendRedirect("Dashboard.jsp");
                } else {
                    request.setAttribute("accNo", accNo); // Retain accNo
                    request.setAttribute("errorMessage", "Invalid credentials. Please try again.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                }
            }
        } catch (NumberFormatException e) {
            // Handle invalid input (non-numeric username)
            request.setAttribute("errorMessage", "Invalid input. Please enter a valid account number.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } catch (Exception e) {
            // Handle other exceptions
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
