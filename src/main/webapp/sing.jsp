<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Вход</title>
    <link rel="stylesheet" href="css/forHome.css">
    <link rel="stylesheet" href="css/regSing.css">

</head>
<body>

<div class="registration-container">
    <h2>Вход</h2>
    <form action="${pageContext.request.contextPath}/SingServlet" method="post">
        <div class="form-group">

        <label for="login">Логин</label>
        <input type="text" id="login" name="login" required>
        <label for="password">Пароль</label>
        <input type="password" id="password" name="password" required>
        </div>

        <button type="submit" class="submit-btn">Войти</button>

    </form>

    <!-- Ссылка на страницу регистрации -->
    <a href="registration.jsp" class="link">Забыли пароль? Зарегистрируйтесь</a>
</div>

</body>
</html>
