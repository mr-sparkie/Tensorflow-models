package com.hightech.controllers;

import com.hightech.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

public class Transaction extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserService userService = new UserService();

    public Transaction() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accNo") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            int accNo = (int) session.getAttribute("accNo");
            int balance = userService.getBalance(accNo);
            out.print("{\"balance\": " + balance + "}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.sendRedirect("error.html");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accNo") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        response.setContentType("application/json");
        try (PrintWriter out = response.getWriter()) {
            int accNo = (int) session.getAttribute("accNo");
            String type = request.getParameter("type");
            int amount = Integer.parseInt(request.getParameter("amount"));
            int minimumBalance = 1000;

            if ("deposit".equalsIgnoreCase(type)) {
                userService.deposit(accNo, amount);
            } else if ("withdraw".equalsIgnoreCase(type)) {
                int currentBalance = userService.getBalance(accNo);
                if (amount <= currentBalance - minimumBalance) {
                    userService.withdraw(accNo, amount);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\": \"Insufficient funds. Minimum balance of " + minimumBalance + " must be maintained.\"}");
                    return;
                }
            } else if ("transfer".equalsIgnoreCase(type)) {
                int receiverAccNo = Integer.parseInt(request.getParameter("receiverAccNo"));
                int currentBalance = userService.getBalance(accNo);
                if (amount <= currentBalance - minimumBalance) {
                    userService.transfer(accNo, receiverAccNo, amount);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    out.print("{\"error\": \"Insufficient funds. Minimum balance of " + minimumBalance + " must be maintained.\"}");
                    return;
                }
            }

            int updatedBalance = userService.getBalance(accNo);
            out.print("{\"balance\": " + updatedBalance + "}");
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.print("{\"error\": \"Invalid transaction amount\"}");
        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.sendRedirect("error.html");
        }
    }
}
