<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transaction History</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #ffffff;
            margin: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
        }

        .transaction-container {
            width: 100%;
            max-width: 800px;
            margin: auto;
            padding: 20px;
            box-sizing: border-box;
            background-color: #ffffff;
            border: 1px solid #e0e0e0;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
            border-radius: 8px;
            margin-top: 20px;
        }

        .transaction {
            border-bottom: 1px solid #ccc;
            padding: 10px;
            margin-bottom: 10px;
        }

        .transaction-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        .transaction-table th, .transaction-table td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }

        .transaction-table th {
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
    <div class="transaction-container">
        <h1>Transaction History</h1>
        
        <!-- Current Transaction Section -->
        <div id="current-transaction">
            <h2>Current Transaction</h2>
            <div id="current-transaction-details">
                <!-- Details will be dynamically added here -->
            </div>
        </div>
        
        <!-- Last 10 Transactions Table -->
        <div id="last-10-transactions">
            <h2>Last 10 Transactions</h2>
            <table class="transaction-table">
                <thead>
                    <tr>
                        <th>Transaction ID</th>
                        <th>Account Number</th>
                        <th>Type</th>
                        <th>Amount</th>
                        <th>Updated Balance</th>
                        <th>Timestamp</th>
                    </tr>
                </thead>
                <tbody id="last-10-transactions-body">
                    <!-- Rows will be dynamically added here -->
                </tbody>
            </table>
        </div>
        
    </div>
    <script>
        // JavaScript functions to fetch and display transactions
        function fetchTransactions() {
            fetch('TransactionDataServlet.java') // Changed URL here
                .then(response => response.json())
                .then(data => {
                    showCurrentTransaction(data.currentTransaction);
                    showLast10Transactions(data.last10Transactions);
                })
                .catch(error => {
                    console.error('Error fetching transactions:', error);
                    alert('Failed to fetch transactions. Please try again later.');
                });
        }

        function showCurrentTransaction(currentTransaction) {
            const currentTransactionDetails = document.getElementById('current-transaction-details');
            currentTransactionDetails.innerHTML = ''; // Clear previous content

            const currentTransactionElement = document.createElement('div');
            currentTransactionElement.classList.add('transaction');
            currentTransactionElement.innerHTML = `
                <p>Transaction ID: ${currentTransaction.transaction_id}</p>
                <p>Account Number: ${currentTransaction.acc_no}</p>
                <p>Type: ${currentTransaction.type}</p>
                <p>Amount: $${currentTransaction.amount.toFixed(2)}</p>
                <p>Updated Balance: $${currentTransaction.updated_balance.toFixed(2)}</p>
                <p>Timestamp: ${new Date(currentTransaction.timestamp).toLocaleString()}</p>
            `;
            currentTransactionDetails.appendChild(currentTransactionElement);
        }

        function showLast10Transactions(last10Transactions) {
            const last10TransactionsBody = document.getElementById('last-10-transactions-body');
            last10TransactionsBody.innerHTML = ''; // Clear previous content

            last10Transactions.forEach(transaction => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${transaction.transaction_id}</td>
                    <td>${transaction.acc_no}</td>
                    <td>${transaction.type}</td>
                    <td>$${transaction.amount.toFixed(2)}</td>
                    <td>$${transaction.updated_balance.toFixed(2)}</td>
                    <td>${new Date(transaction.timestamp).toLocaleString()}</td>
                `;
                last10TransactionsBody.appendChild(row);
            });
        }

        // Load transactions when the page loads
        window.onload = function() {
            fetchTransactions();
        };
    </script>
</body>
</html>
