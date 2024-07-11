package com.hightech.dao;

import com.hightech.bean.Transaction;
import com.hightech.util.ConnectionFactory;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionDao {

    public int getBalance(int accNo) throws SQLException {
        String query = "SELECT intial_balance FROM clients WHERE acc_no = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, accNo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("intial_balance");
                }
            }
        }
        return 0;
    }

    public void updateBalance(int accNo, int balance) throws SQLException {
        String query = "UPDATE clients SET intial_balance = ? WHERE acc_no = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, balance);
            stmt.setInt(2, accNo);
            stmt.executeUpdate();
        }
    }

    public void saveTransaction(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions (acc_no, type, amount, updated_balance, timestamp) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transaction.getAccNo()); // Use getAccNo() method
            stmt.setString(2, transaction.getType());
            stmt.setBigDecimal(3, transaction.getAmount());
            stmt.setBigDecimal(4, transaction.getUpdatedBalance());
            stmt.setObject(5, transaction.getTimestamp());

            stmt.executeUpdate();
        }
    }

    public void saveDepositTransaction(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions (acc_no, type, amount, updated_balance, timestamp) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transaction.getAccNo());
            stmt.setString(2, "Deposit");
            stmt.setBigDecimal(3, transaction.getAmount());
            stmt.setBigDecimal(4, transaction.getUpdatedBalance());
            stmt.setObject(5, transaction.getTimestamp());

            stmt.executeUpdate();
        }
    }

    public void saveWithdrawTransaction(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions (acc_no, type, amount, updated_balance, timestamp) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transaction.getAccNo());
            stmt.setString(2, "Withdraw");
            stmt.setBigDecimal(3, transaction.getAmount().negate()); // Negate amount for withdraw
            stmt.setBigDecimal(4, transaction.getUpdatedBalance());
            stmt.setObject(5, transaction.getTimestamp());

            stmt.executeUpdate();
        }
    }

    public List<Transaction> getAllTransactionsForAccount(int accNo) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions WHERE acc_no = ? ORDER BY timestamp DESC";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, accNo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int transactionId = rs.getInt("transaction_id");
                    String type = rs.getString("type");
                    BigDecimal amount = rs.getBigDecimal("amount");
                    BigDecimal updatedBalance = rs.getBigDecimal("updated_balance");
                    LocalDateTime timestamp = rs.getObject("timestamp", LocalDateTime.class);

                    Transaction transaction = new Transaction(transactionId, accNo, type, amount, updatedBalance, timestamp);
                    transactions.add(transaction);
                }
            }
        }
        return transactions;
    }

    public Transaction getCurrentTransaction() throws SQLException {
        Transaction transaction = null;
        String query = "SELECT * FROM transactions ORDER BY timestamp DESC LIMIT 1";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                int transactionId = rs.getInt("transaction_id");
                int accNo = rs.getInt("acc_no");
                String type = rs.getString("type");
                BigDecimal amount = rs.getBigDecimal("amount");
                BigDecimal updatedBalance = rs.getBigDecimal("updated_balance");
                LocalDateTime timestamp = rs.getObject("timestamp", LocalDateTime.class);

                transaction = new Transaction(transactionId, accNo, type, amount, updatedBalance, timestamp);
            }
        }
        return transaction;
    }

    public List<Transaction> getLast10Transactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transactions ORDER BY timestamp DESC LIMIT 10";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int transactionId = rs.getInt("transaction_id");
                int accNo = rs.getInt("acc_no");
                String type = rs.getString("type");
                BigDecimal amount = rs.getBigDecimal("amount");
                BigDecimal updatedBalance = rs.getBigDecimal("updated_balance");
                LocalDateTime timestamp = rs.getObject("timestamp", LocalDateTime.class);

                Transaction transaction = new Transaction(transactionId, accNo, type, amount, updatedBalance, timestamp);
                transactions.add(transaction);
            }
        }
        return transactions;
    }
}
