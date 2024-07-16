package com.hightech.controller;

import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import com.hightech.util.DbUtil;

//@WebServlet("/taskManager")
public class TaskManagerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if (action != null && !action.isEmpty()) {
            switch (action) {
                case "add":
                    addTask(request, response);
                    break;
                case "update":
                    updateTask(request, response);
                    break;
                case "delete":
                    deleteTask(request, response);
                    break;
                default:
                    response.sendRedirect("taskManagement.jsp?error=Invalid action");
            }
        } else {
            response.sendRedirect("taskManagement.jsp?error=Action parameter not specified");
        }
    }

    private void addTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        int userId = Integer.parseInt(request.getParameter("user_id"));
        String project = request.getParameter("project");
        String dateStr = request.getParameter("date");
        String startTimeStr = request.getParameter("start_time");
        String endTimeStr = request.getParameter("end_time");
        BigDecimal duration = new BigDecimal(request.getParameter("duration"));
        String taskCategory = request.getParameter("task_category");
        String description = request.getParameter("description");

        // Parse date and time strings
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO tasks (user_id, project, date, start_time, end_time, duration, task_category, description) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?, ?)")) {

            java.util.Date parsedDate = dateFormat.parse(dateStr);
            java.sql.Date date = new java.sql.Date(parsedDate.getTime());

            java.util.Date parsedStartTime = timeFormat.parse(startTimeStr);
            java.sql.Time startTime = new java.sql.Time(parsedStartTime.getTime());

            java.util.Date parsedEndTime = timeFormat.parse(endTimeStr);
            java.sql.Time endTime = new java.sql.Time(parsedEndTime.getTime());

            // Set parameters for the prepared statement
            stmt.setInt(1, userId);
            stmt.setString(2, project);
            stmt.setDate(3, date);
            stmt.setTime(4, startTime);
            stmt.setTime(5, endTime);
            stmt.setBigDecimal(6, duration);
            stmt.setString(7, taskCategory);
            stmt.setString(8, description);

            // Execute the statement
            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                response.sendRedirect("taskManagement.jsp?message=Task added successfully&action=add");
            } else {
                response.sendRedirect("taskManagement.jsp?error=Failed to add task&action=add");
            }

        } catch (SQLException | ParseException ex) {
            ex.printStackTrace();
            response.sendRedirect("taskManagement.jsp?error=An error occurred: " + ex.getMessage() + "&action=add");
        }
    }

    private void updateTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form parameters
        int taskId = Integer.parseInt(request.getParameter("task_id"));
        int userId = Integer.parseInt(request.getParameter("user_id"));
        String project = request.getParameter("project");
        String dateStr = request.getParameter("date");
        String startTimeStr = request.getParameter("start_time");
        String endTimeStr = request.getParameter("end_time");
        BigDecimal duration = new BigDecimal(request.getParameter("duration"));
        String taskCategory = request.getParameter("task_category");
        String description = request.getParameter("description");

        // Parse date and time strings
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE tasks SET user_id = ?, project = ?, date = ?, start_time = ?, end_time = ?, " +
                             "duration = ?, task_category = ?, description = ? WHERE task_id = ?")) {

            java.util.Date parsedDate = dateFormat.parse(dateStr);
            java.sql.Date date = new java.sql.Date(parsedDate.getTime());

            java.util.Date parsedStartTime = timeFormat.parse(startTimeStr);
            java.sql.Time startTime = new java.sql.Time(parsedStartTime.getTime());

            java.util.Date parsedEndTime = timeFormat.parse(endTimeStr);
            java.sql.Time endTime = new java.sql.Time(parsedEndTime.getTime());

            // Set parameters for the prepared statement
            stmt.setInt(1, userId);
            stmt.setString(2, project);
            stmt.setDate(3, date);
            stmt.setTime(4, startTime);
            stmt.setTime(5, endTime);
            stmt.setBigDecimal(6, duration);
            stmt.setString(7, taskCategory);
            stmt.setString(8, description);
            stmt.setInt(9, taskId);

            // Execute the statement
            int rowsUpdated = stmt.executeUpdate();
            if (rowsUpdated > 0) {
                response.sendRedirect("taskManagement.jsp?message=Task updated successfully&action=update");
            } else {
                response.sendRedirect("taskManagement.jsp?error=Failed to update task&action=update");
            }

        } catch (SQLException | ParseException ex) {
            ex.printStackTrace();
            response.sendRedirect("taskManagement.jsp?error=An error occurred: " + ex.getMessage() + "&action=update");
        }
    }

    private void deleteTask(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve task ID parameter
        int taskId = Integer.parseInt(request.getParameter("task_id"));

        try (Connection conn = DbUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM tasks WHERE task_id = ?")) {

            // Set parameter for the prepared statement
            stmt.setInt(1, taskId);

            // Execute the statement
            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                response.sendRedirect("taskManagement.jsp?message=Task deleted successfully&action=delete");
            } else {
                response.sendRedirect("taskManagement.jsp?error=Failed to delete task&action=delete");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
            response.sendRedirect("taskManagement.jsp?error=An error occurred: " + ex.getMessage() + "&action=delete");
        }
    }
}
