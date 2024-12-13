<%
    String nodeName = (String) request.getAttribute("nodeName");
    String photos = (String) request.getAttribute("photos");
    String nodeId = request.getParameter("nodeId");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Фотоальбом</title>
    <link rel="stylesheet" href="css/photoAlbum.css"> <!-- Подключим CSS -->
    <script src="https://upload-widget.cloudinary.com/latest/global/all.js" type="text/javascript"></script>
</head>
<body>
<!-- Заголовок и кнопка возврата -->
<header>
    <h1 id="albumTitle">Фотоальбом</h1>
    <span id="familyMemberName">
        <%= nodeName %>
    </span>
    <button id="backButton" onclick="window.history.back()">
        Назад
    </button>
</header>

<!-- Кнопка добавления фото -->
<div style="text-align: center; margin-bottom: 20px;">
    <button id="uploadPhotoButton" class="cloudinary-button">Добавить фото</button>
</div>

<!-- Контейнер для фотоальбома -->
<div id="photoAlbum" style="display: flex; flex-wrap: wrap; gap: 20px; justify-content: center;">
    <!-- Сюда будут добавляться карточки фото через JS -->
</div>

<!-- Модальное окно для полноразмерного изображения -->
<div id="lightbox" class="lightbox" style="display: none;">
    <span id="closeLightbox" class="close">&times;</span>
    <img id="lightboxImage" class="lightbox-content" src="" alt="Full Image">
</div>

<script>
    let photos = <%= photos %>;
    const nodeId = <%= nodeId %>;
</script>

<script src="js/photoAlbum.js"></script> <!-- Подключаем JS -->
</body>
</html>
