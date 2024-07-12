<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Banking Dashboard</title>
    <link rel="stylesheet" href="Dashboard.css">
    <style>
        /* Inline styles can also be placed here if needed */
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background-color: #f0f0f0; /* Formal light gray background */
            color: #333; /* Dark gray text color */
        }

        .container {
            width: 100%;
            max-width: 400px;
            background-color: #ffffff; /* White background */
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1); /* Soft shadow */
            text-align: center;
            transition: box-shadow 0.3s;
        }

        .container:hover {
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.2); /* Slightly stronger shadow on hover */
        }

        h1 {
            font-size: 1.8em;
            font-weight: bold;
            color: #333; /* Dark gray text color */
            margin-bottom: 20px;
        }

        p {
            font-size: 1.2em;
            margin-bottom: 20px;
        }

        input[type="number"] {
            width: calc(100% - 20px);
            padding: 10px;
            font-size: 1em;
            border: 1px solid #ccc; /* Light gray border */
            border-radius: 4px;
            background-color: #f9f9f9; /* Light gray background */
            color: #333; /* Dark gray text color */
            margin-bottom: 20px;
        }

        button {
            width: calc(100% - 20px);
            padding: 10px;
            font-size: 1em;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: transform 0.3s, background-color 0.3s;
            margin-bottom: 15px;
        }

        button.deposit {
            background-color: #4CAF50; /* Green deposit button */
            color: #fff; /* White text color */
        }

        button.withdraw {
            background-color: #f44336; /* Red withdraw button */
            color: #fff; /* White text color */
        }

        button.statement {
            background-color: #2196F3; /* Blue statement button */
            color: #fff; /* White text color */
        }

        button.exit {
            background-color: #757575; /* Dark gray logout button */
            color: #fff; /* White text color */
        }

        button:hover {
            transform: translateY(-5px);
        }

        button.deposit:hover {
            background-color: #45a049; /* Darker green on hover */
        }

        button.withdraw:hover {
            background-color: #c82333; /* Darker red on hover */
        }

        button.statement:hover {
            background-color: #0b7dda; /* Darker blue on hover */
        }

        button.exit:hover {
            background-color: #5a6268; /* Darker gray on hover */
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Banking Dashboard</h1>
        <p>Balance: <span id="balance">$0.00</span></p>
        
        <input type="number" id="amount" placeholder="Enter amount">
        
        <button class="deposit" onclick="handleDeposit()">Deposit</button>
        <button class="withdraw" onclick="handleWithdraw()">Withdraw</button>
        <button class="statement" onclick="generateStatement()">Generate Statement</button>
        <form action="LogoutServlet" method="post">
            <button type="submit" class="exit">Logout</button>
        </form>

        <div id="message" class="message"></div>

        <div id="statement-container"></div>
    </div>

    <script src="Dashboard.js"></script>
</body>
</html>
