package com.hightech.service;

import com.hightech.dao.AdminDao;

import java.sql.SQLException;
import java.util.HashMap;

public class AdminService {
    private AdminDao adminDao = new AdminDao();

    /**
     * Validates admin login credentials.
     *
     * @param accNo    The admin's account number
     * @param password The admin's password
     * @return true if login credentials are valid, false otherwise
     * @throws SQLException if there is an issue with database access
     */
    public boolean adminLogin(int accNo, String password) throws SQLException {
        HashMap<Integer, String> admins = adminDao.validate();

        // Check if the provided account number exists in the admin map
        // and if the corresponding password matches the provided password
        return admins.containsKey(accNo) && admins.get(accNo).equals(password);
    }
}
