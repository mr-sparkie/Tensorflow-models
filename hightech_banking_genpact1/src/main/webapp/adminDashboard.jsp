<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.hightech.bean.Task" %>
<%@ page import="java.sql.Connection, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException,java.sql.Date,java.sql.Time" %>
<%@ page import="com.hightech.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Admin Dashboard</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css">
    <link rel="stylesheet" href="gojoPalette.css">

</head>
<body>

<div class="container">
    <h1>Admin Dashboard</h1>
    
    <div class="tabs">
        <button class="tab-button active" data-tab="viewTasks">View Tasks</button>
        <button class="tab-button" data-tab="addTask">Add Task</button>
        <button class="tab-button" data-tab="updateTask">Update Task</button>
        <button class="tab-button" data-tab="deleteTask">Delete Task</button>
    </div>
    
    <div id="viewTasks" class="tab-content active">
        <h2>View Tasks</h2>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <c:if test="${not empty message}">
            <div class="alert alert-success">${message}</div>
        </c:if>
<table>
    <thead>
        <tr>
            <th>Task ID</th>
            <th>User ID</th>
            <th>Project</th>
            <th>Date</th>
            <th>Start Time</th>
            <th>End Time</th>
            <th>Duration</th>
            <th>Task Category</th>
            <th>Description</th>
        </tr>
    </thead>
    <tbody>
        <% 
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConnection();

            String query = "SELECT * FROM tasks "; // Assuming 'user_id' is used to filter tasks
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();

            boolean tasksFound = false;
            while (rs.next()) {
                tasksFound = true;
                int taskId = rs.getInt("task_id");
                int userId = rs.getInt("user_id");
                String project = rs.getString("project");
                Date date = rs.getDate("date");
                Time startTime = rs.getTime("start_time");
                Time endTime = rs.getTime("end_time");
                float duration = rs.getFloat("duration");
                String taskCategory = rs.getString("task_category");
                String description = rs.getString("description");

                // Displaying task details in table rows
        %>
                <tr>
                    <td><%= taskId %></td>
                    <td><%= userId %></td>
                    <td><%= project %></td>
                    <td><%= date %></td>
                    <td><%= startTime %></td>
                    <td><%= endTime %></td>
                    <td><%= duration %></td>
                    <td><%= taskCategory %></td>
                    <td><%= description %></td>
                </tr>
        <% 
            }

            if (!tasksFound) {
        %>
                <tr>
                    <td colspan="9">No tasks found.</td>
                </tr>
        <% 
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close resources in finally block
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        %>
    </tbody>
</table>
</div>
    
    <div id="addTask" class="tab-content">
        <h2>Add Task</h2>
        <form action="taskManager" method="post">
            <input type="hidden" name="action" value="add">
            <div class="form-group">
                <label for="user_id">User ID</label>
                <input type="number" id="user_id" name="user_id" required>
            </div>
            <div class="form-group">
                <label for="project">Project</label>
                <input type="text" id="project" name="project" required>
            </div>
            <div class="form-group">
                <label for="date">Date</label>
                <input type="date" id="date" name="date" required>
            </div>
            <div class="form-group">
                <label for="start_time">Start Time</label>
                <input type="time" id="start_time" name="start_time" required>
            </div>
            <div class="form-group">
                <label for="end_time">End Time</label>
                <input type="time" id="end_time" name="end_time" required>
            </div>
            <div class="form-group">
                <label for="duration">Duration</label>
                <input type="number" step="0.01" id="duration" name="duration" required>
            </div>
            <div class="form-group">
                <label for="task_category">Task Category</label>
                <input type="text" id="task_category" name="task_category" required>
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description" required></textarea>
            </div>
            <div class="form-group">
                <button type="submit">Add Task</button>
            </div>
        </form>
    </div>
    
    <div id="updateTask" class="tab-content">
        <h2>Update Task</h2>
        <form action="taskManager" method="post">
            <input type="hidden" name="action" value="update">
            <div class="form-group">
                <label for="task_id">Task ID</label>
                <input type="number" id="task_id" name="task_id" required>
            </div>
            <div class="form-group">
                <label for="user_id">User ID</label>
                <input type="number" id="user_id" name="user_id" required>
            </div>
            <div class="form-group">
                <label for="project">Project</label>
                <input type="text" id="project" name="project" required>
            </div>
            <div class="form-group">
                <label for="date">Date</label>
                <input type="date" id="date" name="date" required>
            </div>
            <div class="form-group">
                <label for="start_time">Start Time</label>
                <input type="time" id="start_time" name="start_time" required>
            </div>
            <div class="form-group">
                <label for="end_time">End Time</label>
                <input type="time" id="end_time" name="end_time" required>
            </div>
            <div class="form-group">
                <label for="duration">Duration</label>
                <input type="number" step="0.01" id="duration" name="duration" required>
            </div>
            <div class="form-group">
                <label for="task_category">Task Category</label>
                <input type="text" id="task_category" name="task_category" required>
            </div>
            <div class="form-group">
                <label for="description">Description</label>
                <textarea id="description" name="description" required></textarea>
            </div>
            <div class="form-group">
                <button type="submit">Update Task</button>
            </div>
        </form>
    </div>
    
    <div id="deleteTask" class="tab-content">
        <h2>Delete Task</h2>
        <form action="taskManager" method="post">
            <input type="hidden" name="action" value="delete">
            <div class="form-group">
                <label for="task_id">Task ID</label>
                <input type="number" id="task_id" name="task_id" required>
            </div>
            <div class="form-group">
                <button type="submit">Delete Task</button>
            </div>
        </form>
    </div>
</div>

<script>
    const tabs = document.querySelectorAll('.tab-button');
    const contents = document.querySelectorAll('.tab-content');

    tabs.forEach(tab => {
        tab.addEventListener('click', () => {
            tabs.forEach(btn => btn.classList.remove('active'));
            tab.classList.add('active');
            const target = tab.getAttribute('data-tab');
            contents.forEach(content => {
                if (content.id === target) {
                    content.classList.add('active');
                } else {
                    content.classList.remove('active');
                }
            });
        });
    });
</script>

</body>
</html>
