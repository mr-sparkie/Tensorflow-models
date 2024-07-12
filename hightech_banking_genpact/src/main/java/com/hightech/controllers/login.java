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

            // Check if it's an admin login
            boolean isAdmin = userService.isAdmin(accNo);

            if (isAdmin) {
                String password = request.getParameter("password");
                boolean isValidAdmin = adminService.adminLogin(accNo, password);
                if (isValidAdmin) {
                    HttpSession session = request.getSession();
                    session.setAttribute("accNo", accNo);
                    session.setAttribute("role", "admin");
                    response.sendRedirect("admin.jsp");
                } else {
                    response.sendRedirect("login_error.jsp");
                }
            } else {
                String password =request.getParameter("password");
                boolean isValidClient = userService.login(accNo, password);
                if (isValidClient) {
                    HttpSession session = request.getSession();
                    session.setAttribute("accNo", accNo);
                    session.setAttribute("role", "client");
                    response.sendRedirect("Dashboard.jsp");
                } else {
                    response.sendRedirect("login_error.jsp");
                }
            }
        } catch (NumberFormatException e) {
            // Handle invalid input (non-numeric username or password)
            System.err.println("Error parsing login credentials: " + e.getMessage());
            response.sendRedirect("login_error.jsp");
        } catch (Exception e) {
            // Handle other exceptions
            System.err.println("Error during login: " + e.getMessage());
            response.sendRedirect("login_error.jsp");
        }
    }
}
