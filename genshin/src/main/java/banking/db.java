package banking;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class db {
    private static Connection conn;

    static {
        try {
            // Register the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "admin_1", "frozen");
            System.out.println("Database connection established.");
//            transaction(1001);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            System.out.println("Error establishing database connection: " + e.getMessage());
        }
    }

    public static HashMap<Integer, Integer> validate() throws SQLException {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
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
    
    public static HashMap<Integer, String> adminval() throws SQLException {
    	try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
        HashMap<Integer, String> acc = new HashMap<>();
        if (conn != null) {
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery("SELECT * FROM admins")) {
                while (rs.next()) {
                    acc.put(rs.getInt(1), rs.getString(3));
                }
                System.out.println("Query executed successfully. Result: " + acc);
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error executing query: " + e.getMessage());
            }
        }
        return acc;
    }
    
    
    
    
    
    public static int transaction(int accno) throws SQLException {
        Statement stmt = conn.createStatement();
        int n = 0;
        PreparedStatement psm = conn.prepareStatement("select intial_balance from clients where acc_no = ?");
        psm.setInt(1,accno);
        ResultSet rs = psm.executeQuery();
        if(rs.next()) { 
        n = rs.getInt("intial_balance");
        }
		return n;
    	
    }
    public static int roll(int accno,int bal) throws SQLException {
        Statement stmt = conn.createStatement();
        int n = 0;
        PreparedStatement psm = conn.prepareStatement("UPDATE clients SET intial_balance = ? WHERE acc_no = ?");
        psm.setInt(1, bal);
        psm.setInt(2, accno);
        psm.executeUpdate();

        
		return n;
    	
    }
    public static void insertStatement(int accNo, String transactionType, double transactionAmount, double updatedBalance) throws SQLException {
        String sql = "INSERT INTO statements (acc_no, transaction_type, transaction_amount, updated_balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, accNo);
            stmt.setString(2, transactionType);
            stmt.setDouble(3, transactionAmount);
            stmt.setDouble(4, updatedBalance);
            stmt.executeUpdate();
        }
    }

	public static void new_user(int acc_no, String full_name, String address, String mobile_no, String email_id,
			String acc_type, String dob, String id_proof, int initial_balance, int password) throws SQLException {
		PreparedStatement stmt = conn.prepareStatement("INSERT INTO clients (acc_no, full_name, address, mobile_no, Email_id, acc_type, dob, id_proof, intial_balance, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
        stmt.setInt(1, acc_no);
        stmt.setString(2, full_name);
        stmt.setString(3, address);
        stmt.setString(4, mobile_no);
        stmt.setString(5, email_id);
        stmt.setString(6, acc_type);
        stmt.setString(7, dob);
        stmt.setString(8, id_proof);
        stmt.setInt(9, initial_balance);
        stmt.setInt(10, password);
        stmt.executeUpdate();
	}
    public static List<HashMap<String, Object>> fetchTransactions() throws SQLException {
        List<HashMap<String, Object>> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transactions ORDER BY timestamp DESC LIMIT 10";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                HashMap<String, Object> transaction = new HashMap<>();
                transaction.put("transaction_id", rs.getInt("transaction_id"));
                transaction.put("acc_no", rs.getInt("acc_no"));
                transaction.put("type", rs.getString("type"));
                transaction.put("amount", rs.getInt("amount"));
                transaction.put("updated_balance", rs.getInt("updated_balance"));
                transaction.put("timestamp", rs.getTimestamp("timestamp").toLocalDateTime());

                transactions.add(transaction);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Rethrow the exception to be handled by the caller
        }

        return transactions;
    }
	
}
