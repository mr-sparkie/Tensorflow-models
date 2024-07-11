package com.hightech.dao;

import com.hightech.bean.Admin;
import com.hightech.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AdminDao {

    public HashMap<Integer, String> validate() {
        HashMap<Integer, String> admins = new HashMap<>();
        String query = "SELECT * FROM admins";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                admins.put(rs.getInt("admin_id"), rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return admins;
    }

    public boolean addAdmin(Admin admin) {
        String query = "INSERT INTO admins (admin_id, username, password) VALUES (?, ?, ?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, admin.getAdminId());
            stmt.setString(2, admin.getUsername());
            stmt.setString(3, admin.getPassword());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
