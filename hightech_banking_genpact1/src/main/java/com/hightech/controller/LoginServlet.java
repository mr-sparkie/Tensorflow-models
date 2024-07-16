package com.hightech.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.hightech.bean.User;
import com.hightech.services.UserService;

//@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    public LoginServlet() {
        this.userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get username (assuming it's the user ID) and password from request parameters
            int userId = Integer.parseInt(request.getParameter("username"));
            String password = request.getParameter("password");

            // Authenticate user
            User user = userService.authenticateUser(userId, password);

            if (user != null) {
                // Create a session if user authentication is successful
                HttpSession session = request.getSession();
                session.setAttribute("user", user);

                // Redirect to respective dashboard based on user role
                if ("Admin".equals(user.getRole())) {
                    response.sendRedirect("adminDashboard.jsp");
                } else if ("Associate".equals(user.getRole())) {
                    response.sendRedirect("associateDashboard.jsp");
                } else {
                    response.sendRedirect("userDashboard.jsp");
                }
            } else {
                // Redirect back to login page with error message
                response.sendRedirect("login.jsp?error=1");
            }
        } catch (NumberFormatException e) {
            // Handle invalid number format for user ID
            response.sendRedirect("login.jsp?error=1");
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace(); // Log the exception for debugging
            response.sendRedirect("login.jsp?error=1");
        }
    }
}
