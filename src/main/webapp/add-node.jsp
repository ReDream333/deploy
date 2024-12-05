<%--
  Created by IntelliJ IDEA.
  User: konon
  Date: 05.12.2024
  Time: 1:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Добавить участника</title>
</head>
<body>
<h1>${heading}</h1>
<form action="/addInitialMembers" method="post">
    <input type="hidden" name="stage" value="${stage}">
    <label>Имя: <input type="text" name="firstName" required></label><br>
    <label>Фамилия: <input type="text" name="lastName" required></label><br>
    <label>Отчество: <input type="text" name="surname"></label><br>
    <label>Пол:
        <select name="gender" required>
            <option value="M">Мужской</option>
            <option value="F">Женский</option>
        </select>
    </label><br>
    <label>Дата рождения: <input type="date" name="birthDate"></label><br>
    <label>Дата смерти: <input type="date" name="deathDate"></label><br>
    <label>Биография: <textarea name="biography"></textarea></label><br>
    <button type="submit">Далее</button>
</form>
</body>
</html>
