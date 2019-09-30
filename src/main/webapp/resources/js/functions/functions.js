    // Количество элементов в массиве
    function countElem (array) {return array.length;}

    // Получение id документа из адресной строки
    function getId () {return new URL(window.location.href).searchParams.get("id"); }

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
            upClass = ' disableElem';
        }
        var reqUired = '';
        if (required == true) {
            reqUired = ' required';
        }
        if (attachment == 1) {
            $(element).append('<div class="md-form file-field"><div class="btn btn-primary btn-sm float-left"><span>Обзор</span><input title="' + title + '" type="' + type + '" id="' + name + '" name="' + name + '" data-field="' + field + '" ' + idVal + enaBled + reqUired + ' class="inputFile'+ upClass + '"' + inputVal + '></div><div class="file-path-wrapper btnLoad"><input class="file-path validate" type="text" placeholder="Выберите файл"></div></div>');
        } else if (text == 1) {
            $(element).append('<input title="' + title + '" type="' + type + '" id="' + name + '" name="' + name + '" data-field="' + field + '" ' + idVal + enaBled + reqUired + ' class="white form-control' + upClass + '"' + inputVal + '>');
        } else {
            $(element).append('<div class="row ml-1 mb-3" id="' + id + '">' + col + '<div class="row">' + '<div class="col-md-3 text-left">' + '<span for="' + name + '" class="text-muted">' + iconName + '</span>' + '</div>' + '<div class="col-md-9">' + '<input title="' + title + '" type="' + type + '" id="' + name + '" name="' + name + '" data-field="' + field + '" ' + idVal + enaBled + reqUired + ' class="white form-control' + upClass + '"' + inputVal + '><div class="invalid-feedback">Поле обязательно для заполнения</div>' + '</div>' + '</div>' + '</div>' + colShort + '</div>');
        }
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
    function createOptions (url, field, name, id, select) {
        return $.getJSON (url, function(data) {
            for (var i in data) {
                var selectedField = '';
                // Получаем отмеченные поля если есть необходимость
                if (select != '') {if (select === data[i][id]) {selectedField = ' selected="selected"';}}
                $(field).append('<option class="active" value="' + data[i][id] + '"' + selectedField + '>' +  data[i][name] + '</option>');
            }
        })
    }

    // Иерархические справочники (изменение элемента, к какому применить)
    function createOptionsValue (element, block) {
        //console.log(element);
        $(element).on('change', function () {
            var numberSelectField = $(this).val();
            /*var idParent = $(this).attr("name");
            idParent = idParent.substr(11);*/
            //$(block+' .parent').addClass('d-none');
            var idParent = $(this).attr("data-catalog");
            $(block + " .p" + idParent).each(function () {
                $(this).removeClass('d-none');
                $(this).find("select").each(function () {
                    var tempCatalogField = $(this).attr("id");
                    var numberCatalogField = $(this).attr("data-catalog");
                    var nameCatalogField = '#' + tempCatalogField;
                    // Количество опций по запросу, тут же в функции прячем ненужные
                    console.log("rest/profile/catalogs/" + numberCatalogField + "/elems/parent/" + numberSelectField + " - " + nameCatalogField);
                    sumOptions ("rest/profile/catalogs/" + numberCatalogField + "/elems/parent/" + numberSelectField, nameCatalogField);
                    // Открываем опции
                    createOptions ("rest/profile/catalogs/" + numberCatalogField + "/elems/parent/" + numberSelectField, nameCatalogField, "valueStr", "id", "");
                    $(this).find("option.active").remove();
                });
            });
        });
    }

    // Получение правильного и обратного формата даты
    function formatDate (date, reverse) {
        var date = new Date(date);
        var day = date.getDate();
        var month = date.getMonth()+1;
        if (month < 10) {month = '0' + month;}
        var year = date.getFullYear();
        if (reverse == 1) {return year + '-' + month + '-' + day;} else {return day + '-' + month + '-' + year;}
    }

    // Получение правильного и обратного формата времени
    function formatTime (date) {
        var date = new Date(date);
        var hours = date.getHours();
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
                    $("#newBlockGroup").append('<div class="row card mb-3 blockGroup" id="blockGroup' + dubKey + '" data-field="' + dubKey + '"' + idField + '><div class="col-12"><div class="card-body"><div class="row"><div class="col-md-9 text-left"><h6 id="nameGroup">Вопрос ' + dubKey + '</h6></div><div class="col-md-3 text-right"><div id="delGroup' + dubKey + '" class="btn btn-danger btn-sm pointer delGroup rounded' + delButton + '" title="Удалить вопрос"><i class="fas fa-trash"></i></div></div></div><hr><div class="row"><div class="col-12 blockGroupFields" data-block="1"></div></div></div></div></div>');
                    $(".blockName").html(rowFields.field.name).attr("data-block", rowFields.field.fieldId);
                    if(rowFields.field.enabled == false) {$(".addGroup").remove();}
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
                        if(rowSelectField.required == true) {required = ' required';}
                        // Если вид поля SELECT
                        if (rowSelectField.fieldType === "CATALOG") {
                            var selectFieldName = 'selectField' + dubKey + rowSelectField.catalogId;
                            // Добавляем строку
                            $('#blockGroup' + dubKey + ' .blockGroupFields').append('<div class="row blockRow' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><span class="text-muted">' + rowSelectField.name + '</span></div><div class="col-md-9 mt-3"><select class="browser-default custom-select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-catalog="' + rowSelectField.catalogId + '" data-field="' + rowSelectField.fieldId + '"' + idField + enaOpiton + required + '><option value="" class="alert-primary" selected>Выберите значение справочника</option></select></div></div>');
                            //var numberCatalog = ('#blockGroup' + dubKey + '#selectField' + dubKey + rowSelectField.catalogId);
                            var numberCatalog = ('#' + selectFieldName);
                            // Формирование правильных полей
                            //var numberFieldValue = $(selectFieldName).parents('.blockGroup').attr('data-field');
                            //console.log(numberFieldValue);
                            //createOptionsValue('#blockGroup' + dubKey + ' #'+selectFieldName);
                            createOptionsValue(numberCatalog, '#blockGroup' + dubKey);
                            if(parentBlock == '') {
                                // Добавляем опции
                                createOptions ("rest/profile/catalogs/" + rowSelectField.catalogId + "/elems", numberCatalog, "valueStr", "id", numberField);
                            }
                        }
                        if (rowSelectField.fieldType === "ATTACHMENT") {
                            // Добавляем строку
                            if(parentBlock == '') {
                                /*var fileId = y+1;
                                var nameFile = "inputFile" + fileId;*/
                                $('#blockGroup' + dubKey + ' .blockGroupFields').append('<div class="row blockRow' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><span class="text-muted">' + rowSelectField.name + '</span></div><div class="col-md-9 mt-3"></div></div>');
                                createInput(".col-md-9:last", "file", 'inputFile', 'inputFile', "Загрузить файл", 0, '' + rowSelectField.name, rowSelectField.valueStr, rowSelectField.fieldId, 0, idField, rowSelectField.enabled, rowSelectField.required, 1, '');
                                /*fileId = fileId+1;*/
                            }
                        }
                        if (rowSelectField.fieldType === "TEXTAREA") {
                            // Добавляем строку
                            if(parentBlock == '') {
                                $('#blockGroup' + dubKey + ' .blockGroupFields').append('<div class="row blockRow' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><span class="text-muted">' + rowSelectField.name + '</span></div><div class="col-md-9 mt-3"><textarea></textarea></div></div>');
                            }
                        }
                        if (rowSelectField.fieldType === "TEXT") {
                            // Добавляем строку
                            if(parentBlock == '') {
                                var textId = y+1;
                                var nameText = "inputText" + textId;
                                $('#blockGroup' + dubKey + ' .blockGroupFields').append('<div class="row blockRow' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><span class="text-muted">' + rowSelectField.name + '</span></div><div class="col-md-9 mt-3"></div></div>');
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
            console.log(i);
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

    // Формирование объекта JSON
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