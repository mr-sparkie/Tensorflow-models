

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
        int accno = Integer.parseInt(request.getParameter("username"));
        int password = Integer.parseInt(request.getParameter("password"));
        boolean n = banking.user.login(accno, password);
        if (n) {
            response.sendRedirect("dashboard.html");
        } else {
            response.sendRedirect("login_error.html");
        }
    }
}
