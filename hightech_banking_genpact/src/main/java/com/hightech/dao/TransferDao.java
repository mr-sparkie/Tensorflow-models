package com.hightech.dao;

import com.hightech.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TransferDao {

    // Method to get all transfer transactions for an account
    public ResultSet getAllTransfersForAccount(int accNo) throws SQLException {
        String query = "SELECT * FROM transfer WHERE sender_acc_no = ?  ORDER BY timestamp DESC";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query);
            stmt.setInt(1, accNo);
            stmt.setInt(2, accNo);
            rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            throw e;
        }
    }

    // Method to get the last 10 transfer transactions
    public ResultSet getLast10Transfers() throws SQLException {
        String query = "SELECT * FROM transfer ORDER BY timestamp DESC LIMIT 10";
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            conn = ConnectionFactory.getConnection();
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            return rs;
        } catch (SQLException e) {
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
            throw e;
        }
    }
}
