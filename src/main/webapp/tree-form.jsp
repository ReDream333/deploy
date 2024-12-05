<%--
  Created by IntelliJ IDEA.
  User: konon
  Date: 04.12.2024
  Time: 10:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Создание дерева</title>
</head>
<body>

<h1>Создание нового дерева</h1>
<form action="/createTree" method="POST">
    <label for="treeName">Название дерева:</label>
    <input type="text" id="treeName" name="treeName" required>

    <label for="isPrivate">Приватное дерево?</label>
    <select id="isPrivate" name="isPrivate">
        <option value="true">Да</option>
        <option value="false">Нет</option>
    </select>

    <button type="submit">Создать</button>
</form>
</body>

</html>
