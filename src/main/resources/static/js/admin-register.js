document.addEventListener('DOMContentLoaded', function() {
    const password = document.getElementById('password');
    const confirmPassword = document.getElementById('confirmPassword');

    function checkPasswords() {
        if (password.value !== confirmPassword.value) {
            confirmPassword.style.borderColor = '#e53e3e';
        } else {
            confirmPassword.style.borderColor = '#48bb78';
        }
    }

    if (password && confirmPassword) {
        password.addEventListener('input', checkPasswords);
        confirmPassword.addEventListener('input', checkPasswords);
    }

    const usernameInput = document.getElementById('username');
    if (usernameInput) {
        usernameInput.focus();
    }
});