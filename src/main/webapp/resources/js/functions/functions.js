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
        }
        var reqUired = '';
        if (required == true) {
            reqUired = ' required';
        }
        if (attachment == 1) {
            $(element).append('<div class="md-form file-field"><div class="btn btn-primary btn-sm float-left"><span>Обзор</span><input title="' + title + '" type="' + type + '" id="' + name + '" name="' + name + '" data-field="' + field + '" ' + idVal + enaBled + reqUired + ' class="'+ upClass + '"' + inputVal + '></div><div class="file-path-wrapper btnLoad"><input class="file-path validate" type="text" placeholder="Выберите файл"></div></div>');
        } else if (text == 1) {
            $(element).append('<input title="' + title + '" type="' + type + '" id="' + name + '" name="' + name + '" data-field="' + field + '" ' + idVal + enaBled + reqUired + ' class="white form-control' + upClass + '"' + inputVal + '>');
        } else {
            $(element).append('<div class="row ml-1 mb-3" id="' + id + '">' + col + '<div class="row">' + '<div class="col-md-4 text-left">' + '<span for="' + name + '" class="text-muted">' + iconName + '</span>' + '</div>' + '<div class="col-md-8">' + '<input title="' + title + '" type="' + type + '" id="' + name + '" name="' + name + '" data-field="' + field + '" ' + idVal + enaBled + reqUired + ' class="white form-control' + upClass + '"' + inputVal + '><div class="invalid-feedback">Поле обязательно для заполнения</div>' + '</div>' + '</div>' + '</div>' + colShort + '</div>');
        }
    }

    // Подсчёт OPTION в поле SELECT
    function sumOptions (url, field) {
        return $.getJSON (url, function(data) {
            var jsonZapros = data.length;
            if(jsonZapros > 0) {
                $(field).parents(".blockRow").attr("data-option",jsonZapros);
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

    // Изменение полей при выборе OPTION
    function changeSelect (data, field) {
        $(data).map(function(i, element) {
            console.log(element);
            var numberCatalogField = element.substr(11);
            var nameCatalogField = "#" + element;
            $(this).find("select option.active").remove();
            //alert("rest/profile/catalogs/" + numberCatalogField + "/elems/parent/" + numberSelectField + " -- " + nameCatalogField);
            createOptions ("rest/profile/catalogs/" + numberCatalogField + "/elems/parent/" + field, nameCatalogField, "valueStr", "id", "");
            $(this).removeClass('d-none');
            alert(nameCatalogField);
            var sumOption = $(nameCatalogField).attr("data-option");
            //alert(sumOption);
            if(sumOption > 0) {$(this).removeClass('d-none');} else {$(this).addClass('d-none');}
        });
    }

    // Список полей по виду документа
    /*
    1. url - Запрос
    2. id - Номер документа
    3. short - Цифровое значения короткого поля (1 - да)
    */
    function getFieldsDocument (url, id, short) {
        return $.getJSON (url, function(data) {
            // Массив для полей в одной группе
            var groupFieldsArray = [];
            // Проверяем регистрация или редактирование документа
            var rowChild = data;
            // Переменная даты
            var valueDate = "";
            // Переменная поля
            var idField = "";
            if(id > 0) {rowChild = data.childFields;}
            for (var i in rowChild) {
                var row = rowChild[i];
                if (row.field.fieldType === "DATE") {
                    if (id > 0) {
                        idField = row.field.id;
                        if(row.field.valueDate !== "") {valueDate = formatDate(row.field.valueDate, 1);}
                    }
                    createInput ("#blockUp", "date", "blockDate",  "inputDate", "Введите дату", short, '<i class="fas fa-calendar-alt mr-2"></i>' + row.field.name, valueDate, row.field.fieldId, 1, idField, row.field.enabled, row.field.required, '', '');
                }
                if (row.field.fieldType === "TIME") {
                    if (id > 0) {
                        idField = row.field.id;
                        if(row.field.valueDate !== "") {valueDate = formatTime(row.field.valueDate);}
                    }
                    createInput ("#blockUp", "time", "blockTime",  "inputTime", "Введите время", short, '<i class="fas fa-clock mr-2"></i>' + row.field.name, valueDate, row.field.fieldId, 1, idField, row.field.enabled, row.field.required, '', '');
                }
                if (row.field.fieldType === "GROUP_FIELDS") {
                    var groupFieldsElement = {"field" : row.field}
                    groupFieldsArray.push(groupFieldsElement);
                }
            }
            //console.log(groupFieldsArray);
            for (var key in groupFieldsArray) {
                //console.log(groupFieldsArray);
                key = parseInt(key);
                var rowFields = groupFieldsArray[key];
                var dubKey = parseInt(key+1);
                var delButton = ' d-none';
                // переменная счёта полей
                var blocKey = '';
                if(key != 0) {
                    blocKey = key;
                    delButton = '';
                }
                if(id > 0) {idField = ' data-id="' + rowFields.field.id + '"';}
                // Добавляем вопрос
                $("#newBlockGroup").append('<div class="row card mb-3 blockGroup" id="blockGroup' + blocKey + '" data-field="' + key + '"' + idField + '><div class="col-12"><div class="card-body"><div class="row"><div class="col-md-9 text-left"><h6 id="nameGroup' + blocKey + '">Вопрос ' + dubKey + '</h6></div><div class="col-md-3 text-right"><div id="delGroup' + blocKey + '" class="btn btn-danger btn-sm pointer delGroup rounded' + delButton + '" title="Удалить вопрос"><i class="fas fa-trash"></i></div></div></div><hr><div class="row"><div class="col-12 blockGroupFields" data-block="1"></div></div></div></div></div>');
                $(".blockName").html(rowFields.field.name).attr("data-block", rowFields.field.fieldId);
                if(rowFields.field.enabled == false) {$(".addGroup").remove();}
                for (var y in rowFields.field.childFields) {
                    //console.log(rowFields.field.childFields);
                    var rowSelectField = rowFields.field.childFields[y];
                    // Есть ли родитель у блока
                    var parentBlock = '';
                    var parentCatalog = '';
                    if(rowSelectField.parentCatalogId > 0/* && !id*/) {
                        //var parentBlockId = $("#blockGroup" + blocKey).attr("data-field");
                        parentCatalog = ' p' + rowSelectField.parentCatalogId;
                        parentBlock = ' d-none';
                        //console.log(parentBlockId);
                    }
                    var numberField = '';
                    if(id > 0) {
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
                        var selectFieldName = 'select' + blocKey + 'Field' + rowSelectField.catalogId;
                        // Добавляем строку
                        $('#blockGroup' + blocKey + ' .blockGroupFields').append('<div class="row blockRow' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><span class="text-muted">' + rowSelectField.name + '</span></div><div class="col-md-9 mt-3"><select class="browser-default custom-select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-field="' + rowSelectField.fieldId + '"' + idField + enaOpiton + required + '><option value="" class="alert-primary" selected>Выберите значение справочника</option></select></div></div>');
                        var numberCatalog = ('#blockGroup' + blocKey + ' #select' + blocKey + 'Field' + rowSelectField.catalogId);
                        //console.log(numberCatalog);
                        $('#blockGroup' + blocKey + ' #'+selectFieldName).on('change', function () {
                            //console.log('#blockGroup' + blocKey + ' #'+selectFieldName);
                            var numberSelectField = $(this).val();
                            var idParent = $(this).attr("name");
                            idParent = idParent.substr(11);
                            $(".p" + idParent).each(function () {
                                $(this).removeClass('d-none');
                                $(this).find("select").each(function () {
                                    var tempCatalogField = $(this).attr("id");
                                    var numberCatalogField = tempCatalogField.substr(11);
                                    var nameCatalogField = '#' + tempCatalogField;
                                    //var idParentField =  $(idParentSearch).attr("data-row");
                                    //var idParentBlock = $("[data-row = " + idParentField + "]");
                                    //console.log(idParentBlock);
                                    // Количество опций по запросу
                                    sumOptions ("rest/profile/catalogs/" + numberCatalogField + "/elems/parent/" + numberSelectField, nameCatalogField);
                                    /*var idParentSearch = $(this).parents(".blockRow");
                                    var idParentField =  $(idParentSearch).attr("data-option");
                                    console.log(idParentField);*/
                                    // Открываем опции
                                    createOptions ("rest/profile/catalogs/" + numberCatalogField + "/elems/parent/" + numberSelectField, nameCatalogField, "valueStr", "id", "");
                                    //var sumOption = $(nameCatalogField).parents(".blockRow").attr("data-option");
                                    //console.log(nameCatalogField + ' -- ' + sumOption);
                                    $(this).find("option.active").remove();
                                    /*if(sumOption > 0) {
                                        $(this).removeClass('d-none');
                                    } else {
                                        $(this).addClass('d-none');
                                    }*/
                                });
                                //
                                //console.log(idParentBlock);
                                //changeSelect(tempCatalogField, numberSelectField);
                            });
                        });
                        if(parentBlock == '') {
                            // Добавляем опции
                            createOptions ("rest/profile/catalogs/" + rowSelectField.catalogId + "/elems", numberCatalog, "valueStr", "id", numberField);
                        }
                    }
                    if (rowSelectField.fieldType === "ATTACHMENT") {
                        // Добавляем строку
                        if(parentBlock == '') {
                            $('#blockGroup' + blocKey + ' .blockGroupFields').append('<div class="row blockRow' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><span class="text-muted"><i class="fas fa-file-download mr-2"></i>' + rowSelectField.name + '</span></div><div class="col-md-9 mt-3"></div></div>');
                            createInput(".col-md-9:last", "file", "inputFile", "inputFile", "Загрузить файл", 0, '' + rowSelectField.name, rowSelectField.valueStr, rowSelectField.fieldId, 0, idField, rowSelectField.enabled, rowSelectField.required, 1, '');
                        }
                    }
                    if (rowSelectField.fieldType === "TEXTAREA") {
                        // Добавляем строку
                        if(parentBlock == '') {
                            $('#blockGroup' + blocKey + ' .blockGroupFields').append('<div class="row blockRow' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><span class="text-muted"><i class="fas fa-file-download mr-2"></i>' + rowSelectField.name + '</span></div><div class="col-md-9 mt-3"><textarea></textarea></div></div>');
                        }
                    }
                    if (rowSelectField.fieldType === "TEXT") {
                        // Добавляем строку
                        if(parentBlock == '') {
                            $('#blockGroup' + blocKey + ' .blockGroupFields').append('<div class="row blockRow' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><span class="text-muted"><i class="fas fa-file mr-2"></i>' + rowSelectField.name + '</span></div><div class="col-md-9 mt-3"></div></div>');
                            createInput(".col-md-9:last", "text", "", "inputText", "Введите значение", 0, '' + rowSelectField.name, rowSelectField.valueStr, rowSelectField.fieldId, 0, idField, rowSelectField.enabled, rowSelectField.required, '', 1);
                        }
                    }
                }
            }
        })
            .done(function(response) {
                if (response.length == 0) {$("#blockUp, #blockDown, #btnSave").addClass("d-none");}
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
                if (attrVal != '') {
                    var value = attrVal + "T00:00:00";
                    dataDate = attrVal;
                } else {var value = null;}
            }
            if (attrElem === "time") {if (attrVal != '') {var value = "1900-01-01" + "T" + attrVal + ":00";} else {var value = null;}}
            if(id > 0) {idField = parseInt($(this).attr("data-id"));}
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
                if(i !== 0) {elementBlock = elementBlock + i;}
                var elementArray = [];
                $(elementBlock + ' [data-field]').each(function() {
                    if(id > 0) {idField = parseInt($(this).attr("data-id"));}
                    var elementBlockElem = {
                        "id" : idField,
                        "childFields" : [],
                        "fieldId" : parseInt($(this).attr("data-field")),
                        "valueInt" : parseInt($(this).val())
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
                        item.id = item.projectRegNum;
                        item.projectRegNum = formatDate(item.insertDateTime, 0);
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