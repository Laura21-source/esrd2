    // Количество элементов в массиве
    function countElem (array) {
        return array.length;
    }

    // Функция получения текстового поля
    function createInput (element, type, id, name, title, short, iconName, value, field, up, idField) {
        var idVal = "";
        if(idField) {
            idVal = ' data-id="' + idField + '"';
        }
        var inputVal = '';
        if(value) {
            inputVal = ' value="'+ value + '"';
        }
        var col = '<div class="col-md-12">';
        var colShort = '';
        if (short == 1) {
            col = '<div class="col-md-6">';
            colShort = '<div class="col-md-6">&nbsp;</div>';
        }
        var upClass = '';
        if (up == 1) {
            upClass = ' upElem';
        }
        $(element).append('<div class="row ml-1 mb-3" id="' + id + '">' + col + '<div class="row">' + '<div class="col-md-4 text-left">' + '<span for="' + name + '" class="text-muted">' + iconName + '</span>' + '</div>' + '<div class="col-md-8">' + '<input title="' + title + '" type="' + type + '" id="' + name + '" name="' + name + '" data-field="' + field + '" ' + idVal + ' class="white form-control' + upClass + '"' + inputVal + '>' + '</div>' + '</div>' + '</div>' + colShort + '</div>');
    }

    // Функция получения полей по выбору SELECT Запрос:Атрибут_поля:Название_поля:Значение_поля:Значение_выбранного_поля:ID_поля
    function createOptions (url, field, name, id, select) {
        $.getJSON (url, function(data) {
            for (var i in data) {
                var selectedField = '';
                // Получаем отмеченные поля если есть необходимость
                if (select) {
                    if(select === data[i][id]) {
                        selectedField = ' selected="selected"';
                    }
                }
                $(field).append('<option value="' + data[i][id] + '"' + selectedField + '>' +  data[i][name] + '</option>');
            }
        });
    }

    // Получение правильного и обратного формата даты
    function formatDate (date, reverse) {
        var date = new Date(date);
        var day = date.getDate();
        var month = date.getMonth()+1;
        if (month < 10) {
            month = '0' + month;
        }
        var year = date.getFullYear();
        if (reverse == 1) {
            return year + '-' + month + '-' + day;
        } else {
            return day + '-' + month + '-' + year;
        }

    }

    // Получение правильного и обратного формата времени
    function formatTime (date) {
        var date = new Date(date);
        var hours = date.getHours();
        var minutes = date.getMinutes();
        if (minutes < 10) {
            minutes = '0' + minutes;
        }
        return hours + ':' + minutes;
    }

    // Получение id документа из адресной строки
    function getId () {
        return new URL(window.location.href).searchParams.get("id");
    }

    // Список полей по виду документа Путь:номер_документа:короткие_поля
    function getFieldsDocument (url, id, short) {
        $.getJSON (url, function(data) {
            // Массив для полей в одной группе
            var groupFieldsArray = [];
            // Проверяем регистрация или редактирование документа
            var rowChild = data;
            // Переменная даты
            var valueDate = "";
            // Переменная поля
            var idField = "";
            if(id > 0) {
                rowChild = data.childFields;
            }
            for (var i in rowChild) {
                var row = rowChild[i];
                if (row.field.fieldType === "DATE") {
                    if (id > 0) {
                        idField = row.field.id;
                        if(row.field.valueDate !== "") {
                            valueDate = formatDate(row.field.valueDate, 1);
                        }
                    }
                    createInput ("#blockUp", "date", "blockDate",  "inputDate", "Введите дату", short, '<i class="fas fa-calendar-alt mr-2"></i>' + row.field.name, valueDate, row.field.fieldId, 1, idField);
                }
                if (row.field.fieldType === "TIME") {
                    if (id > 0) {
                        idField = row.field.id;
                        if(row.field.valueDate !== "") {
                            valueDate = formatTime(row.field.valueDate);
                        }
                    }
                    createInput ("#blockUp", "time", "blockTime",  "inputTime", "Введите время", short, '<i class="fas fa-clock mr-2"></i>' + row.field.name, valueDate, row.field.fieldId, 1, idField);
                }
                if (row.field.fieldType === "GROUP_FIELDS") {
                    var groupFieldsElement = {
                        "field" : row.field
                    }
                    groupFieldsArray.push(groupFieldsElement);
                }
            }
            console.log(groupFieldsArray);
            for (var key in groupFieldsArray) {
                key = parseInt(key);
                var rowFields = groupFieldsArray[key];
                var dubKey = parseInt(key+1);
                var blocKey = '';
                var delButton = ' d-none';
                if(key != 0) {
                    blocKey = key;
                    delButton = '';
                }
                if(id > 0) {
                    idField = ' data-id="' + rowFields.field.id + '"';
                }
                $("#newBlockGroup").append('<div class="row card mb-3 blockGroup" id="blockGroup' + blocKey + '" data-field="' + key + '"' + idField + '><div class="col-12"><div class="card-body"><div class="row"><div class="col-md-9 text-left"><h6 id="nameGroup' + blocKey + '">Вопрос ' + dubKey + '</h6></div><div class="col-md-3 text-right"><div id="delGroup' + blocKey + '" class="btn btn-danger btn-sm pointer delGroup rounded' + delButton + '" title="Удалить вопрос"><i class="fas fa-trash"></i></div></div></div><hr><div class="row"><div class="col-12 blockGroupFields" data-block="1"></div></div></div></div></div>');
                $(".blockName").html(rowFields.field.name).attr("data-block" , rowFields.field.fieldId);
                for (var y in rowFields.field.childFields) {
                    var rowSelectField = rowFields.field.childFields[y];
                    if (rowSelectField.fieldType === "CATALOG") {
                        if(id > 0) {
                            idField = ' data-id="' + rowSelectField.id + '"';
                        }
                        var selectFieldName = "selectField" + rowSelectField.catalogId;
                        $("#blockGroup" + blocKey + " .blockGroupFields").append('<div class="row blockRow"><div class="col-md-3 text-left mt-3"><span class="text-muted">' + rowSelectField.name + '</span></div><div class="col-md-9 mt-3"><select class="browser-default custom-select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-field="' + rowSelectField.fieldId + '"' + idField + '><option value="" class="alert-primary" selected>Выберите значение справочника</option></select></div></div>');
                        var numberCatalog = ('#blockGroup' + blocKey + ' #selectField' + rowSelectField.catalogId);
                        // Номер поля для отметки в селектах если нужно
                        var numberField = '';
                        if (id > 0) {
                            numberField = rowSelectField.valueInt;
                        }
                        createOptions ("rest/profile/catalogs/" + rowSelectField.catalogId + "/elems", numberCatalog, "valueStr", "id", numberField);
                    }
                }
            }
        })
            .done(function(response){
                if (response.length == 0){
                    $("#blockUp, #blockDown, #btnSave").addClass("d-none");
                }
        });
    }

    // Формирование массива элементов для JSON
    function createDataField (id) {
        var id = parseInt(id);
        var dataField = [];
        var field = '';
        var idField = null;
        var dataDate = '';
        $('.upElem').each(function(i) {
            var key = i+1;
            var attrElem = $(this).attr("type");
            var attrVal = $(this).val();
            var attrId = parseInt($(this).attr("data-field"));
            if (attrElem === "date") {
                var value = attrVal + "T00:00:00";
                dataDate = attrVal;
            }
            if (attrElem === "time") {
                var value = dataDate + "T" + attrVal + ':00';
            }
            if(id > 0) {
                idField = parseInt($(this).attr("data-id"));
            }
            field = {
                "field": {
                    "id" : idField,
                    "childFields": [],
                    "fieldId": attrId,
                    "valueDate" : value
                },
                "position": key,
            }
            dataField.push(field);
        });
        return dataField;
    }

    // Формирование массива блока для JSON - аргумент начало отсчёта
    function createDataBlock (id, key) {
        var id = parseInt(id);
        var idField = null;
        var dataBlock = [];
        $('.blockGroup').each(function(i) {
            if($(this).attr("data-field") == i) {
                var elementBlock = "#blockGroup";
                if(i !== 0) {
                    elementBlock = elementBlock + i;
                }
                var elementArray = [];
                $(elementBlock + ' [data-field]').each(function() {
                    if(id > 0) {
                        idField = parseInt($(this).attr("data-id"));
                    }
                    var elementBlockElem = {
                        "id" : idField,
                        "childFields" : [],
                        "fieldId" : parseInt($(this).attr("data-field")),
                        "valueInt" : parseInt($(this).val())
                    }
                    elementArray.push(elementBlockElem);
                });
                console.log(JSON.stringify(elementArray));
                var position = parseInt(key)+i;
                var fieldId = parseInt($(".blockName").attr("data-block"));
                if(id > 0) {
                    idField = parseInt($(elementBlock).attr("data-id"));
                }
                var dataBlockElement = {
                    "field" : {
                        "id" : idField,
                        "childFields": elementArray,
                        "fieldId" : fieldId,
                    },
                    "position" : position
                }
            }
            dataBlock.push(dataBlockElement);
        });
        return dataBlock;
    }

    // Формирование объекта JSON
    function createJSON (id,dataType,dataField,dataBlock) {
        var id = parseInt(id);
        if(id === 0) {id = null;}
        var childFields = [];
        if(dataField !== "") {
            for(var key in dataField){
                childFields.push(dataField[key]);
            }
        }
        if(dataBlock !== "") {
            for(var key in dataBlock){
                childFields.push(dataBlock[key]);
            }
        }
        var valueObj = {
            "id" : id,
            "docTypeId" : parseInt(dataType),
            "childFields" : childFields
        }
        return valueObj;
    }