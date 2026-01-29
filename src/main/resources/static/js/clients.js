/*<![CDATA[*/
// Переменные для хранения данных об удалении
let clientToDeleteId = null;
let clientToDeleteName = null;

// CSRF токены (получаем из Thymeleaf)
const csrfToken = /*[[${_csrf.token}]]*/ '';
const csrfHeader = /*[[${_csrf.headerName}]]*/ '';

// Показать модальное окно подтверждения
function showConfirmDelete(clientId, clientName) {
    clientToDeleteId = clientId;
    clientToDeleteName = clientName;

    document.getElementById('clientName').textContent = clientName;
    document.getElementById('confirmModal').style.display = 'flex';
}

// Скрыть модальное окно
function hideConfirmDelete() {
    document.getElementById('confirmModal').style.display = 'none';
    clientToDeleteId = null;
    clientToDeleteName = null;
}

// Показать индикатор загрузки
function showLoading() {
    document.getElementById('loading').style.display = 'flex';
}

// Скрыть индикатор загрузки
function hideLoading() {
    document.getElementById('loading').style.display = 'none';
}

// Отправить запрос на удаление через AJAX
function submitDelete() {
    if (!clientToDeleteId) {
        hideConfirmDelete();
        return;
    }

    showLoading();
    hideConfirmDelete();

    // Отправляем AJAX запрос с CSRF токеном
    fetch(`/admin/clients/${clientToDeleteId}/delete`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            [csrfHeader]: csrfToken
        }
    })
        .then(response => {
            hideLoading();
            if (response.ok) {
                // Перенаправляем на ту же страницу с сообщением об успехе
                window.location.href = '/admin/clients?success=deleted';
            } else if (response.status === 404) {
                window.location.href = '/admin/clients?error=notfound';
            } else {
                throw new Error('Ошибка удаления: ' + response.status);
            }
        })
        .catch(error => {
            hideLoading();
            console.error('Error:', error);
            alert('Не удалось удалить заявку. Попробуйте еще раз.');
        });
}

// Закрыть модальное окно при клике вне его
document.getElementById('confirmModal').addEventListener('click', function(e) {
    if (e.target === this) {
        hideConfirmDelete();
    }
});

// Закрыть модальное окно при нажатии Escape
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        hideConfirmDelete();
    }
});

// Инициализация при загрузке
document.addEventListener('DOMContentLoaded', function() {
    console.log('Admin panel loaded successfully');

    // Проверяем, что CSRF токен получен
    if (!csrfToken) {
        console.warn('CSRF token is missing!');
    }
});
/*]]>*/