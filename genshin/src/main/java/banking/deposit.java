package banking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet("/deposit")
public class deposit extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public deposit() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int balance = user.money(); // Retrieve balance from user's account
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
        try {
            String type = request.getParameter("type");
            int amount = Integer.parseInt(request.getParameter("amount"));
            int minimumBalance = 1000;

            if ("deposit".equalsIgnoreCase(type)) {
                user.setmoney(user.money() + amount);
            } else if ("withdraw".equalsIgnoreCase(type)) {
                int currentBalance = user.money();
                if (amount <= currentBalance - minimumBalance) { // Enforce minimum balance
                    user.setmoney(currentBalance - amount);
                } else {
                    response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    response.getWriter().println("Insufficient funds. Minimum balance of " + minimumBalance + " must be maintained.");
                    return;
                }
            }

            int updatedBalance = user.money(); // Get the updated balance

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
