
//создание диаграммы
function init() {
    window.myDiagram = new go.Diagram('myDiagramDiv', {
        allowCopy: false,
        layout: new go.TreeLayout({ angle: 90, nodeSpacing: 5 })
    }   );

    var bluegrad = new go.Brush('Linear', { 0: 'rgb(60, 204, 254)', 1: 'rgb(70, 172, 254)' });
    var pinkgrad = new go.Brush('Linear', { 0: 'rgb(255, 192, 203)', 1: 'rgb(255, 142, 203)' });

    // Создание Легенды, ее размещение
    myDiagram.add(
        new go.Part('Table', {
            layerName: 'ViewportBackground',
            alignment: new go.Spot(0, 0, 20, 20)
        }).add(
            new go.TextBlock('Легенда', { row: 0, font: 'bold 10pt Helvetica, Arial, sans-serif' }), // end row 0
            new go.Panel('Horizontal', { row: 1, alignment: go.Spot.Left })
                .add(
                    new go.Shape('Rectangle', { desiredSize: new go.Size(30, 30), fill: bluegrad, margin: 5 }),
                    new go.TextBlock('Мужчины', { font: 'bold 8pt Helvetica, bold Arial, sans-serif' })
                ), // end row 1
            new go.Panel('Horizontal', { row: 2, alignment: go.Spot.Left })
                .add(
                    new go.Shape('Rectangle', { desiredSize: new go.Size(30, 30), fill: pinkgrad, margin: 5 }),
                    new go.TextBlock('Женщины', { font: 'bold 8pt Helvetica, bold Arial, sans-serif' })
                ) // end row 2
        )
    );

    //получить текст подсказки
    function tooltipTextConverter(person) {
        var str = '';
        str += 'Биография: ' + person.biography;
        return str;
    }


    //определение подсказки при наведении
    var tooltiptemplate = go.GraphObject.build('ToolTip', {
        'Border.fill':'whitesmoke',
        'Border.stroke': 'black'
    }).add(
        new go.TextBlock({
            font: 'bold 8pt Helvetica, bold Arial, sans-serif',
            wrap: go.Wrap.Fit,
            margin: 5
        }).bind('text', '', tooltipTextConverter));


    //Определить цвет ноды
    function genderBrushConverter(gender) {
        if (gender === 'M') return bluegrad;
        if (gender === 'F') return pinkgrad;
        return 'orange';
    }




    //Определение шаблона(вида) Node - родственников
    myDiagram.nodeTemplate = new go.Node('Auto', {
        deletable: false,
        selectable: true,
        toolTip: tooltiptemplate
    })
        .bind('text', 'fullName')
        .add(
            new go.Shape('Rectangle', {
                fill: 'orange',
                stroke: 'black',
                stretch: go.Stretch.Fill,
                alignment: go.Spot.Center
            })
                .bind('fill', 'gender', genderBrushConverter),
            new go.Panel('Vertical')
                .add(
                    new go.TextBlock({
                        font: '18px Poppins',
                        alignment: go.Spot.Center,
                        margin: 6
                    })
                        .bind('text', 'fullName'),
                    // new go.TextBlock().bind('text', 'birthday'),
                    new go.TextBlock({
                        font: 'bold 13px Poppins',
                    }) .bind("text", (data) => {
                        const birth =
                            (data.birthday!=null && data.birthday !== "Неизвестно" && data.birthday!=="null") ?
                            data.birthday.substring(0, 4) :
                            "Неизвестно";
                        const death =
                            data.death!=null && data.death !== "null" ?
                            data.death.substring(0, 4) : "";

                        return death ? `${birth} - ${death}` : birth;
                    })
                )
        );


    //Определение шаблона(вида) Link - связей
    myDiagram.linkTemplate = new go.Link({ // the whole link panel
        routing: go.Routing.Orthogonal,
        corner: 5,
        selectable: false
    }).add(new go.Shape())


    //создание графа - да да, не дерева - ГРАФА
    console.log(nodeDataArray)
    myDiagram.model = new go.GraphLinksModel(nodeDataArray, linkDataArray);

    //выбираем Ноду, которой нужно добавить родителя
    myDiagram.addDiagramListener("ObjectSingleClicked", function (e) {
        const part = e.subject.part;
        if (!(part instanceof go.Node)) return;

        const nodeData = part.data;
        const selectedNodeInfo = document.getElementById("selectedNodeInfo");
        const addParentButton = document.getElementById("addParentButton");

        selectedNodeInfo.textContent = nodeData.fullName;
        addParentButton.disabled = false;

        addParentButton.onclick = function () {
            addParent(nodeData);
        };
    });



    /*
        //функция для добавления родителя

    */

    // Модальное окно и кнопки
    const parentModal = document.getElementById("parentModal");
    const saveParentButton = document.getElementById("saveParentButton");
    const cancelParentButton = document.getElementById("cancelParentButton");

    // Функция открытия модального окна
    function openParentModal(childNode) {
        parentModal.style.display = "flex"; // Показываем окно

        saveParentButton.onclick = function () {
            const parentFirstName = document.getElementById("parentFirstName").value.trim();
            const parentLastName = document.getElementById("parentLastName").value.trim();
            const parentGender = document.getElementById("parentGender").value;
            const parentBirthDate = document.getElementById("parentBirthDate").value;
            const parentDeathDate = document.getElementById("parentDeathDate").value;
            const parentBiography = document.getElementById("parentBiography").value.trim();

            // Проверка на обязательные поля
            if (!parentFirstName || !parentLastName || !parentGender) {
                alert("Имя, фамилия и пол обязательны для заполнения.");
                return;
            }

            // Формируем данные для отправки
            const requestData = {
                treeId: treeId, // Глобальный treeId
                firstName: parentFirstName,
                lastName: parentLastName,
                gender: parentGender,
                birthDate: parentBirthDate || null,
                deathDate: parentDeathDate || null,
                biography: parentBiography || null,
                childId: childNode.key
            };

            console.log(requestData);

            // Отправляем данные на сервер
            fetch("/addFamilyMember", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(requestData)
            })
                .then((response) => {
                    if (!response.ok) throw new Error("Ошибка при добавлении родителя");
                    return response.json();
                })
                .then((newParent) => {
                    console.log("Новый родитель от сервера:", newParent);
                    // Добавляем нового родителя в диаграмму
                    myDiagram.model.addNodeData({
                        key: newParent.id,
                        fullName: newParent.firstName +" "+ newParent.lastName,
                        gender: newParent.gender,
                        birthday: newParent.birthDate || "Неизвестно",
                        death: newParent.deathDate,
                        biography: newParent.biography
                    });

                    // Добавляем связь между родителем и ребенком
                    myDiagram.model.addLinkData({
                        from: childNode.key,
                        to: newParent.id
                    });

                    alert("Родитель " + newParent.firstName +" "+ newParent.lastName + " добавлен успешно.");
                })
                .catch((error) => {
                    console.error("Ошибка:", error);
                    alert("Не удалось добавить родителя.");
                })
                .finally(() => {
                    parentModal.style.display = "none"; // Закрываем окно
                    clearModalFields();
                });
        };

        cancelParentButton.onclick = function () {
            parentModal.style.display = "none";
            clearModalFields();
        };
    }

    // Функция очистки полей модального окна
    function clearModalFields() {
        document.getElementById("parentFirstName").value = "";
        document.getElementById("parentLastName").value = "";
        document.getElementById("parentGender").value = "M";
        document.getElementById("parentBirthDate").value = "";
        document.getElementById("parentDeathDate").value = "";
        document.getElementById("parentBiography").value = "";
    }

    // Привязка функции открытия окна к кнопке "Добавить родителя"
    myDiagram.addDiagramListener("ObjectSingleClicked", function (e) {
        const part = e.subject.part;
        if (!(part instanceof go.Node)) return;

        const nodeData = part.data;
        const addParentButton = document.getElementById("addParentButton");

        addParentButton.disabled = false;
        addParentButton.onclick = function () {
            openParentModal(nodeData);
        };
    });

    // Вызов функции установки обработчика событий
    if (typeof setupNodeClickListener === "function") {
        setupNodeClickListener(myDiagram);
    } else {
        console.error("Функция setupNodeClickListener не найдена.");
    }





}
window.addEventListener('DOMContentLoaded', init);



