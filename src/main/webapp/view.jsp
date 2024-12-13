
<%
  String nodes = (String) request.getAttribute("nodes");
  String links = (String) request.getAttribute("links");
  Long treeId = Long.valueOf(request.getParameter("treeId"));
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="ru">
<head>
  <script src="https://unpkg.com/gojs"></script>
  <style>
    #myDiagramDiv {
      width: 100%;
      height: 500px;
      border: 1px solid black;
    }
  </style>
  <title>
    Дерево + имя неплохо было бы отображать
  </title>
</head>
<body>

<div id="sample">
  <div id="myDiagramDiv" style="background-color: white; border: solid 1px black; width: 100%; height: 600px"></div>
</div>

<div id="controls" style="margin-top: 10px;">
  <div>
    <strong>Выбранный родственник:</strong> <span id="selectedNodeInfo">Нет</span>
  </div>
  <button id="addParentButton" disabled>Добавить родителя</button>
</div>


<div id="nodeInfoPanel" style="display: none; position: fixed; right: 0; top: 0; width: 350px; height: 100%; background: #f8f9fa; box-shadow: -2px 0 5px rgba(0,0,0,0.1); padding: 20px; z-index: 1000;">
  <button id="closePanelButton" style="float: right;">✖</button>

  <!-- Фото в кружочке перед именем -->
  <div style="display: flex; align-items: center;">
    <img id="nodeViewPhoto" src="images/ava.jpg" alt="Фото" style="width: 100px; height: 100px; border-radius: 50%; margin-right: 10px;">
    <h2 id="nodeName">ФИО</h2>
  </div>

  <!-- Режим просмотра -->
  <div id="nodeViewMode">
    <p>Дата рождения: <span id="nodeBirthDate">Неизвестно</span></p>
    <p>Дата смерти: <span id="nodeDeathDate">Неизвестно</span></p>
    <button id="editNodeButton">Редактировать</button>
    <button id="deleteNodeButton" style="display: none; background-color: red; color: white;">Удалить</button>
    <button id="biographyButton">Биография</button>
    <button id="photoAlbumButton">Фото-альбом</button>
  </div>

  <!-- Режим редактирования -->
  <div id="nodeEditMode" style="display: none;">
    <label>Фамилия: <input type="text" id="editLastName"></label><br>
    <label>Имя: <input type="text" id="editFirstName"></label><br>
    <label>Дата рождения: <input type="date" id="editBirthDate"></label><br>
    <label>Дата смерти: <input type="date" id="editDeathDate"></label><br>

    <button id="upload_widget" class="cloudinary-button">Загрузить фото</button>

    <button id="saveNodeButton" style="background-color: green; color: white;">Сохранить</button>
    <button id="cancelEditButton">Отмена</button>
  </div>


  <script src="https://upload-widget.cloudinary.com/latest/global/all.js" type="text/javascript"></script>


</div>

<div id="parentModal" style="display: none; position: fixed; top: 0; left: 0; width: 100%; height: 100%; background: rgba(0,0,0,0.5); z-index: 1000; justify-content: center; align-items: center;">
  <div style="background: white; padding: 20px; border-radius: 8px; width: 300px; box-shadow: 0 4px 6px rgba(0,0,0,0.1);">
    <h3>Добавить родителя</h3>
    <label>Имя: <input type="text" id="parentFirstName"></label><br><br>
    <label>Фамилия: <input type="text" id="parentLastName"></label><br><br>
    <label>Пол:
      <select id="parentGender">
        <option value="M">м</option>
        <option value="F">ж</option>
      </select>
    </label><br><br>
    <label>Дата рождения: <input type="date" id="parentBirthDate"></label><br><br>
    <label>Дата смерти: <input type="date" id="parentDeathDate"></label><br><br>
    <label>Биография: <textarea id="parentBiography"></textarea></label><br><br>
    <button id="saveParentButton">Сохранить</button>
    <button id="cancelParentButton">Отмена</button>
  </div>
</div>


<script>
  const nodeDataArray = <%= nodes %>;
  const linkDataArray = <%= links %>;
  const treeId = <%= treeId %>;
</script>

<script src="js/treeDiagram.js"></script> <!-- Создаёт диаграмму -->
<script src="js/nodeInfo.js"></script>    <!-- Добавляет функционал нод -->

</body>
</html>
