<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Изменение данных пользователя</title>
    <script src="js/profileSettings.js"></script>
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


</div>
</body>
</html>
