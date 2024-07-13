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

    // Method to get balance for an account
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

    // Method to update balance for an account
    public void updateBalance(int accNo, int balance) throws SQLException {
        String query = "UPDATE clients SET intial_balance = ? WHERE acc_no = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, balance);
            stmt.setInt(2, accNo);
            stmt.executeUpdate();
        }
    }

    // Method to save a transaction (generic)
    public void saveTransaction(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions (acc_no, type, amount, updated_balance, timestamp) " +
                "VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, transaction.getAccNo());
            stmt.setString(2, transaction.getType());
            stmt.setBigDecimal(3, transaction.getAmount());
            stmt.setBigDecimal(4, transaction.getUpdatedBalance());
            stmt.setObject(5, transaction.getTimestamp());

            stmt.executeUpdate();
        }
    }

    // Method to save a deposit transaction
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

    // Method to save a withdrawal transaction
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

    // Method to get all transactions for an account
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

    // Method to get the current (latest) transaction
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

    // Method to get the last 10 transfer transactions
    public List<Transaction> getLast10TransferTransactions() throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT * FROM transfer ORDER BY timestamp DESC LIMIT 10";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int transactionId = rs.getInt("transaction_id");
                int accNo = rs.getInt("acc_no");
                int receiverAccNo = rs.getInt("receiver_acc_no");
                BigDecimal amount = rs.getBigDecimal("amount");
                LocalDateTime timestamp = rs.getObject("timestamp", LocalDateTime.class);

                Transaction transaction = new Transaction(transactionId, accNo, "Transfer", amount, null, timestamp);
                transaction.setReceiverAccNo(receiverAccNo);
                transactions.add(transaction);
            }
        }
        return transactions;
    }

    // Method to create a trigger in the database for balance updates
    public void createTriggerForBalanceUpdate() throws SQLException {
        String triggerQuery = "CREATE TRIGGER balance_update_trigger " +
                "AFTER UPDATE ON clients " +
                "FOR EACH ROW " +
                "BEGIN " +
                "    INSERT INTO transactions (acc_no, type, amount, updated_balance, timestamp) " +
                "    VALUES (NEW.acc_no, 'Balance Update', NEW.intial_balance - OLD.intial_balance, NEW.intial_balance, NOW()); " +
                "END";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(triggerQuery)) {
            stmt.executeUpdate();
        }
    }

    // Method to drop the trigger from the database
    public void dropTriggerForBalanceUpdate() throws SQLException {
        String triggerQuery = "DROP TRIGGER IF EXISTS balance_update_trigger";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(triggerQuery)) {
            stmt.executeUpdate();
        }
    }

	public void recordTransfer(int accNo, int receiverAccNo, int amount) {
		// TODO Auto-generated method stub
		
	}
}
