package com.hightech.controllers;

import com.hightech.service.UserService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/transaction")
public class Transaction extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    public Transaction() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accNo") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int accNo = (int) session.getAttribute("accNo");
            int balance = userService.getBalance(accNo); // Retrieve balance from user's account
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print("{\"balance\": " + balance + "}");
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.sendRedirect("error.html"); // Redirect to an error page if something goes wrong
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accNo") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        try {
            int accNo = (int) session.getAttribute("accNo");
            String type = request.getParameter("type");
            int amount = Integer.parseInt(request.getParameter("amount"));
            int minimumBalance = 1000;

            if ("deposit".equalsIgnoreCase(type)) {
                userService.deposit(accNo, amount);
            } else if ("withdraw".equalsIgnoreCase(type)) {
                int currentBalance = userService.getBalance(accNo);
                if (amount <= currentBalance - minimumBalance) { // Enforce minimum balance
                    userService.withdraw(accNo, amount);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().println("Insufficient funds. Minimum balance of " + minimumBalance + " must be maintained.");
                    return;
                }
            }

            int updatedBalance = userService.getBalance(accNo); // Get the updated balance

            // Set content type and write JSON response
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();
            out.print("{\"balance\": " + updatedBalance + "}");
            out.flush();
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Invalid transaction amount");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.sendRedirect("error.html"); // Redirect to an error page if something goes wrong
        }
    }
}
