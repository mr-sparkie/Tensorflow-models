<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank Admin Panel</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #1c1c1c;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
            color: #fff;
        }
        .admin-panel {
            background-color: #252525;
            border: 1px solid #00ff00;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            padding: 20px;
            max-width: 400px;
            width: 100%;
            box-sizing: border-box;
            text-align: center;
        }
        .admin-links {
            display: flex;
            flex-direction: column;
            gap: 10px;
            margin-top: 20px;
        }
        .admin-links a {
            padding: 15px 20px;
            font-size: 18px;
            background-color: #000;
            color: white;
            border: none;
            border-radius: 4px;
            text-decoration: none;
            cursor: pointer;
            transition: background-color 0.3s ease;
            display: block;
            text-align: center;
        }
        .admin-links a:hover {
            background-color: #333;
        }
        .logout-form {
            margin-top: 20px;
        }
        .logout-form button {
            padding: 10px 20px;
            font-size: 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }
        .logout-form button:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="admin-panel">
        <h1>Welcome, Admin!</h1>
        <div class="admin-links">
            <a href="AddAdmin.jsp">Add Admin</a>
            <a href="admin-panel.jsp">Add User</a>
        </div>
        <div class="logout-form">
            <form action="LogoutServlet" method="post">
                <button type="submit">Logout</button>
            </form>
        </div>
    </div>
</body>
</html>
