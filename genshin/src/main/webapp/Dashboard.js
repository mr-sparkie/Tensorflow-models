const amountInput = document.getElementById('amount');
const balanceDisplay = document.getElementById('balance');
const messageDisplay = document.getElementById('message');

async function handleDeposit() {
    const amount = amountInput.value.trim();
    if (amount && !isNaN(amount)) {
        try {
            const response = await fetch('deposit?type=deposit&amount=' + amount, {
                method: 'POST'
            });
            if (response.ok) {
                const data = await response.json();
                updateBalance(data.balance);
                showMessage('Deposit successful!', 'success');
            } else {
                const errorMessage = await response.text();
                showMessage(`Deposit failed: ${errorMessage}`, 'error');
            }
        } catch (error) {
            console.error('Error making deposit:', error);
            showMessage('Deposit failed. Please try again later.', 'error');
        }
    } else {
        showMessage("Please enter a valid amount.", 'error');
    }
}

async function handleWithdraw() {
    const amount = amountInput.value.trim();
    if (amount && !isNaN(amount)) {
        try {
            const response = await fetch('deposit?type=withdraw&amount=' + amount, {
                method: 'POST'
            });
            if (response.ok) {
                const data = await response.json();
                updateBalance(data.balance);
                showMessage('Withdrawal successful!', 'success');
            } else {
                const errorMessage = await response.text();
                showMessage(`Withdrawal failed: ${errorMessage}`, 'error');
            }
        } catch (error) {
            console.error('Error making withdrawal:', error);
            showMessage('Withdrawal failed. Please try again later.', 'error');
        }
    } else {
        showMessage("Please enter a valid amount.", 'error');
    }
}

function generateStatement() {
    fetch('/transactions')
        .then(response => response.json())
        .then(data => {
            showTransactions(data);
            showMessage('Statement generated!', 'info');
        })
        .catch(error => {
            console.error('Error fetching transactions:', error);
            showMessage('Failed to generate statement. Please try again later.', 'error');
        });
}

function showTransactions(transactions) {
    const statementContainer = document.getElementById('statement-container');
    statementContainer.innerHTML = ''; // Clear previous content
    
    transactions.forEach(transaction => {
        const transactionElement = document.createElement('div');
        transactionElement.classList.add('transaction');
        transactionElement.innerHTML = `
            <p>Transaction ID: ${transaction.transaction_id}</p>
            <p>Account Number: ${transaction.acc_no}</p>
            <p>Type: ${transaction.type}</p>
            <p>Amount: $${transaction.amount.toFixed(2)}</p>
            <p>Updated Balance: $${transaction.updated_balance.toFixed(2)}</p>
            <p>Timestamp: ${new Date(transaction.timestamp).toLocaleString()}</p>
        `;
        statementContainer.appendChild(transactionElement);
    });
}

function clearInput() {
    amountInput.value = '';
}

function updateBalance(newBalance) {
    balanceDisplay.textContent = `$${newBalance.toFixed(2)}`;
}

function showMessage(message, type) {
    messageDisplay.textContent = message;
    messageDisplay.className = `message ${type}`;
    setTimeout(() => {
        messageDisplay.textContent = '';
        messageDisplay.className = 'message';
    }, 3000);
}

function closeWindow() {
    window.close(); // This will attempt to close the current window
}

window.onload = function() {
    fetchInitialBalance();
};

async function fetchInitialBalance() {
    try {
        const response = await fetch('deposit');
        if (response.ok) {
            const data = await response.json();
            updateBalance(data.balance);
        } else {
            showMessage("Failed to fetch initial balance", 'error');
        }
    } catch (error) {
        console.error("Error fetching initial balance:", error);
        showMessage("Failed to fetch initial balance", 'error');
    }
}
