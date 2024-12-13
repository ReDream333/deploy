

document.addEventListener("DOMContentLoaded", () => {
    const photoAlbum = document.getElementById("photoAlbum");
    const lightbox = document.getElementById("lightbox");
    const lightboxImage = document.getElementById("lightboxImage");
    const closeLightbox = document.getElementById("closeLightbox");
    const uploadPhotoButton = document.getElementById("uploadPhotoButton");


    // Инициализация виджета Cloudinary
    const cloudinaryWidget = cloudinary.createUploadWidget({
        cloudName: 'gtree', // Ваш Cloudinary cloud name
        uploadPreset: 'usersphoto' // Ваш пресет
    }, (error, result) => {
        if (!error && result && result.event === "success") {
            console.log("Uploaded Image Info:", result.info);

            // Добавление нового фото в галерею
            addPhotoToGallery(result.info.secure_url);
        }
    });

    // Обработчик для кнопки загрузки фото
    uploadPhotoButton.addEventListener("click", () => {
        cloudinaryWidget.open();
    });
    // Добавление нового фото в массив и отображение
    function addPhotoToGallery(photoUrl) {
        const newPhoto = {
            nodeId: nodeId,
            photoUrl: photoUrl, //ВНИМАНИЕ тут было src
            description: "Новое фото" // Стандартное описание
        };

        // Имитация сохранения фото на сервер
        fetch("/album", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(newPhoto)
        })
            .then(response => {
                if (!response.ok) throw new Error("Ошибка при добавлении фото");
                return response.json();
            })
            .then((newPhotoIs) => {
                console.log(newPhotoIs)
                photos.push(newPhotoIs); // Добавляем фото в массив
                console.log(photos)
                renderPhotoAlbum(); // Обновляем галерею
                alert("Фото успешно добавлено!");
            })
            .catch(error => console.error("Ошибка:", error));
    }

    // Функция для отрисовки фотографий
    function renderPhotoAlbum() {
        photoAlbum.innerHTML = ""; // Очищаем альбом
        photos.forEach((photo) => {
            const photoCard = document.createElement("div");
            photoCard.className = "photo-item";
            photoCard.setAttribute("data-photo-id", photo.id);

            console.log(photo);

            photoCard.innerHTML = `
                <img src="${photo.photoUrl}" alt="Фото" class="photo-thumb">
                <div class="photo-description">
                    <p class="description-text">${photo.description}</p>
                    <button class="edit-desc-btn">✏️ Изменить</button>
                    <button class="delete-photo-btn">🗑️ Удалить</button>
                </div>
            `;

            // Добавляем обработчики событий
            photoCard.querySelector(".photo-thumb").addEventListener("click", () => openLightbox(photo.photoUrl));
            photoCard.querySelector(".delete-photo-btn").addEventListener("click", () => deletePhoto(photo.id));
            photoCard.querySelector(".edit-desc-btn").addEventListener("click", () => editDescription(photo.id));

            photoAlbum.appendChild(photoCard);
        });
    }

    // Открытие изображения на весь экран
    function openLightbox(src) {
        lightboxImage.src = src;
        lightbox.style.display = "flex";
    }

    // Закрытие полноразмерного изображения
    closeLightbox.addEventListener("click", () => {
        lightbox.style.display = "none";
    });

    // Удаление фото
    function deletePhoto(photoId) {
        if (!confirm("Вы уверены, что хотите удалить это фото?")) return;

        // Имитация запроса к серверу
        fetch(`/album?id=${photoId}`, { method: "DELETE" })
            .then((response) => {
                if (!response.ok) throw new Error("Ошибка удаления фото");
                photos = photos.filter((photo) => photo.id !== photoId); // Удаляем фото из массива
                renderPhotoAlbum(); // Перерисовываем альбом
                alert("Фото удалено!");
            })
            .catch((error) => console.error("Ошибка:", error));
    }

    // Изменение описания фото
    function editDescription(photoId) {
        const photo = photos.find((p) => p.id === photoId);
        const newDescription = prompt("Введите новое описание:", photo.description);

        if (newDescription !== null) {
            // Имитация запроса к серверу
            fetch(`/album`, {
                method: "PUT",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({ id: photoId, description: newDescription })
            })
                .then((response) => {
                    if (!response.ok) throw new Error("Ошибка обновления описания");
                    photo.description = newDescription; // Обновляем описание в массиве
                    renderPhotoAlbum(); // Перерисовываем альбом
                    alert("Описание обновлено!");
                })
                .catch((error) => console.error("Ошибка:", error));
        }
    }

    // Инициализация альбома
    renderPhotoAlbum();
});