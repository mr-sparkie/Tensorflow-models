<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Error</title>
    <style>
        /* Embedded CSS styles */
        body, html {
            margin: 0;
            padding: 0;
            height: 100%;
            font-family: Arial, sans-serif;
            background: url('login_error.jpg') no-repeat center center fixed;
            background-size: cover;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        .error-container {
            background: rgba(255, 255, 255, 0.8);
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            text-align: center;
        }

        .error-container p {
            color: red;
            font-size: 18px;
            margin-bottom: 20px;
        }

        button {
            padding: 10px 20px;
            border: none;
            border-radius: 4px;
            background: #d9534f;
            color: white;
            font-size: 16px;
            cursor: pointer;
        }

        button:hover {
            background: #c9302c;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <p>Your credentials mismatch, please try again!!</p>
        <button type="button" onclick="tryAgain()">Try Again</button>

        <script>
            function tryAgain() {
                window.location.href = "http://localhost:8080/hightech_banking_genpact/login.jsp"; // Replace with the URL of your login JSP page
            }
        </script>
    </div>
</body>
</html>
