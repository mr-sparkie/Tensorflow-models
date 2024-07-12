package com.hightech.controllers;

import com.hightech.bean.Admin;
import com.hightech.dao.AdminDao;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet("/NewAdmin")
public class NewAdminServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private AdminDao adminDao;

    public void init() {
        adminDao = new AdminDao();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("accNo") == null) {
            response.sendRedirect("login.jsp");
            return;
        }

        int adminId = Integer.parseInt(request.getParameter("admin_id"));
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Admin newAdmin = new Admin(adminId, username, password);

        if (adminDao.addAdmin(newAdmin)) {
            response.sendRedirect("admin.jsp"); // Redirect to admin panel page
        } else {
            // Handle error
            response.sendRedirect("error.jsp");
        }
    }
}
