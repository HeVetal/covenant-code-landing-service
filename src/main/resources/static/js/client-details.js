/*<![CDATA[*/
// Показать модальное окно удаления
function showDeleteModal() {
    document.getElementById('deleteModal').style.display = 'flex';
}

// Скрыть модальное окно удаления
function hideDeleteModal() {
    document.getElementById('deleteModal').style.display = 'none';
}

// Закрыть модальное окно при клике вне его
document.getElementById('deleteModal').addEventListener('click', function(e) {
    if (e.target === this) {
        hideDeleteModal();
    }
});

// Закрыть модальное окно при нажатии Escape
document.addEventListener('keydown', function(e) {
    if (e.key === 'Escape') {
        hideDeleteModal();
    }
});

// Показать индикатор загрузки при отправке форм
document.addEventListener('DOMContentLoaded', function() {
    const forms = document.querySelectorAll('form');
    forms.forEach(form => {
        form.addEventListener('submit', function() {
            document.getElementById('loading').style.display = 'flex';
        });
    });

    // Инициализация
    console.log('Client details loaded:', {
        id: /*[[${client.id}]]*/ '',
        name: /*[[${client.name}]]*/ '',
        status: /*[[${client.status}]]*/ ''
    });
});
/*]]>*/