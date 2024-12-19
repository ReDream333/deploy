<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>Публичные деревья</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/userTrees.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">

</head>

<body>

<button class="back-button" onclick="history.back()">Назад</button>

<div class="user-container">
  <h2>Публичные Деревья</h2>
  <div class="tree-buttons">
    <jsp:useBean id="publicTrees" scope="request" type="java.util.List"/>
    <c:if test="${empty publicTrees}">
      <p class="no-trees-message">Пока публичных деревьев нет, станьте первым!</p>
    </c:if>

    <c:forEach var="tree" items="${publicTrees}">
      <button class="tree-button"
              onclick="location.href='${pageContext.request.contextPath}/viewTree?treeId=${tree.id()}'">
          ${tree.name()} <i class="fa-solid fa-tree"></i>
      </button>
    </c:forEach>
  </div>
</div>



</body>
</html>
