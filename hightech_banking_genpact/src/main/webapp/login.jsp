<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Advanced Login Page</title>
    <style>
        /* CSS styles embedded directly within the JSP page */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #000;
            overflow: hidden;
        }

        .background {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: black;
            z-index: -1;
            animation: pulseBackground 8s ease-in-out infinite alternate;
        }

        .overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5); /* Adjust overlay transparency */
        }

        @keyframes pulseBackground {
            0% {
                background-position: 0% 50%;
            }
            100% {
                background-position: 100% 50%;
            }
        }

        .container {
            position: relative;
            width: 100%;
            max-width: 500px;
            z-index: 1; /* Ensure container is above background */
        }

        .login-container {
            position: relative;
            background-color: rgba(0, 0, 0, 0.8);
            padding: 40px;
            border-radius: 10px;
            overflow: hidden;
            text-align: center;
            box-shadow: 0 10px 20px rgba(0, 255, 255, 0.5); /* Initial box shadow */
            transition: box-shadow 0.3s ease-in-out; /* Transition for box shadow */
        }

        .login-container:hover {
            box-shadow: 0 10px 40px rgba(255, 255, 255, 0.8); /* Hover box shadow */
        }

        .neon-text {
            font-size: 2em;
            color: #fff;
            text-shadow: 0 0 10px #fff, 0 0 20px #fff, 0 0 30px #fff, 0 0 40px #0ff, 0 0 70px #0ff, 0 0 80px #0ff, 0 0 100px #0ff, 0 0 150px #0ff;
            margin-bottom: 30px;
            animation: neonGlow 1.5s ease-in-out infinite alternate;
        }

        @keyframes neonGlow {
            0% {
                filter: brightness(150%);
                transform: translateY(-2px);
            }
            100% {
                filter: brightness(200%);
                transform: translateY(2px);
            }
        }

        .login-form {
            max-width: 400px;
            width: 100%;
            margin-top: 30px; /* Adjust top margin for spacing */
        }

        .input-group {
            margin-bottom: 15px;
            text-align: left; /* Ensure text alignment inside input groups */
        }

        .input-group label {
            display: block;
            margin-bottom: 8px;
            color: #fff;
        }

        .input-group input {
            width: calc(100% - 20px); /* Adjust width for padding */
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            background-color: rgba(255, 255, 255, 0.1);
            color: #fff;
        }

        button[type="submit"] {
            width: 100%;
            background-color: #0ff;
            color: #000;
            padding: 10px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            transition: background-color 0.3s;
        }

        button[type="submit"]:hover {
            background-color: #39ff14;
        }
    </style>
</head>
<body>
    <div class="background">
        <div class="overlay"></div>
    </div>
    <div class="container">
        <div class="login-container">
            <h2 class="neon-text">Login</h2>
            <form class="login-form" action="login" method="POST">
                <div class="input-group">
                    <label for="username">Username</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="input-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit">Login</button>
            </form>
        </div>
    </div>
</body>
</html>
