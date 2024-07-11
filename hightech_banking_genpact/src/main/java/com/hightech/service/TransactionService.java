package com.hightech.service;

import com.hightech.dao.TransactionDao;

import java.sql.SQLException;

public class TransactionService {
    private TransactionDao transactionDao = new TransactionDao();

    public int getBalance(int accNo) {
        try {
            return transactionDao.getBalance(accNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void updateBalance(int accNo, int balance) {
        try {
            transactionDao.updateBalance(accNo, balance);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
