package com.hightech.bean;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transaction {
    private int transactionId;
    private int accNo; // Assuming this is the account number field
    private String type; // deposit or withdraw
    private BigDecimal amount;
    private BigDecimal updatedBalance;
    private LocalDateTime timestamp;

    public Transaction() {
        // Default constructor
    }

    public Transaction(int transactionId, int accNo, String type, BigDecimal amount, BigDecimal updatedBalance, LocalDateTime timestamp) {
        this.transactionId = transactionId;
        this.accNo = accNo;
        this.type = type;
        this.amount = amount;
        this.updatedBalance = updatedBalance;
        this.timestamp = timestamp;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getAccNo() {
        return accNo;
    }

    public void setAccNo(int accNo) {
        this.accNo = accNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getUpdatedBalance() {
        return updatedBalance;
    }

    public void setUpdatedBalance(BigDecimal updatedBalance) {
        this.updatedBalance = updatedBalance;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
