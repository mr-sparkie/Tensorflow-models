package banking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/newuser")
public class newuser extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public newuser() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get parameters from the request
        int acc_no = Integer.parseInt(request.getParameter("acc_no"));
        String full_name = request.getParameter("full_name");
        String address = request.getParameter("address");
        String mobile_no = request.getParameter("mobile_no");
        String email_id = request.getParameter("Email_id");
        String acc_type = request.getParameter("acc_type");
        String dob = request.getParameter("dob");
        String id_proof = request.getParameter("id_proof");
        int initial_balance = Integer.parseInt(request.getParameter("initial_balance"));
        int password = Integer.parseInt(request.getParameter("password"));

        // Call the method to add a new user
        try {
            db.new_user(acc_no, full_name, address, mobile_no, email_id, acc_type, dob, id_proof, initial_balance, password);
            response.sendRedirect("login_error.html"); // Redirect to a success page
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.html"); // Redirect to an error page
        }
    }
}

