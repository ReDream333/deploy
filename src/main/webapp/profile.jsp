    <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Профиль пользователя</title>
        <link rel="stylesheet" href="css/forHome.css">

    </head>
    <style>


        .notifications-button {
            position: absolute;
            top: 20px;
            right: 20px;
            cursor: pointer;
            background-color: #6FCF97;
        }

        /* Окно уведомлений */
        .notifications {
            background-color: #6FCF97;
            padding: 15px;
            border-radius: 15px;
            margin-top: 20px;

            display: none; /* Скрыто по умолчанию */
        }

        /* Отображение уведомлений, если чекбокс включен */
        #toggle-notifications:checked + .notifications {
            display: block;
        }

        .notifications h3 {
            font-size: 1.2em;
            margin-bottom: 10px;
        }

        .notification-item {
            font-size: 0.9em;
            color: #333;
            margin-bottom: 8px;
        }


        /* Контейнер страницы пользователя */
        .user-container {
            display: flex;
            justify-content: space-between;
            padding: 20px;
            margin: 20px auto;
            max-width: 1000px;
            flex-grow: 1;
        }

        /* Блок с информацией о пользователе */
        .user-info-section {
            display: flex;
            align-items: flex-start;
            width: 60%;
            padding: 20px;
            background-color: #ffffff;
            border-radius: 8px;
            box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.1);
        }

        .user-photo {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            margin-right: 20px;
        }

        .user-details {
            flex: 1;
        }

        .user-details h2 {
            font-size: 1.8em;
            margin: 0;
            color: #09f; /* Основной цвет */
        }

        .user-description {
            font-size: 1em;
            margin: 10px 0;
            color: #555;
        }

        .user-stats {
            font-size: 0.9em;
            color: #777;
            margin: 10px 0;
        }

        .edit-profile-button {
            padding: 10px 20px;
            background-color: #007bff;
            color: #fff;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        .edit-profile-button:hover {
            background-color: #0056b3;
        }

        /* Кнопки действий */

        .button {
            width: 180px;
            padding: 10px;
            font-size: 1em;
            color: #fff;
            background-color: #6c6;
            border: none;
            border-radius: 15px;
            cursor: pointer;
            text-align: center;
            transition: background-color 0.3s ease;
        }

        .button:hover {
            background-color: #09f;
        }


    </style>
    <body>

    <header>

    </header>

    <nav>
        <a href="profil.html">Профиль</a>
        <a href="faq.html">FAQ</a>
        <a href="#user-trees">Деревья пользователей</a>
    </nav>


    <button class="notifications-button" onclick="toggleNotifications()">🔔</button>

    <div class="notifications" id="notifications">
        <div class="notification-item">Ваше дерево было обновлено.</div>
        <div class="notification-item">Новый комментарий к вашему древу.</div>
        <div class="notification-item">У вас новый запрос на добавление в дерево.</div>
    </div>

    <div class="user-container">
        <div class="user-info-section">
            <img src="${user.photo() != null ? user.photo() : 'https://res.cloudinary.com/gtree/image/upload/v1732359437/samples/animals/cat.jpg'}" alt="Фото пользователя" class="user-photo">
            <div class="user-details">
                <h2>${user.username()}</h2>
                <p>Аккаунт создан: ${user.createdAt()}</p>
    <%--            <p class="user-description">Описание пользователя самого себя при желании</p>--%>
    <%--            <p class="user-stats">--%>
    <%--                Количество деревьев: 3<br>--%>
    <%--                Скрытых деревьев: 1<br>--%>
    <%--                Публичных деревьев: 2<br>--%>
    <%--                Дата регистрации: 01.01.2023--%>
    <%--            </p>--%>
                <button class="edit-profile-button" onclick="location.href='/change'">Изменить профиль</button>
                <form action="${pageContext.request.contextPath}/uploadProfilePhoto" method="post" enctype="multipart/form-data">
                    <label for="upload_widget"> Изменить фото: </label>
                    <button id="upload_widget" class="cloudinary-button">Выбрать фото. Облако</button>
                </form>
            </div>
        </div>
        <button class="button">Просмотреть свои деревья
            <img src="images/search-.png" alt="search" height = 160 width = 160></button>

        <form action="/createTree" method="Get">
        <button type="submit" class="button">Создать новое дерево
            <img src="images/create-.png" alt="create" height = 160 width = 160></button>
        </form>
    </div>


    <script src="https://upload-widget.cloudinary.com/latest/global/all.js" type="text/javascript"></script>

    <script type="text/javascript">
        var myWidget = cloudinary.createUploadWidget({
                cloudName: 'gtree',
                uploadPreset: 'usersphoto'}, (error, result) => {
                if (!error && result && result.event === "success") {
                    console.log('Done! Here is the image info: ', result.info);
                    const uploadedImageUrl = result.info.secure_url;
                    fetch('/saveProfilePhoto', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            imageUrl: uploadedImageUrl
                        })
                    })
                        .then(response => response.json())
                        .then(data => {
                            console.log('Response from server:', data);
                            alert('Фото профиля успешно обновлено!');

                            updateProfilePhoto(uploadedImageUrl);


                        })
                        .catch(err => {
                            console.error('Ошибка сохранения на сервере:', err);
                            alert('Ошибка при обновлении фото профиля.');
                        });
                }
            }
        )

        // Функция для обновления фото пользователя
        function updateProfilePhoto(newImageUrl) {
            const userPhoto = document.querySelector('.user-photo');
            if (userPhoto) {
                userPhoto.src = newImageUrl; // Обновляем атрибут src
            }
        }

        document.getElementById("upload_widget").addEventListener("click", function(){
            myWidget.open();
        }, false);
    </script>


    <script>
        // Функция для переключения видимости окна уведомлений
        function toggleNotifications() {
            const notifications = document.getElementById("notifications");
            if (notifications.style.display === "none" || notifications.style.display === "") {
                notifications.style.display = "block";
            } else {
                notifications.style.display = "none";
            }
        }
    </script>

    <footer>
        <p>&copy; ReDream 2024 Генеалогическое древо </p>
        <img src="images/logo.png" alt="logo" height = 50 width = 50>
    </footer>

    </body>
    </html>
