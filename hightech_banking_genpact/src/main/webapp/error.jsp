<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Error Page</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .error-container {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            padding: 40px;
            width: 90%;
            max-width: 600px;
            text-align: center;
            box-sizing: border-box;
        }

        .error-title {
            font-size: 24px;
            font-weight: bold;
            color: #333333;
            margin-bottom: 20px;
        }

        .error-message {
            font-size: 18px;
            color: #666666;
            margin-bottom: 20px;
        }

        .error-actions {
            margin-top: 20px;
        }

        .error-actions a {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 4px;
            transition: background-color 0.3s ease;
        }

        .error-actions a:hover {
            background-color: #45a049;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-title">Oops! An Error Occurred</div>
        <div class="error-message">
            We're sorry, but something went wrong.
        </div>
        <div class="error-actions">
            <a href="login.jsp">Go to Homepage</a>
            <a href="contact.jsp">Contact Support</a>
        </div>
    </div>
</body>
</html>
