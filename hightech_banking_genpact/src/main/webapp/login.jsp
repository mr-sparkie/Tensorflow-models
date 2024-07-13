<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bank Login</title>
    <style>
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
            background: linear-gradient(135deg, #000, #333);
            z-index: -1;
            animation: pulseBackground 8s ease-in-out infinite alternate;
        }

        .overlay {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
        }

        @keyframes pulseBackground {
            0% { background-position: 0% 50%; }
            100% { background-position: 100% 50%; }
        }

        .container {
            position: relative;
            width: 100%;
            max-width: 500px;
            z-index: 1;
        }

        .login-container {
            position: relative;
            background-color: rgba(0, 0, 0, 0.8);
            padding: 40px;
            border-radius: 10px;
            overflow: hidden;
            text-align: center;
            box-shadow: 0 10px 20px rgba(0, 255, 255, 0.5);
            transition: box-shadow 0.3s ease-in-out;
        }

        .login-container:hover {
            box-shadow: 0 10px 40px rgba(255, 255, 255, 0.8);
        }

        .neon-text {
            font-size: 2em;
            color: #fff;
            text-shadow: 0 0 10px #fff, 0 0 20px #fff, 0 0 30px #fff, 0 0 40px #0ff, 0 0 70px #0ff, 0 0 80px #0ff, 0 0 100px #0ff, 0 0 150px #0ff;
            margin-bottom: 30px;
            animation: neonGlow 1.5s ease-in-out infinite alternate;
        }

        @keyframes neonGlow {
            0% { filter: brightness(150%); transform: translateY(-2px); }
            100% { filter: brightness(200%); transform: translateY(2px); }
        }

        .login-form {
            max-width: 400px;
            width: 100%;
            margin-top: 30px;
            margin-bottom: 20px;
            padding: 0 20px;
        }

        .input-group {
            margin-bottom: 15px;
            text-align: left;
        }

        .input-group label {
            display: block;
            margin-bottom: 8px;
            color: #fff;
            font-size: 0.9em;
        }

        .input-group input {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
            background-color: rgba(255, 255, 255, 0.1);
            color: #fff;
        }

        button[type="submit"], button[type="button"] {
            width: 100%;
            background-color: #0ff;
            color: #000;
            padding: 10px 0;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            font-size: 16px;
            margin-top: 10px;
            transition: background-color 0.3s;
        }

        button[type="submit"]:hover, button[type="button"]:hover {
            background-color: #39ff14;
        }

         .success-message {
            color: cyan;
            margin-bottom: 15px;
            font-size: 0.9em;
             transition: opacity 0.5s ease; 
        }
         .error-message{
            color: red;
            margin-bottom: 15px;
            font-size: 0.9em;
             transition: opacity 0.5s ease; 
        }
        

        .forgot-password-link {
            color: #0ff;
            cursor: pointer;
            font-size: 0.9em;
            text-decoration: underline;
            margin-top: 10px;
            display: block;
            transition: color 0.3s;
        }

        .forgot-password-link:hover {
            color: #39ff14;
        }

        .popup {
            display: none;
            position: fixed;
            top: 50%;
            left: 50%;
            transform: translate(-50%, -50%);
            background-color: rgba(0, 0, 0, 0.8);
            padding: 20px;
            border-radius: 10px;
            z-index: 2;
        }

        .popup .input-group {
            margin-bottom: 10px;
        }

        .popup button {
            background-color: cyan;
            margin-top: 10px;
        }

        .popup-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background-color: rgba(0, 0, 0, 0.5);
            backdrop-filter: blur(5px); /* Adjust the blur effect */
            z-index: 1;
            display: none;
        }
    </style>
</head>
<body>
    <div class="background">
        <div class="overlay"></div>
    </div>
    <div class="container">
        <div class="login-container">
            <h2 class="neon-text">Bank Login</h2>
            <form class="login-form" action="login" method="post">
                <input type="hidden" name="action" value="login">
                <div class="input-group">
                    <label for="username">Account Number</label>
                    <input type="text" id="username" name="username" required>
                </div>
                <div class="input-group">
                    <label for="password">Password</label>
                    <input type="password" id="password" name="password" required>
                </div>
                <button type="submit">Login</button>
                <%-- Display error message if login fails --%>
                <% if (request.getAttribute("errorMessage") != null) { %>
                    <div class="error-message"><%= request.getAttribute("errorMessage") %></div>
                <% } %>

                <%-- Display success message if password changed --%>
                <% if (session.getAttribute("successMessage") != null) { %>
                    <div class="success-message"><%= session.getAttribute("successMessage") %></div>
                    <% session.removeAttribute("successMessage"); // Remove message after displaying %>
                <% } %>
                <!-- Display error message if present -->
            </form>

            <a class="forgot-password-link" onclick="showPopup('forgotPasswordPopup')">Forgot Password?</a>
        </div>
    </div>
    <div class="popup" id="forgotPasswordPopup">
        <div class="popup-overlay"></div>
        <div class="popup-content">
            <form id="forgotPasswordForm" action="forgotPassword" method="post">
                <input type="hidden" name="action" value="forgotPassword">
                <div class="input-group">
                    <label for="acc_no">Account Number</label>
                    <input type="text" id="acc_no" name="accno" required>
                </div>
                <div class="input-group">
                    <label for="oldPassword">Old Password</label>
                    <input type="password" id="oldPassword" name="oldPassword" required>
                </div>
                <div class="input-group">
                    <label for="newPassword">New Password</label>
                    <input type="password" id="newPassword" name="newPassword" required>
                </div>
                <div class="input-group">
                    <label for="confirmPassword">Confirm New Password</label>
                    <input type="password" id="confirmPassword" name="confirmPassword" required>
                </div>
                <button type="submit">Change Password</button>
            </form>
            <button class="close-button" onclick="hidePopup('forgotPasswordPopup')">Close</button>
        </div>
    </div>

    <script>
        function showPopup(popupId) {
            document.getElementById(popupId).style.display = 'block';
            document.body.style.overflow = 'hidden'; // Prevent scrolling when popup is open
        }

        function hidePopup(popupId) {
            document.getElementById(popupId).style.display = 'none';
            document.body.style.overflow = 'auto'; // Enable scrolling when popup is closed
        }
    </script>
</body>
</html>
