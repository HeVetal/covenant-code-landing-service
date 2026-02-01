document.addEventListener('DOMContentLoaded', function() {
    // Автофокус на первом поле
    const nameInput = document.getElementById('name');
    if (nameInput) {
        nameInput.focus();
    }

    // Маска для телефона
    const phoneInput = document.getElementById('phone');
    if (phoneInput) {
        phoneInput.addEventListener('input', function(e) {
            let value = e.target.value.replace(/\D/g, '');
            if (value.length > 0) {
                if (!value.startsWith('7') && !value.startsWith('8')) {
                    value = '7' + value;
                }
                let formatted = '+7 (';
                if (value.length > 1) {
                    formatted += value.substring(1, 4);
                }
                if (value.length >= 4) {
                    formatted += ') ' + value.substring(4, 7);
                }
                if (value.length >= 7) {
                    formatted += '-' + value.substring(7, 9);
                }
                if (value.length >= 9) {
                    formatted += '-' + value.substring(9, 11);
                }
                e.target.value = formatted;
            }
        });
    }

    // Проверяем наличие ошибки из контроллера (flash attribute)
    const errorMessage = document.querySelector('.error-message');
    if (errorMessage && errorMessage.textContent.trim()) {
        // Показываем модальное окно ошибки
        const errorModal = document.getElementById('errorModal');
        const errorSecondsSpan = document.getElementById('errorSeconds');

        errorModal.style.display = 'flex';

        // Обратный отсчет для ошибки
        let seconds = 5;
        const errorCountdown = setInterval(function() {
            seconds--;
            errorSecondsSpan.textContent = seconds;

            if (seconds <= 0) {
                clearInterval(errorCountdown);
                window.location.href = '/v1/layout';
            }
        }, 1000);
    }

    // Проверяем параметр success из URL
    const urlParams = new URLSearchParams(window.location.search);
    const successParam = urlParams.get('success');

    if (successParam === 'true') {
        const successModal = document.getElementById('successModal');
        const successSecondsSpan = document.getElementById('successSeconds');

        successModal.style.display = 'flex';

        // Обратный отсчет для успеха
        let seconds = 5;
        const successCountdown = setInterval(function() {
            seconds--;
            successSecondsSpan.textContent = seconds;

            if (seconds <= 0) {
                clearInterval(successCountdown);
                window.location.href = '/v1/layout';
            }
        }, 1000);
    }
});