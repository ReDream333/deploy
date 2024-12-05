<%--
  Created by IntelliJ IDEA.
  User: konon
  Date: 05.12.2024
  Time: 0:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Добавить начальных членов семьи</title>
    <link rel="stylesheet" href="styles.css">
</head>
<body>
<h1>Добавить начальных членов семьи</h1>
<form action="/addInitialMembers" method="post">
    <div>
        <label for="selfName">Ваше имя:</label>
        <input type="text" id="selfName" name="selfName" required>
    </div>
    <div>
        <label for="parent1Name">Имя первого родителя:</label>
        <input type="text" id="parent1Name" name="parent1Name" required>
    </div>
    <div>
        <label for="parent2Name">Имя второго родителя:</label>
        <input type="text" id="parent2Name" name="parent2Name">
    </div>
    <button type="submit">Сохранить</button>
</form>
</body>
</html>
