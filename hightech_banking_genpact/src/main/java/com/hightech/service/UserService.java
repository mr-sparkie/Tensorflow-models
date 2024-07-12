package com.hightech.service;

import com.hightech.bean.User;
import com.hightech.dao.AdminDao;
import com.hightech.dao.UserDao;
import com.hightech.dao.TransactionDao;

import java.sql.SQLException;
import java.util.HashMap;

public class UserService {
    private UserDao userDao = new UserDao();
    private AdminDao adminDao = new AdminDao();
    private TransactionDao transactionDao = new TransactionDao();

    public boolean login(int accNo, String password) throws SQLException {
        HashMap<Integer, String> users = userDao.validate();
        return users.containsKey(accNo) && users.get(accNo).equals(password);
    }

    public int getBalance(int accNo) throws SQLException {
        return transactionDao.getBalance(accNo);
    }

    public void updateBalance(int accNo, int balance) throws SQLException {
        transactionDao.updateBalance(accNo, balance);
    }

    public void deposit(int accNo, int amount) throws SQLException {
        int currentBalance = getBalance(accNo);
        updateBalance(accNo, currentBalance + amount);
    }

    public void withdraw(int accNo, int amount) throws SQLException {
        int currentBalance = getBalance(accNo);
        updateBalance(accNo, currentBalance - amount);
    }

    public void createUser(User user) throws SQLException {
        userDao.addUser(user);
    }

    public boolean isAdmin(int accNo) throws SQLException {
        HashMap<Integer, String> admins = adminDao.validate();
        System.out.print(admins);
        return admins.containsKey(accNo);
    }

	public void transfer(int accNo, int receiverAccNo, int amount) {
		// TODO Auto-generated method stub
		
	}
}
