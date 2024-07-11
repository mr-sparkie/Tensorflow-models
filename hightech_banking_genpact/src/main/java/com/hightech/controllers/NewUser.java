package com.hightech.controllers;

import com.hightech.bean.User;
import com.hightech.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/newuser")
public class NewUser extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private final UserService userService = new UserService();

    public NewUser() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        int password = Integer.parseInt(request.getParameter("password"));

        // Create a new user object
        User user = new User(accNo, fullName, address, mobileNo, emailId, accType, dob, idProof, initialBalance, password);

        // Call the method to add a new user
        try {
            userService.createUser(user);
            
            // Create a new session and set the accNo attribute
            HttpSession session = request.getSession();
            session.setAttribute("accNo", accNo);

            response.sendRedirect("admin.jsp"); // Redirect to a success page
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp"); // Redirect to an error page
        }
    }
}
