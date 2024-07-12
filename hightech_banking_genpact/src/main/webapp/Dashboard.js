const amountInput = document.getElementById('amount');
const receiverAccNoInput = document.getElementById('receiverAccNo');
const balanceDisplay = document.getElementById('balance');
const messageDisplay = document.getElementById('message');

async function handleDeposit() {
    const amount = amountInput.value.trim();
    if (amount && /^\d+$/.test(amount) && parseInt(amount) > 0) {
        try {
            const response = await fetch('transaction?type=deposit&amount=' + amount, {
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
        showMessage("Please enter a valid positive integer amount.", 'error');
    }
}

async function handleWithdraw() {
    const amount = amountInput.value.trim();
    if (amount && /^\d+$/.test(amount) && parseInt(amount) > 0) {
        try {
            const response = await fetch('transaction?type=withdraw&amount=' + amount, {
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
        showMessage("Please enter a valid positive integer amount.", 'error');
    }
}



async function handleTransfer() {
    const amount = amountInput.value.trim();
    const receiverAccNo = receiverAccNoInput.value.trim();
    if (amount && /^\d+$/.test(amount) && parseInt(amount) > 0 && receiverAccNo && /^\d+$/.test(receiverAccNo) && parseInt(receiverAccNo) > 0) {
        try {
            const response = await fetch('transaction?type=transfer&amount=' + amount + '&receiverAccNo=' + receiverAccNo, {
                method: 'POST'
            });
            if (response.ok) {
                const data = await response.json();
                updateBalance(data.balance);
                showMessage('Transfer successful!', 'success');
            } else {
                const errorMessage = await response.text();
                showMessage(`Transfer failed: ${errorMessage}`, 'error');
            }
        } catch (error) {
            console.error('Error making transfer:', error);
            showMessage('Transfer failed. Please try again later.', 'error');
        }
    } else {
        showMessage("Please enter valid amount and receiver account number.", 'error');
    }
}

function generateStatement() {
    // Fetch statement logic here
    window.location.href = 'statement.jsp';
}

function showTransactions(transactions) {
    const statementContainer = document.getElementById('statement-container');
    statementContainer.innerHTML = '';
    
    transactions.forEach(transaction => {
        const transactionElement = document.createElement('div');
        transactionElement.classList.add('transaction');
        transactionElement.innerHTML = `
            <p>Transaction ID: ${transaction.transferId}</p>
            <p>Sender Account Number: ${transaction.senderAccNo}</p>
            <p>Amount: $${transaction.amount.toFixed(2)}</p>
            <p>Timestamp: ${new Date(transaction.timestamp).toLocaleString()}</p>
        `;
        statementContainer.appendChild(transactionElement);
    });
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

window.onload = function() {
    fetchInitialBalance();
};

async function fetchInitialBalance() {
    try {
        const response = await fetch('transaction');
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
