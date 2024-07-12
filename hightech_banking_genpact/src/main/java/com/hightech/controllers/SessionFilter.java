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
        boolean loggedIn = (session != null && session.getAttribute("accNo") != null);
        boolean loginRequest = httpRequest.getRequestURI().equals(loginURI);
        boolean isLoginPage = httpRequest.getRequestURI().endsWith("login.jsp");

        // Check if user is trying to access admin pages directly
        boolean isAdminPage = httpRequest.getRequestURI().contains("/admin");

        if (loggedIn || loginRequest || isLoginPage) {
            // Allow logged-in users, login requests, and access to login.jsp
            chain.doFilter(request, response);
        } else if (isAdminPage) {
            // Redirect to login.jsp if trying to access admin pages without logging in as admin
            httpResponse.sendRedirect("login.jsp");
        } else {
            // Forward to login.jsp for all other unauthorized accesses
            httpResponse.sendRedirect("login.jsp");
        }
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
