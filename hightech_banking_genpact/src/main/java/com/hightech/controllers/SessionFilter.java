package com.hightech.controllers;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter("/*") // Apply this filter to all requests
public class SessionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false); // Use getSession(false) to not create a new session if it doesn't exist

        String loginURI = httpRequest.getContextPath() + "/login";
        String forgotPasswordURI = httpRequest.getContextPath() + "/forgotPassword";
        boolean loggedIn = (session != null && session.getAttribute("isLoggedIn") != null && (boolean) session.getAttribute("isLoggedIn"));
        boolean loginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean forgotPasswordRequest = httpRequest.getRequestURI().equals(forgotPasswordURI);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.jsp");

        // Check if the request is for a restricted page
        boolean isRestrictedPage = httpRequest.getRequestURI().endsWith("AddAdmin.jsp")
                                || httpRequest.getRequestURI().endsWith("admin.jsp")
                                || httpRequest.getRequestURI().endsWith("admin-panel.jsp")
                                || httpRequest.getRequestURI().endsWith("statement.jsp")
                                || httpRequest.getRequestURI().endsWith("Dashboard.jsp");

        if (loggedIn || loginRequest || forgotPasswordRequest || isLoginPage) {
            // Allow logged-in users, login requests, forgot password requests, and access to login.jsp
            if (isRestrictedPage && loggedIn) {
                String role = (String) session.getAttribute("role");
                if (role != null && role.equals("admin")) {
                    chain.doFilter(request, response);
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                chain.doFilter(request, response);
            }
        } else {
            // Redirect to login.jsp for all other unauthorized accesses
            httpResponse.sendRedirect("login.jsp");
        }
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
