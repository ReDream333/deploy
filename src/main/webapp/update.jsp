<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Изменение данных пользователя</title>
</head>

<body>
<div>
    <h1>Что вы желаете поменять</h1>
    <form>
        <p>Имя: <span id="currentName">${user.username()}</span></p>
        <label for="name">Новое имя:</label>
        <input type="text" id="name" name="newName" placeholder="Введите новое имя">
        <button type="button" id="button">Изменить имя</button>
    </form>
    <p id="message" style="color: red;"></p>

    <script>
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
    </script>
</div>
</body>
</html>
