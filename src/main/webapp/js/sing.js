document.getElementById("login-form")
    .addEventListener("submit", function (e)
    {
        e.preventDefault(); // Предотвращаем стандартную отправку формы

        const login = document.getElementById("login").value;
        const password = document.getElementById("password").value;
        const errorMessage = document.getElementById("error-message");

        const data = {
            login: login,
            password: password
        };

        fetch(contextPath + '/sing', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json' // Устанавливаем тип содержимого
            },
            body: JSON.stringify(data) // Преобразуем данные в JSON-строку
        })
            .then(response => response.json())
            .then(data => {
                if (data.status === "success") {
                    window.location.href = data.redirectUrl; // Перенаправляем пользователя
                } else {
                    errorMessage.textContent = data.message; // Показываем сообщение об ошибке
                }
            })
            .catch(error => {
                console.error("Ошибка:", error);
                errorMessage.textContent = "Произошла ошибка. Попробуйте снова.";
            });
});