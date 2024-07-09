let balance = 0.00;

function deposit() {
    let amount = prompt("Enter deposit amount:");
    if (amount && !isNaN(amount)) {
        balance += parseFloat(amount);
        updateBalance();
    } else {
        alert("Please enter a valid number.");
    }
}

function withdraw() {
    let amount = prompt("Enter withdraw amount:");
    if (amount && !isNaN(amount)) {
        if (parseFloat(amount) <= balance) {
            balance -= parseFloat(amount);
            updateBalance();
        } else {
            alert("Insufficient balance.");
        }
    } else {
        alert("Please enter a valid number.");
    }
}

function updateBalance() {
    document.getElementById('balance').innerText = `$${balance.toFixed(2)}`;
}
