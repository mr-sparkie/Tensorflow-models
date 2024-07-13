package com.hightech.controllers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;

import com.hightech.dao.UserDao;

public class ForgotPassword extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String newPassword = request.getParameter("newPassword");
        String confirmPassword = request.getParameter("confirmPassword");
        String oldPassword = request.getParameter("oldPassword");
        String accNoParam = request.getParameter("accNo");

        if (newPassword.equals(confirmPassword)) {
            HttpSession session = request.getSession(false); // Ensure session exists
            int accNo = 0;
            
            // Check if session exists and has accNo attribute
            if (session != null && session.getAttribute("accNo") != null) {
                accNo = (int) session.getAttribute("accNo");
            } else {
                try {
                    // If session is null or accNo is not found, use the accNo from the request parameter
                    accNo = Integer.parseInt(request.getParameter("accno"));
                } catch (NumberFormatException e) {
                    request.setAttribute("errorMessage", "Invalid account number.");
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                    return;
                }
            }

            UserDao userDao = new UserDao(); // Instantiate UserDao
            boolean passwordUpdated = userDao.updatePassword(accNo , newPassword, oldPassword);

            if (passwordUpdated) {
                // Password updated successfully, set success message
                if (session == null) {
                    session = request.getSession();
                }
                session.setAttribute("successMessage", "Password updated successfully. Please login with your new password.");
                session.setAttribute("isLoggedIn", false); // Log the user out
                session.removeAttribute("accNo"); // Remove the account number from the session
            } else {
                // Handle error in updating password
                request.setAttribute("errorMessage", "Failed to update password");
            }
        } else {
            // Passwords don't match, set error message
            request.setAttribute("errorMessage", "Passwords do not match");
        }

        // Forward back to login.jsp with appropriate message
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
