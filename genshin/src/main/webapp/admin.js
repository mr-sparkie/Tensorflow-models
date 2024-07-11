document.addEventListener('DOMContentLoaded', function() {
    const form = document.querySelector('form');
    const statusMessage = document.getElementById('statusMessage');

    form.addEventListener('submit', function(event) {
        event.preventDefault();

        // Simulate form submission (replace with actual submission code)
        // For demonstration, show success or error message based on input validity
        const isValid = form.checkValidity();
        if (isValid) {
            statusMessage.textContent = 'Client information submitted successfully!';
            statusMessage.classList.remove('status-error');
            statusMessage.classList.add('status-success');
        } else {
            statusMessage.textContent = 'Please fill in all required fields.';
            statusMessage.classList.remove('status-success');
            statusMessage.classList.add('status-error');
        }
    });

    const clearBtn = document.getElementById('clearBtn');
    clearBtn.addEventListener('click', function() {
        form.reset();
        statusMessage.textContent = '';
        statusMessage.className = 'status-message';
    });
});
