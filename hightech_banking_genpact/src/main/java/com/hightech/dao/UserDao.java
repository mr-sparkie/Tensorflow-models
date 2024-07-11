package com.hightech.dao;

import com.hightech.bean.User;
import com.hightech.util.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class UserDao {
    public HashMap<Integer, Integer> validate() throws SQLException {
        HashMap<Integer, Integer> users = new HashMap<>();
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "SELECT acc_no, password FROM clients";
            try (PreparedStatement ps = conn.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    users.put(rs.getInt("acc_no"), rs.getInt("password"));
                }
            }
        }
        return users;
    }

    public int getBalance(int accNo) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "SELECT balance FROM clients WHERE acc_no = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, accNo);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        return rs.getInt("balance");
                    }
                }
            }
        }
        return 0;
    }

    public void updateBalance(int accNo, int balance) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "UPDATE clients SET balance = ? WHERE acc_no = ?";
            try (PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setInt(1, balance);
                ps.setInt(2, accNo);
                ps.executeUpdate();
            }
        }
    }

    public void addUser(User user) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection()) {
            String query = "INSERT INTO clients (acc_no, full_name, address, mobile_no, email_id, acc_type, dob, id_proof, initial_balance, password) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
                ps.setInt(10, user.getPassword());
                ps.executeUpdate();
            }
        }
    }

    public boolean isAdmin(int accNo) {
        // Implement admin validation logic here
        return false;
    }
}
