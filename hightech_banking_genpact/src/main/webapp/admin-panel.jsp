<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New User</title>
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Roboto">
    <style>
        body {
            font-family: 'Roboto', sans-serif;
            background-color: #f0f0f0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }

        .form-container {
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0px 0px 10px rgba(0, 0, 0, 0.1);
            padding: 40px;
            width: 90%;
            max-width: 600px;
            box-sizing: border-box;
            overflow: auto; /* Enable scrolling if content exceeds container height */
            max-height: 80vh; /* Limit max height to allow scrolling */
        }

        .form-title {
            text-align: center;
            margin-bottom: 20px;
            font-size: 24px;
            font-weight: bold;
            color: #333333;
        }

        .form-input {
            margin-bottom: 20px;
        }

        .form-input label {
            display: block;
            margin-bottom: 5px;
            color: #666666;
            font-size: 16px;
        }

        .form-input input, 
        .form-input select {
            width: 100%;
            padding: 10px;
            font-size: 16px;
            border: 1px solid #cccccc;
            border-radius: 4px;
            box-sizing: border-box;
        }

        .form-input select {
            appearance: none;
            -webkit-appearance: none;
            background-image: url("data:image/svg+xml,%3Csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 24 24' fill='%23333'%3E%3Cpath d='M7 10l5 5 5-5H7z'/%3E%3C/svg%3E");
            background-position: right 10px top 50%;
            background-repeat: no-repeat;
            background-size: 24px auto;
            padding-right: 40px;
        }

        .form-submit {
            text-align: center;
        }

        .form-submit button {
            padding: 12px 24px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .form-submit button:hover {
            background-color: #45a049;
        }

        @media (max-width: 768px) {
            .form-container {
                width: 95%;
                max-width: none;
            }
        }
    </style>
</head>
<body>
    <div class="form-container">
        <div class="form-title">Add New User</div>
        <form action="NewUser" method="post">
            <div class="form-input">
                <label for="acc_no">Account Number:</label>
                <input type="text" id="acc_no" name="acc_no" required>
            </div>
            <div class="form-input">
                <label for="full_name">Full Name:</label>
                <input type="text" id="full_name" name="full_name" required>
            </div>
            <div class="form-input">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" required>
            </div>
            <div class="form-input">
                <label for="mobile_no">Mobile Number:</label>
                <input type="tel" id="mobile_no" name="mobile_no" required>
            </div>
            <div class="form-input">
                <label for="email">Email:</label>
                <input type="email" id="email" name="email" required>
            </div>
            <div class="form-input">
                <label for="acc_type">Account Type:</label>
                <select id="acc_type" name="acc_type" required>
                    <option value="">Select account type</option>
                    <option value="Savings">Savings</option>
                    <option value="Current">Current</option>
                </select>
            </div>
            <div class="form-input">
                <label for="dob">Date of Birth:</label>
                <input type="date" id="dob" name="dob" required>
            </div>
            <div class="form-input">
                <label for="id_proof">ID Proof:</label>
                <input type="text" id="id_proof" name="id_proof" required>
            </div>
            <div class="form-input">
                <label for="initial_balance">Initial Balance:</label>
                <input type="number" id="initial_balance" name="initial_balance" required>
            </div>
            <div class="form-input">
                <label for="password">Password:</label>
                <input type="password" id="password" name="password" required>
            </div>
            <div class="form-submit">
                <button type="submit">Submit</button>
            </div>
        </form>
    </div>
</body>
</html>
