package com.hightech.controller;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.hightech.services.UserService;

//@WebServlet("/updatePassword")
public class UpdatePasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService;

    public UpdatePasswordServlet() {
        this.userService = new UserService();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            // Get parameters from request
            String accNoStr = request.getParameter("accno");
            String oldPassword = request.getParameter("oldPassword");
            String newPassword = request.getParameter("newPassword");
            String confirmPassword = request.getParameter("confirmPassword");

            // Validate inputs
            if (accNoStr == null || oldPassword == null || newPassword == null || confirmPassword == null ||
                accNoStr.isEmpty() || oldPassword.isEmpty() || newPassword.isEmpty() || confirmPassword.isEmpty()) {
                response.sendRedirect("login.jsp?passwordUpdateError=1");
                return;
            }

            int userId = Integer.parseInt(accNoStr);

            // Check if newPassword matches confirmPassword
            if (!newPassword.equals(confirmPassword)) {
                response.sendRedirect("login.jsp?passwordMismatch=1");
                return;
            }

            // Verify old password before updating
            boolean isOldPasswordCorrect = userService.verifyOldPassword(userId, oldPassword);

            if (!isOldPasswordCorrect) {
                response.sendRedirect("login.jsp?incorrectOldPassword=1");
                return;
            }

            // Update password in database
            boolean isUpdated = userService.updatePassword(userId, oldPassword, newPassword);

            if (isUpdated) {
                response.sendRedirect("login.jsp?passwordUpdateSuccess=1");
            } else {
                response.sendRedirect("login.jsp?passwordUpdateError=1");
            }
        } catch (NumberFormatException e) {
            // Handle invalid number format for accno parameter
            response.sendRedirect("login.jsp?passwordUpdateError=1");
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace(); // Log the exception for debugging
            response.sendRedirect("login.jsp?passwordUpdateError=1");
        }
    }
}
