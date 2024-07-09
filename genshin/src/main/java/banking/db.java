package banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class db {
    private static Connection conn;

    static {
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "admin_1", "frozen");
            System.out.println("Database connection established.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error establishing database connection: " + e.getMessage());
        }
    }

    public static HashMap<Integer, Integer> validate() throws SQLException {
        HashMap<Integer, Integer> acc = new HashMap<>();
        if (conn != null) {
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM clients")) {
                while (rs.next()) {
                    acc.put(rs.getInt(1), rs.getInt(10));
                }
                System.out.println("Query executed successfully. Result: " + acc);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing query: " + e.getMessage());
            }
        }
        return acc;
    }
}
