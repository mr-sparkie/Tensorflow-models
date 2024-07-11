//package banking;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet("/server")
//public class server extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    public server() {
//        super();
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        response.getWriter().append("Served at: ").append(request.getContextPath());
//    }
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int accno = Integer.parseInt(request.getParameter("username"));
//        int password = Integer.parseInt(request.getParameter("password"));
//        System.out.println("Received username: " + accno + ", password: " + password);
//        boolean n = banking.user.login(accno, password);
//        
//        if (n) {
//            response.sendRedirect("dashboard.html");
//        } else {
//            response.sendRedirect("login_error.html");
//        }
//    }
//}
package banking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/server")
public class server extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public server() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    	    int accno = Integer.parseInt(request.getParameter("username"));
    	
    	   

    	    // Check if it's an admin login
    	    boolean isAdmin = banking.user.admin(accno);

    	    if (isAdmin) {
    	        String password = request.getParameter("password");
    	    	boolean isValidAdmin = banking.user.admin_login(accno, password);
    	        if (isValidAdmin) {
    	            response.sendRedirect("admin.html");
    	        } else {
    	            response.sendRedirect("login_error.html");
    	        }
    	    } else {
    	    	 int Password  = Integer.parseInt(request.getParameter("password"));
    	        boolean isValidClient = banking.user.login(accno, Password);
    	        if (isValidClient ) {
    	            response.sendRedirect("Dashboard.html");
    	        } else {
    	            response.sendRedirect("login_error.html");
    	        }


    	    }
    	} catch (NumberFormatException e) {
    	    // Handle invalid input (non-numeric username or password)
    	    System.err.println("Error parsing login credentials: " + e.getMessage());
    	    response.sendRedirect("login_error.html");
    	} catch (Exception e) {
    	    // Handle other exceptions
    	    System.err.println("Error during login: " + e.getMessage());
    	    response.sendRedirect("login_error.html");
    	}
    }
}
