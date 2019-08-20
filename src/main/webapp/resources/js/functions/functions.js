    // Функция получения текстового поля
    function createInput (element, type, id, name, title, short, iconName, value) {
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
        $(element).append('<div class="row ml-1 mb-3" id="' + id + '">' + col + '<div class="row">' + '<div class="col-md-4 text-left">' + '<span for="' + name + '" class="text-muted">' + iconName + '</span>' + '</div>' + '<div class="col-md-8">' + '<input title="' + title + '" type="' + type + '" id="' + name + '" name="' + name + '" class="white form-control"' + inputVal + '>' + '</div>' + '</div>' + '</div>' + colShort + '</div>');
    }

    // Функция получения полей по выбору SELECT Запрос:Атрибут_поля:Название_поля:Значение_поля:Значение_выбранного_поля
    function createOptions (url, field, name, id, select) {
        $.getJSON (url, function(data) {
            for (var i in data) {
                var selectedField = '';
                // Получаем отмеченные поля если есть необходимость
                if (select) {
                    if(select === data[i][id]) {
                        selectedField = 'selected="selected"';
                    }
                }
                $(field).append('<option value="' + data[i][id] + '"' + selectedField + '>' +  data[i][name] + '</option>');
            }
        });
    }

    // Получение правильного и обратного формата даты
    function formatDate (date, reverse) {
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
    function formatTime (date, reverse) {
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

    // Список полей по виду документа Путь:номер_документа
    function getFieldsDocument (url, id, short) {
        $.getJSON (url, function(data) {
            // Массив для полей в одной группе
            var newBlockArray = [];
            for (var i in data) {
                var row = data[i];
                if (row.field.fieldType === "TIME") {
                    createInput ("#blockUp", "time", "blockTime",  "inputTime", "Введите время", short, '<i class="fas fa-clock mr-2"></i>' + row.field.name);
                }
                if (row.field.fieldType === "DATE") {
                    createInput ("#blockUp", "date", "blockDate",  "inputDate", "Введите дату", short, '<i class="fas fa-calendar-alt mr-2"></i>' + row.field.name);
                }
                if (row.field.fieldType === "GROUP_FIELDS") {
                    var newBlockArrayElem = {
                        "field" : row.field
                    }
                    newBlockArray.push(newBlockArrayElem);
                }
            }
            for (var key in newBlockArray) {
                key = parseInt(key);
                var newRowFields = newBlockArray[key];
                var dubbleKey = parseInt(key+1);
                var questionKey = '';
                var delButton = ' d-none';
                if(key != 0) {
                    questionKey = key;
                    delButton = '';
                }
                $("#newBlockGroup").append('<div class="row card mb-3 blockGroup" id="blockGroup' + questionKey + '" data-field="' + key + '"><div class="col-12"><div class="card-body"><div class="row"><div class="col-md-9 text-left"><h6 id="nameGroup' + questionKey + '">Вопрос ' + dubbleKey + '</h6></div><div class="col-md-3 text-right"><div id="delGroup' + questionKey + '" class="btn btn-danger btn-sm pointer delGroup d-none rounded' + delButton + '" title="Удалить вопрос"><i class="fas fa-trash"></i></div></div></div><hr><div class="row"><div class="col-12 blockGroupFields" data-block="1"></div></div></div></div></div>');
                $(".blockName").html(newRowFields.field.name);
                for (var y in newRowFields.field.childFields) {
                    var rowSelectField = newRowFields.field.childFields[y];
                    if (rowSelectField.fieldType === "CATALOG") {
                        var selectFieldName = "selectField" + rowSelectField.catalogId;
                        $("#newBlockGroup" + questionKey + " .blockGroupFields").append('<div class="row blockRow"><div class="col-md-3 text-left mt-3"><span class="text-muted">' + rowSelectField.name + '</span></div><div class="col-md-9 mt-3"><select class="browser-default custom-select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-field="' + rowSelectField.fieldId + '"><option value="" class="alert-primary" selected>Выберите значение справочника</option></select></div></div>');
                        var numberCatalog = ('#blockGroup' + questionKey + ' #selectField' + rowSelectField.catalogId);
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


    /*$(".blockName").html(row.field.name);
    for(var k in row.field.childFields) {
        var rowSelectField = row.field.childFields[k];
        var selectFieldName = "selectField" + rowSelectField.catalogId;
        $(".blockGroupFields").append('<div class="row blockRow"><div class="col-md-3 text-left mt-3"><span class="text-muted">' + rowSelectField.name + '</span></div><div class="col-md-9 mt-3"><select class="browser-default custom-select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-field="' + rowSelectField.fieldId + '"><option value="" class="alert-primary" selected>Выберите значение справочника</option></select></div></div>');
        // Получаем поля Option для Select CATALOG
        createOptions("rest/profile/catalogs/" + rowSelectField.catalogId + "/elems", '#selectField'+rowSelectField.catalogId, "valueStr", "id", "");
    }*/


