<%--
  Created by IntelliJ IDEA.
  User: konon
  Date: 22.11.2024
  Time: 4:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">

<head>
    <meta charset="UTF-8">
    <title>Главная</title>
    <link rel="stylesheet" href="css/forHome.css">
</head>

<body>

<header>
    <h1>Создай своё генеалогическое древо</h1>
</header>

<nav>
    <a href="home.jsp">Главная</a>
    <a href="faq.html">FAQ</a>
    <a href="sing.jsp">Вход</a>
</nav>

<div class="container">
    <div class="intro">
        <h1>Добро пожаловать на сайт для создания генеалогического древа!</h1>
        <p>Здесь вы можете сохранить или узнать историю своей семьи. <br/>
            Кликнув на кнопку ниже, вы сможете пройти регистрацию. Если у вас уже есть аккаунт, перейдите на вкладку "Вход"</p>
        <button onclick="location.href='/registration.jsp'">Начать создание древа</button>
    </div>
</div>

<footer>
    <p>&copy; ReDream 2024 Генеалогическое древо </p>
    <img src="images/logo.png" alt="logo" height = 50 width = 50>
</footer>

</body>

</html>

