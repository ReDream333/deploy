document.addEventListener('DOMContentLoaded', function () {
  document.getElementById('button').addEventListener('click', function () {
    const newName = document.getElementById('name').value.trim();
    const messageElement = document.getElementById('message');

    if (newName === '') {
      messageElement.style.color = 'red';
      messageElement.textContent = 'Имя не может быть пустым.';
      return;
    }

    fetch('/change', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      },
      body: new URLSearchParams({ newName })
    })
        .then(response => response.text())
        .then(data => {
          if (data === 'success') {
            messageElement.style.color = 'green';
            messageElement.textContent = 'Имя успешно изменено.';
            document.getElementById('currentName').textContent = newName; // Обновляем отображаемое имя
          } else {
            messageElement.style.color = 'red';
            messageElement.textContent = data; // Показываем сообщение об ошибке
          }
        })
        .catch(error => {
          messageElement.style.color = 'red';
          messageElement.textContent = 'Ошибка при обработке запроса.';
          console.error('Ошибка:', error);
        });
  });
});
