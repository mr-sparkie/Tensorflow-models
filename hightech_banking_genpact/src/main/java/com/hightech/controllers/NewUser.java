package com.hightech.controllers;

import com.hightech.bean.User;
import com.hightech.dao.UserDao;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/newuser")
public class NewUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserDao userDao = new UserDao();

    public NewUser() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action) {
            case "add":
                addUser(request, response);
                break;
            case "fetch":
                fetchUser(request, response);
                break;
            case "update":
                updateUser(request, response);
                break;
            case "delete":
                deleteUser(request, response);
                break;
            default:
                response.sendRedirect("error.jsp");
                break;
        }
    }

    private void addUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from the request
        int accNo = Integer.parseInt(request.getParameter("acc_no"));
        String fullName = request.getParameter("full_name");
        String address = request.getParameter("address");
        String mobileNo = request.getParameter("mobile_no");
        String emailId = request.getParameter("email");
        String accType = request.getParameter("acc_type");
        String dob = request.getParameter("dob");
        String idProof = request.getParameter("id_proof");
        int initialBalance = Integer.parseInt(request.getParameter("initial_balance"));

        // Create a new user object
        User user = new User(accNo, fullName, address, mobileNo, emailId, accType, dob, idProof, initialBalance);

        try {
            // Add the user to the database
            userDao.addUser(user);

            // Redirect to admin.jsp
            response.sendRedirect("admin.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors, redirect to error.jsp or appropriate error page
            response.sendRedirect("error.jsp");
        }
    }

    private void fetchUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int accNo = Integer.parseInt(request.getParameter("acc_no"));

        try {
            User user = userDao.getUserDetails(accNo);
            if (user != null) {
                request.setAttribute("user", user);
                request.setAttribute("activeTab", "update"); // Set active tab to update if user found
                request.getRequestDispatcher("admin-panel.jsp").forward(request, response);
            } else {
                request.setAttribute("errorMessage", "User not found for Account Number: " + accNo);
                request.getRequestDispatcher("error.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors, redirect to error.jsp or appropriate error page
            response.sendRedirect("error.jsp");
        }
    }

    private void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from the request
        int accNo = Integer.parseInt(request.getParameter("acc_no"));
        String fullName = request.getParameter("full_name");
        String address = request.getParameter("address");
        String mobileNo = request.getParameter("mobile_no");
        String emailId = request.getParameter("email");
        String accType = request.getParameter("acc_type");
        String dob = request.getParameter("dob");
        String idProof = request.getParameter("id_proof");
        int initialBalance = Integer.parseInt(request.getParameter("initial_balance"));

        // Create a new user object
        User user = new User(accNo, fullName, address, mobileNo, emailId, accType, dob, idProof, initialBalance);

        try {
            // Update the user in the database
            userDao.updateUser(user);

            // Redirect to admin.jsp
            response.sendRedirect("admin.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors, redirect to error.jsp or appropriate error page
            response.sendRedirect("error.jsp");
        }
    }

    private void deleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from the request
        int accNo = Integer.parseInt(request.getParameter("acc_no"));

        try {
            // Delete the user from the database
            userDao.deleteUser(accNo);

            // Redirect to admin.jsp
            response.sendRedirect("admin.jsp");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle database errors, redirect to error.jsp or appropriate error page
            response.sendRedirect("error.jsp");
        }
    }
}
