    // Количество элементов в массиве
    function countElem (array) {return array.length;}

    // Получение id документа из адресной строки
    function getId () {return new URL(window.location.href).searchParams.get("id"); }

    // Прооверка полей на заполняемость
    function checkValidation(value) {
        var validation = true;
        $(value).each(function() {
            if($(this).val() === '') {validation = false;}
        });
        return validation;
    }

    // Конфигурация сообщения об ошибках
    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "md-toast-bottom-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": 300,
        "hideDuration": 1000,
        "timeOut": 5000,
        "extendedTimeOut": 1000,
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }

    // Заполнение данных организации
    function getValueOrganisation (url, element) {
        return $.getJSON (url, function(data) {
            $(element + ' #shortNameLf').val(data.shortNameLf);
            $(element + ' #fullNameLf').val(data.fullNameLf);
            $(element + ' #inn').val(data.inn);
            $(element + ' #ogrn').val(data.ogrn);
            $(element + ' #kpp').val(data.kpp);
            $(element + ' #address').val(data.address);
            $(element + ' #fioManager').val(data.fioManager);
            $(element + ' #positionManager').val(data.positionManager);
            $(element + ' #shortName').val(data.shortName);
            $(element + ' #shortLegalForm').val(data.shortLegalForm);
            $(element + ' #fullLegalForm').val(data.fullLegalForm);
            $(element + ' #normalizedName').val(data.normalizedName);
        });
    }

    // Функция получения текстового поля
    /*
    1. element - Элемент к которому добавляется поле
    2. type - Тип поля (data, time, text);
    3. id - Значение атрибута id;
    4. name - Название поля (англ.)
    5. title - Поясняющая надпись (рус);
    6. short - Цифровое значения короткого поля (1 - да)
    7. iconName - обозначение иконки справа
    8. value - Значение атрибута value
    9. field - Значение атрибута data-field
    10. up - Присвоени класса upElem (1 - да)
    11. idField - Значение атрибута data-id
    12. enabled - Значение атрибута enabled (true - да)
    13. required - Значение атрибута required (true - да)
    14. attachment - Поле инпут для ввода данных
    15. text - Поле инпут для ввода текста
    */
    function createInput (element, type, id, name, title, short, iconName, value, field, up, idField, enabled, required, attachment, text) {
        var idVal = "";
        if (idField) {
            idVal = ' data-id="' + idField + '"';
        }
        var inputVal = '';
        if (value) {
            inputVal = ' value="' + value + '"';
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
        var enaBled = '';
        if (enabled == false) {
            enaBled = ' disabled';
            upClass = upClass + ' disableElem';
        }
        var reqUired = '';
        var requiredSup = '';
        var requiredValidate = '';
        if (required == true) {
            reqUired = ' required';
            requiredSup = '<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup>';
            requiredValidate = '<div class="invalid-feedback">Поле обязательно для заполнения</div>';
        }
        if (attachment == 1) {
            $(element).append('<div class="md-form file-field mb-2"><div class="btn btn-primary btn-sm float-left"><span>Обзор</span><input title="' + title + '" type="' + type + '" id="' + name + '" name="' + name + '" data-field="' + field + '" ' + idVal + enaBled + reqUired + ' class="inputFile'+ upClass + '"' + inputVal + '></div><div class="file-path-wrapper btnLoad"><input class="file-path validate" type="text" placeholder="Выберите файл">' + requiredValidate + '</div></div>');
        } else if (attachment == 2) {
            $(element).append('<div class="row d-flex align-items-center"><div class="col-md-9"><div class="md-form file-field"><div class="btn btn-primary btn-sm float-left"><span>Обзор</span><input title="' + title + '" type="' + type + '" id="' + name + '" name="' + name + '" data-field="' + field + '" ' + idVal + enaBled + reqUired + ' class="inputFile'+ upClass + '"' + inputVal + '></div><div class="file-path-wrapper btnLoad"><input class="file-path validate" type="text" placeholder="Выберите файл">' + requiredValidate + '</div></div></div><div class="col-md-3"><a href="#" id="btnLoad" class="btn btn-default btn-sm rounded" target="_blank" data-toggle="tooltip" title="Скачать файл"><i class="fas fa-download"></i></a></div></div>');
        } else if (text == 1) {
            $(element).append('<input title="' + title + '" type="' + type + '" id="' + name + '" name="' + name + '" data-field="' + field + '" ' + idVal + enaBled + reqUired + ' class="white form-control' + upClass + '"' + inputVal + '>' + requiredValidate);
        } else {
            $(element).append('<div class="row ml-1 mb-3" id="' + id + '">' + col + '<div class="row">' + '<div class="col-md-3 text-left">' + '<div for="' + name + '" class="text-muted">' + iconName + requiredSup + '</div>' + '</div>' + '<div class="col-md-9">' + '<input title="' + title + '" type="' + type + '" id="' + name + '" name="' + name + '" data-field="' + field + '" ' + idVal + enaBled + reqUired + ' class="white form-control' + upClass + '"' + inputVal + '>' + requiredValidate + '</div>' + '</div>' + '</div>' + colShort + '</div>');
        }
        $('[data-toggle="tooltip"]').tooltip();
    }

    // Подсчёт OPTION в поле SELECT
    function sumOptions (url, field) {
        return $.getJSON (url, function(data) {
            var jsonZapros = data.length;
            if(jsonZapros > 0) {
                $(field).parents(".blockRow").attr("data-option",jsonZapros)/*.removeClass('d-none')*/;
            } else {
                $(field).parents(".blockRow").attr("data-option",jsonZapros).addClass('d-none');
            }
        })
    }

    // Функция получения полей по выбору SELECT
    /*
    1. url - Запрос
    2. field - Элемент SELECT к которому добавляется поле
    3. name - Название поля
    4. id - Название атрибута у выбранного поля
    5. select - Значение выбранного атрибута value
    */
    function createOptions (url, field, name, id, select, spisok) {
        return $.getJSON (url, function(data) {
            for (var i in data) {
                var selectedField = '';
                var nameField = data[i][name];
                // Получаем отмеченные поля если есть необходимость
                if (select != '') {if (select === data[i][id]) {selectedField = ' selected="selected"';}}
                if(spisok === 'users') {
                    var userName = data[i]['fullName'];
                    var phone = '';
                    if(data[i]['phone'] && data[i]['phone'] != '') {phone = ' , тел. ' + data[i]['phone'];}
                    nameField = userName + phone;
                } else if(spisok === 'usersList') {nameField = data[i]['fullName'];}
                $(field).append('<option class="active" value="' + data[i][id] + '"' + selectedField + '>' +  nameField + '</option>');
                //$(field + '.mdb-select').materialSelect();
            }
        })
    }

    // Добавление должности при изменении пользователя
    function createUserList (url, field) {
        return $.getJSON (url, function(data) {
            $(field).html(data.position);
        });
    }

    // Иерархические справочники (изменение элемента, к какому применить)
    function createOptionsValue (element, block) {
        //console.log(element);
        $(element).on('change', function () {
            var numberSelectField = $(this).val();
            if(numberSelectField) {
                //$(block+' .parent').addClass('d-none');
                var idParent = $(this).attr("data-catalog");
                $(block + " .p" + idParent).each(function () {
                    $(this).removeClass('d-none');
                    $(this).find("select").each(function () {
                        var tempCatalogField = $(this).attr("id");
                        var numberCatalogField = $(this).attr("data-catalog");
                        var nameCatalogField = '#' + tempCatalogField;
                        // Количество опций по запросу, тут же в функции прячем ненужные
                        //console.log("rest/profile/catalogs/" + numberCatalogField + "/elems/parent/" + numberSelectField + " - " + nameCatalogField);
                        sumOptions ("rest/profile/catalogs/" + numberCatalogField + "/elems/parent/" + numberSelectField, nameCatalogField);
                        // Открываем опции
                        createOptions ("rest/profile/catalogs/" + numberCatalogField + "/elems/parent/" + numberSelectField, nameCatalogField, "valueStr", "id", "", "");
                        $(this).find("option.activeStat").remove();
                    });
                });
            }

        });
    }

    // Получение правильного и обратного формата даты
    function formatDate (date, reverse) {
        var date = new Date(date);
        var day = date.getDate();
        if (day < 10) {day = '0' + day;}
        var month = date.getMonth()+1;
        if (month < 10) {month = '0' + month;}
        var year = date.getFullYear();
        if (reverse == 1) {return year + '-' + month + '-' + day;} else {return day + '-' + month + '-' + year;}
    }

    // Получение правильного и обратного формата времени
    function formatTime (date) {
        var date = new Date(date);
        var hours = date.getHours();
        if (hours < 10) {hours = '0' + hours;}
        var minutes = date.getMinutes();
        if (minutes < 10) {minutes = '0' + minutes;}
        return hours + ':' + minutes;
    }

    // Получение верхнего стека полей
    function getUpFields(url, id, short) {
        return $.getJSON (url, function(data) {
            var rowChild = data;
            if(id > 0) {rowChild = data.childFields;}
            for(var i in rowChild) {
                var row = rowChild[i];
                // Переменная даты
                var valueDate = '';
                // Переменная поля
                var idField = '';
                var enaOpiton = '';
                var numberField = '';
                if(id > 0) {
                    idField = ' data-id="' + row.field.id + '"';
                    // Номер поля для отметки в селектах если нужно
                    numberField = row.field.valueInt;
                }
                if(row.field.enabled == false) {enaOpiton = ' disabled';}
                var required = '';
                var requiredSup = '';
                var requiredValidate = '';
                if(row.field.required == true) {
                    required = ' required';
                    requiredSup = '<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup>';
                    requiredValidate = '<div class="invalid-feedback">Поле обязательно для заполнения</div>';
                }
                var selectFieldName = 'selectField' + row.field.fieldId;
                if (row.field.fieldType === "DATE") {
                    if (id > 0) {
                        idField = row.field.id;
                        if(row.field.valueDate !== '') {valueDate = formatDate(row.field.valueDate, 1);}
                    }
                    createInput ("#blockUp", "date", "blockDate",  "inputDate", "Введите дату", short, '<i class="fas fa-calendar-alt mr-2"></i>' + row.field.name, valueDate, row.field.fieldId, 1, idField, row.field.enabled, row.field.required, '', '');
                }
                if (row.field.fieldType === "TIME") {
                    if (id > 0) {
                        idField = row.field.id;
                        if(row.field.valueDate !== '') {valueDate = formatTime(row.field.valueDate);}
                    }
                    createInput ("#blockUp", "time", "blockTime",  "inputTime", "Введите время", short, '<i class="fas fa-clock mr-2"></i>' + row.field.name, valueDate, row.field.fieldId, 1, idField, row.field.enabled, row.field.required, '', '');
                }
                var textId = i+1;
                if (row.field.fieldType === "TEXT") {
                    var nameText = "inputText" + textId;
                    createInput("#blockUp", "text", nameText, nameText, "Введите значение", short, '' + row.field.name, row.field.valueStr, row.field.fieldId, 1, idField, row.field.enabled, row.field.required, '', '');
                    textId = textId+1;
                }
                // Если вид поля справочник организаций
                if (row.field.fieldType === "CATALOG_REGNUMBERS") {
                    // Добавляем строку
                    $('#blockUp').append('<div class="row ml-1 mb-3 d-flex align-items-center justify-content-center"><div class="col-md-3 text-left"><div class="text-muted">' + row.field.name + requiredSup + '</div></div><div class="col-md-9 select-outline"><select class="mdb-select md-form md-outline validate colorful-select dropdown-primary upElem" searchable=" Поиск" type="select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-field="' + row.field.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select>' + requiredValidate + '</div></div>');
                    var numberCatalog = ('#' + selectFieldName);
                    $(numberCatalog + '.mdb-select').materialSelect();
                    // Добавляем опции
                    createOptions ("rest/profile/docs/regnumbers/", numberCatalog, "regNum", "id", numberField, '');
                }
            }
        }).done(function(response) {
            if (response.length == 0) {$("#blockUp, #blockDown, #btnSave, #btnWordFile").addClass("d-none");}
        });
    }

    // Получение нижнего стека полей
    function getDownFields(url, id, number) {
        return $.getJSON (url, function(data) {
            var rowChild = data;
            if(id > 0) {
                rowChild = data.childFields;
                number = 1;
            }
            var idField = '';
            for(var key in rowChild) {
                var row = rowChild[key];
                if (row.field.fieldType === "GROUP_FIELDS") {
                    key = parseInt(key);
                    var rowFields = data[key];
                    if(id > 0) {rowFields = row;}
                    var dubKey = 1;
                    var dataField = 0;
                    if(number != '') {
                        dubKey = number;
                        dataField = number;
                    }
                    var delButton = ' d-none';
                    // Кнопка удаления полей
                    if(id > 0) {
                        idField = ' data-id="' + rowFields.field.id + '"';
                        if(number > 1) {delButton = '';}
                    } else {
                        if(number != '') {delButton = '';}
                    }
                    // Добавляем вопрос ' + blocKey + '
                    $("#newBlockGroup").append('<div class="row card mb-3 blockGroup" id="blockGroup' + dubKey + '" data-field="' + dubKey + '"' + idField + '><div class="col-12"><div class="card-body"><div class="row"><div class="col-md-9 text-left"><h5 id="nameGroup">Блок ' + dubKey + '</h5></div><div class="col-md-3 text-right"><div id="delGroup' + dubKey + '" class="btn btn-danger btn-sm pointer delGroup rounded' + delButton + '" data-toggle="tooltip" title="Удалить блок"><i class="fas fa-trash"></i></div></div></div><hr><div class="row"><div class="col-12 blockGroupFields" data-block="1"></div></div></div></div></div>');
                    $(".blockName").html(rowFields.field.name).attr("data-block", rowFields.field.fieldId);
                    if(row.field.enabled === false) {$('.addGroup, #nameGroup').addClass('d-none');}
                    if(row.field.enabled === true) {$('.addGroup, #nameGroup').removeClass('d-none');}
                    for (var y in rowFields.field.childFields) {
                        //console.log(rowFields.field.childFields);
                        var rowSelectField = rowFields.field.childFields[y];
                        // Есть ли родитель у блока
                        var parentBlock = '';
                        var parentCatalog = '';
                        if(rowSelectField.parentCatalogId > 0) {
                            parentCatalog = ' p' + rowSelectField.parentCatalogId;
                            parentBlock = ' d-none';
                        }
                        var numberField = '';
                        if(id > 0) {
                            if(rowSelectField.parentCatalogId > 0) {
                                parentCatalog = ' p' + rowSelectField.parentCatalogId;
                                if(rowSelectField.valueInt && rowSelectField.valueInt > 0) {
                                    parentBlock = '';
                                } else {
                                    parentBlock = ' d-none';
                                }
                            }
                            idField = ' data-id="' + rowSelectField.id + '"';
                            // Номер поля для отметки в селектах если нужно
                            numberField = rowSelectField.valueInt;
                        }
                        var enaOpiton = '';
                        if(rowSelectField.enabled == false) {enaOpiton = ' disabled';}
                        var required = '';
                        var requiredSup = '';
                        var requiredValidate = '';
                        if(rowSelectField.required == true) {
                            required = ' required';
                            requiredSup = '<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup>';
                            requiredValidate = '<div class="invalid-feedback">Поле обязательно для заполнения</div>';
                        }
                        var selectFieldName = 'selectField' + dubKey + rowSelectField.catalogId;
                        // Если вид поля SELECT
                        if (rowSelectField.fieldType === "CATALOG") {
                            // Добавляем строку
                            $('#blockGroup' + dubKey + ' .blockGroupFields').append('<div class="row blockRow' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3 d-flex align-items-center"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9 mt-3 select-outline"><select class="mdb-select md-form md-outline validate colorful-select dropdown-primary" searchable=" Поиск" id="' + selectFieldName + '" name="' + selectFieldName + '" data-catalog="' + rowSelectField.catalogId + '" data-field="' + rowSelectField.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select>' + requiredValidate + '</div></div>');
                            var numberCatalog = ('#' + selectFieldName);
                            $(numberCatalog + '.mdb-select').materialSelect();
                            // Формирование правильных полей
                            createOptionsValue(numberCatalog, '#blockGroup' + dubKey);
                            if(parentBlock == '') {
                                // Добавляем опции
                                createOptions("rest/profile/catalogs/" + rowSelectField.catalogId + "/elems", numberCatalog, "valueStr", "id", numberField, "");
                            }
                        }
                        // Если вид поля справочник организаций
                        if (rowSelectField.fieldType === "CATALOG_ORGANIZATIONS") {
                            var numberCatalog = ('#' + selectFieldName);
                            // Добавляем строку
                            $('#blockGroup' + dubKey + ' .blockGroupFields').append('<div class="row blockRow d-flex align-items-center' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-8 mt-3 select-outline"><select class="mdb-select md-form md-outline validate colorful-select dropdown-primary" searchable=" Поиск" id="' + selectFieldName + '" name="' + selectFieldName + '" data-catalog="' + rowSelectField.catalogId + '" data-field="' + rowSelectField.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select></div><div class="col-md-1 mt-3 text-right"><button class="btn btn-primary btn-md addElement rounded m-0 px-3 waves-effect" data-toggle="modal" data-target="#addElement" data-catalog="' + numberCatalog + '" type="button" data-toggle="tooltip" title="Добавить организацию" ' + enaOpiton + '><i class="fas fa-plus white-text"></i></button></div>' + requiredValidate + '</div>');
                            $(numberCatalog + '.mdb-select').materialSelect();
                            // Добавляем опции
                            createOptions ("rest/profile/organizations/", numberCatalog, "shortNameLf", "id", numberField, '');
                        }
                        // Если вид поля справочник пользователей
                        if (rowSelectField.fieldType === "CATALOG_USERS") {
                            var numberCatalog = ('#' + selectFieldName);
                            // Добавляем строку
                            $('#blockGroup' + dubKey + ' .blockGroupFields').append('<div class="row blockRow d-flex align-items-center' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9 mt-3 select-outline"><select class="mdb-select md-form md-outline validate colorful-select dropdown-primary" searchable=" Поиск" id="' + selectFieldName + '" name="' + selectFieldName + '" data-catalog="' + rowSelectField.catalogId + '" data-field="' + rowSelectField.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select>' + requiredValidate + '</div></div>');
                            $(numberCatalog + '.mdb-select').materialSelect();
                            // Добавляем опции
                            createOptions ("rest/profile/users/", numberCatalog, '', 'id', numberField, 'users');
                            $(numberCatalog + '.mdb-select').materialSelect();
                        }
                        if (rowSelectField.fieldType === "ATTACHMENT") {
                            // Добавляем строку
                            if(parentBlock == '') {
                                var attachment = 1;
                                if(id && id > 0) {attachment = 2;}
                                $('#blockGroup' + dubKey + ' .blockGroupFields').append('<div class="row blockRow d-flex align-items-center' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9"></div></div>');
                                createInput(".col-md-9:last", "file", 'inputFile', 'inputFile', "Загрузить файл", 0, '' + rowSelectField.name, rowSelectField.valueStr, rowSelectField.fieldId, 0, idField, rowSelectField.enabled, rowSelectField.required, attachment, '');
                                // Подсказки
                            }
                        }
                        if (rowSelectField.fieldType === "TEXTAREA") {
                            // Добавляем строку
                            if(parentBlock == '') {
                                $('#blockGroup' + dubKey + ' .blockGroupFields').append('<div class="row blockRow' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9 mt-3"><textarea></textarea>' + requiredValidate + '</div></div>');
                            }
                        }
                        if (rowSelectField.fieldType === "TEXT") {
                            // Добавляем строку
                            if(parentBlock == '') {
                                var textId = y+1;
                                var nameText = "inputText" + textId;
                                $('#blockGroup' + dubKey + ' .blockGroupFields').append('<div class="row blockRow d-flex align-items-center' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9 mt-3"></div></div>');
                                createInput(".col-md-9:last", "text", nameText, nameText, "Введите значение", 0, '' + rowSelectField.name, rowSelectField.valueStr, rowSelectField.fieldId, 0, idField, rowSelectField.enabled, rowSelectField.required, '', 1);
                                textId = textId+1;
                            }
                        }
                    }
                    if(id > 0) {
                        number = number+1;
                    }
                }
            }
        }).done(function(response) {
            if (response.length == 0) {$("#blockUp, #blockDown, #btnSave, #btnWordFile").addClass("d-none");}
        });
    }

    // Формирование массива элементов для JSON
    function createDataField (id) {
        var id = parseInt(id);
        var dataField = [];
        var field = '';
        var idField = null;
        var dataDate = '';
        var valueData = '';
        $('.upElem').each(function(i) {
            var key = i+1;
            var attrElem = $(this).attr("type");
            var attrVal = $(this).val();
            var attrId = parseInt($(this).attr("data-field"));
            if (attrElem === "date") {
                valueData = 1;
                if (attrVal != '') {
                    var value = attrVal + "T00:00:00";
                    dataDate = attrVal;
                } else {var value = null;}
            }
            if (attrElem === "time") {
                valueData = 1;
                if (attrVal != '') {var value = "1900-01-01" + "T" + attrVal + ":00";} else {var value = null;}
            }
            if (attrElem === "text") {
                valueData = 2;
                value = attrVal;
            }
            if (attrElem === "select") {
                valueData = 3;
                value = attrVal;
            }
            if (id > 0) {idField = parseInt($(this).attr("data-id"));}
            if (valueData === 1) {
                field = {
                    "field": {
                        "id" : idField,
                        "childFields": [],
                        "fieldId": attrId,
                        "valueDate" : value
                    },
                    "position": key,
                }
            } else if (valueData === 2) {
                field = {
                    "field": {
                        "id" : idField,
                        "childFields": [],
                        "fieldId": attrId,
                        "valueStr" : value
                    },
                    "position": key,
                }
            } else if (valueData === 3) {
                field = {
                    "field": {
                        "id" : idField,
                        "childFields": [],
                        "fieldId": attrId,
                        "valueInt" : value
                    },
                    "position": key,
                }
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
        $('.blockGroup').each(function(item) {
            var i = parseInt(item)+1;
            //console.log(i);
            if($(this).attr("data-field") == i) {
                var elementBlock = "#blockGroup";
                if(i !== 0) {elementBlock = elementBlock + i;}
                var elementArray = [];
                $(elementBlock + ' [data-field]').each(function() {
                    if(id > 0) {idField = parseInt($(this).attr("data-id"));}
                    var typeAttr = $(this).attr("type");
                    if (typeAttr && typeAttr !== '') {
                        var elementBlockElem = {
                            "id" : idField,
                            "childFields" : [],
                            "fieldId" : parseInt($(this).attr("data-field")),
                            "valueStr" : $(this).val()
                        }
                    } else {
                        var elementBlockElem = {
                            "id" : idField,
                            "childFields" : [],
                            "fieldId" : parseInt($(this).attr("data-field")),
                            "valueInt" : parseInt($(this).val())
                        }
                    }
                    elementArray.push(elementBlockElem);
                });
                var position = parseInt(key)+i;
                var fieldId = parseInt($(".blockName").attr("data-block"));
                if(id > 0) {idField = parseInt($(elementBlock).attr("data-id"));}
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

    // Формирование объекта JSON для отправки на сервер
    function createJSON (id,dataType,dataField,dataBlock) {
        var id = parseInt(id);
        if(id === 0) {id = null;}
        var childFields = [];
        if(dataField !== "") {for (var key in dataField) {childFields.push(dataField[key]);}}
        if(dataBlock !== "") {for (var key in dataBlock) {childFields.push(dataBlock[key]);}}
        var valueObj = {
            "id" : id,
            "docTypeId" : parseInt(dataType),
            "childFields" : childFields
        }
        return valueObj;
    }

    // Формирование листа согласования
    function createAgreeList (data) {
        var agreeList = [];
        var agreeSum = $(data).length;
        var finalUser = false;
        var currentUser = false;
        for(var i in data) {
            if (i < agreeSum) {
                var ordering = parseInt(i)+1;
                if (ordering === agreeSum) {finalUser = true;} else {finalUser = false;}
                if (ordering === 1) {currentUser = true;} else {currentUser = false;}
                var element = {
                    "id" : null,
                    "ordering" : ordering,
                    "user" : {
                        "id" : data[i]['value']
                    },
                    "finalUser" : finalUser,
                    "currentUser" : currentUser
                }
                agreeList.push(element);
            }
        }
        return agreeList;
    }


    // Функция получения записей в таблицу
    function dataTableArray (element, url) {
        $(element).DataTable({
            "columns": [
                { 'data': 'docType.id' },
                { 'data': 'id' },
                { 'data': 'projectRegNum' },
                { 'data': 'insertDateTime' },
                { 'data': 'docType.name' },
            ],
            "ajax": {
                "url" : url,
                "dataSrc" : function(data) {
                    $.each(data, function(i, item) {
                        var key = parseInt(i)+1;
                        item.docType.id = key;
                        var number =  item.id;
                        if (item.regNum && item.regNum !== '') {
                            item.id = item.regNum;
                            item.projectRegNum = formatDate(item.regDateTime, 0);
                        } else {
                            item.id = item.projectRegNum;
                            item.projectRegNum = formatDate(item.insertDateTime, 0);
                        }
                        item.insertDateTime = item.docType.name;
                        item.docType.name = "<a href='agree-document?id=" + number + "'><i class='fas fa-edit text-primary'></i></a>";
                    });
                    return data;
                }
            },
            "iDisplayLength": 25,
            "language": {
                "processing": "Подождите...",
                "search": "Поиск:",
                "lengthMenu": "Показать _MENU_ записей",
                "info": "Страница _PAGE_ из _PAGES_",
                "infoEmpty": "",
                "infoFiltered": "(отфильтровано из _MAX_ записей)",
                "infoPostFix": "",
                "loadingRecords": "Загрузка записей...",
                "zeroRecords": "Записи отсутствуют.",
                "emptyTable": "В таблице отсутствуют данные",
                "paginate": {
                    "first": "",
                    "previous": "",
                    "next": "",
                    "last": ""
                },
                "aria": {
                    "sortAscending": ": активировать для сортировки столбца по возрастанию",
                    "sortDescending": ": активировать для сортировки столбца по убыванию"
                }
            }
        });
        $('.dataTables_length').addClass('bs-select');
    }

    // Формирование списка согласователей без фозможности редактирования
    function createUserListDisabled (url) {
        return $.getJSON(url, function(data) {
            for(var i in data) {
                var row = data[i];
                var position = '';
                var comment = '';
                var agreedDateTime = '';
                if(row.agreedDateTime) {
                    var newDate = formatDate(row.agreedDateTime);
                    var newTime = formatTime(row.agreedDateTime);
                    agreedDateTime = newDate+'/'+newTime;
                }
                if(row.comment) {comment = row.comment;}
                if(row.position) {position = row.position;}
                if(row.currentUser === true) {
                    var currentUser = '<i class="fas fa-user-clock text-warning"></i>';
                } else {
                    currentUser = '<i class="fas fa-ellipsis-h text-muted"></i>';
                }
                if(row.decisionType && row.decisionType === 'ACCEPTED') {
                    currentUser = '<i class="fas fa-check text-success"></i>';
                }
                $('#userListBlockDiv').append('<div class="row mb-3 d-flex align-items-center"><div class="col-1 text-center">'+row.ordering+'</div><div class="col-1 text-center">'+currentUser+'</div><div class="col-4">'+row.fullName+'<br><small class="text-muted">'+position+'</small></div><div class="col-3"><small>'+comment+'</small></div><div class="col-3"><small>'+agreedDateTime+'</small></div></div>');
            }
        });
    }