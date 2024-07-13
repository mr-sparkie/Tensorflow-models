package com.hightech.dao;

import com.hightech.bean.User;
import com.hightech.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UserDao {

    public HashMap<Integer, String> validate() throws SQLException {
        HashMap<Integer, String> users = new HashMap<>();
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "SELECT acc_no, password FROM clients";
            try (PreparedStatement ps = conn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.put(rs.getInt("acc_no"), rs.getString("password"));
                }
            }
        }
        return users;
    }

    public int getBalance(int accNo) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "SELECT intial_balance FROM clients WHERE acc_no = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, accNo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("intial_balance");
                    }
                }
            }
        }
        return 0;
    }

    public void updateBalance(int accNo, int balance) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "UPDATE clients SET intial_balance = ? WHERE acc_no = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, balance);
                ps.setInt(2, accNo);
                ps.executeUpdate();
            }
        }
    }

    public void addUser(User user) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "INSERT INTO clients (acc_no, full_name, address, mobile_no, email_id, acc_type, dob, id_proof, intial_balance, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, user.getAccNo());
                ps.setString(2, user.getFullName());
                ps.setString(3, user.getAddress());
                ps.setString(4, user.getMobileNo());
                ps.setString(5, user.getEmail());
                ps.setString(6, user.getAccType());
                ps.setString(7, user.getDob());
                ps.setString(8, user.getIdProof());
                ps.setInt(9, user.getInitialBalance());
                ps.setString(10, "");
                ps.executeUpdate();
            }
        }
    }

    public void updateUser(User user) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "UPDATE clients SET full_name = ?, address = ?, mobile_no = ?, email_id = ?, acc_type = ?, dob = ?, id_proof = ?, intial_balance = ? WHERE acc_no = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, user.getFullName());
                ps.setString(2, user.getAddress());
                ps.setString(3, user.getMobileNo());
                ps.setString(4, user.getEmail());
                ps.setString(5, user.getAccType());
                ps.setString(6, user.getDob());
                ps.setString(7, user.getIdProof());
                ps.setInt(8, user.getInitialBalance());
                ps.setInt(9, user.getAccNo());
                ps.executeUpdate();
            }
        }
    }

    public void deleteUser(int accNo) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "DELETE FROM clients WHERE acc_no = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, accNo);
                ps.executeUpdate();
            }
        }
    }

    public boolean isAdmin(int accNo) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "SELECT is_admin FROM clients WHERE acc_no = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, accNo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getBoolean("is_admin");
                    }
                }
            }
        }
        return false;
    }

    public User getUserDetails(int accNo) throws SQLException {
        User user = null;
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "SELECT * FROM clients WHERE acc_no = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, accNo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        user = new User(
                                rs.getInt("acc_no"),
                                rs.getString("full_name"),
                                rs.getString("address"),
                                rs.getString("mobile_no"),
                                rs.getString("email_id"),
                                rs.getString("acc_type"),
                                rs.getString("dob"),
                                rs.getString("id_proof"),
                                rs.getInt("intial_balance")
                        );
                    }
                }
            }
        }
        return user;
    }

    public boolean updatePassword(int username, String newPassword, String oldPassword) {
        String selectQuery = "SELECT password FROM clients WHERE acc_no = ?";
        String updateQuery = "UPDATE clients SET password = ? WHERE acc_no = ?";
        String currentPassword = "";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement selectStmt = conn.prepareStatement(selectQuery)) {
            
            selectStmt.setInt(1, username);
            
            try (ResultSet rs = selectStmt.executeQuery()) {
                if (rs.next()) {
                    currentPassword = rs.getString("password");
                } else {
                    // Account number not found
                    System.out.println("Account number not found.");
                    return false;
                }
            }

            // Check if the current password matches the old password provided
            if (currentPassword.equals(oldPassword)) {
                try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                    updateStmt.setString(1, newPassword);
                    updateStmt.setInt(2, username);
                    
                    int updatedRows = updateStmt.executeUpdate();
                    if (updatedRows > 0) {
                        System.out.println("Password updated successfully.");
                        return true;
                    } else {
                        System.out.println("Password update failed.");
                        return false;
                    }
                }
            } else {
                // Old password does not match
                System.out.println("Old password does not match.");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



    public int getPasswordStatus(int accNo) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "SELECT password_changed FROM client_password_status WHERE acc_no = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, accNo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("password_changed");
                    }
                }
            }
        }
        return -1; // Return -1 if no status found (handle as needed in your application)
    }
}
