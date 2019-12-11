    // Получение верхнего стека полей
    function getUpFields(url, id, short, block) {
        var filed = '#blockUp';
        var upElem = 'upElem';
        var inputDate = 'inputDate';
        var inputTime = 'inputTime';
        var blockDate = 'blockDate';
        var blockTime = 'blockTime';
        var up = 1;
        if(block && block === 1) {
            filed = '#blockUpNew';
            upElem = 'upElemNew';
            inputDate = 'inputDateNew';
            inputTime = 'inputTimeNew';
            blockDate = 'blockDateNew';
            blockTime = 'blockTimeNew';
            up = 2;
        }
        return $.getJSON (url, function(data) {
            var rowChild = data;
            if(id > 0 && block !== 1) {rowChild = data.childFields;}
            for(var i in rowChild) {
                var row = rowChild[i];
                // Переменная даты
                var valueDate = '';
                // Переменная поля
                var idField = '';
                var enaOpiton = '';
                var numberField = '';
                if(id > 0) {
                    //idField = ' data-id="' + row.field.id + '"';
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
                    createInput (filed, "date", blockDate,  inputDate, "Введите дату", short, '<i class="fas fa-calendar-alt mr-2"></i>'+row.field.name, valueDate, row.field.fieldId, up, idField, row.field.enabled, row.field.required, '', '');
                }
                if (row.field.fieldType === "TIME") {
                    if (id > 0) {
                        idField = row.field.id;
                        if(row.field.valueDate !== '') {valueDate = formatTime(row.field.valueDate);}
                    }
                    createInput (filed, "time", blockTime,  inputTime, "Введите время", short, '<i class="fas fa-clock mr-2"></i>'+row.field.name, valueDate, row.field.fieldId, up, idField, row.field.enabled, row.field.required, '', '');
                }
                if (row.field.fieldType === "GROUP_CHECKBOX") {
                    var valueInt = 'value="0"';
                    var checekCheckBox = '';
                    idField = row.field.fieldId;
                    if (row.field.valueInt > 0) {
                        valueInt = 'value="'+row.field.valueInt+'"';
                        checekCheckBox = ' checked';
                    }
                    /*id="'+row.field.tag+'Block"*/
                    $(filed).append('<div class="row my-3">' +
                        '<div class="col-md-12 text-left"><div class="form-check">' +
                        '<input type="checkbox" class="form-check-input ' + upElem + '"' +
                        ' id="'+row.field.tag+'" data-field="'+idField+'" name="'+row.field.tag+'" '+valueInt+checekCheckBox+'>' +
                        '<label class="form-check-label text-muted text-left"' +
                        ' for="'+row.field.tag+'">'+row.field.name+'</label>' +
                        '</div></div></div>'
                    );
                    if(row.field.childFields.length > 0) {
                        $(filed).append('<div id="'+row.field.tag+'BlockDiv" class="childBox d-none"></div>');
                        for(var y in row.field.childFields) {
                            var checkField = row.field.childFields[y];
                            idField = null;
                            if (id > 0) {idField = checkField.id;}
                            var elementField = filed+' #'+row.field.tag+'BlockDiv';
                            var textId = y+1;
                            if (checkField.fieldType === "TEXT") {
                                var nameText = "checkText_"+textId;
                                createInput(elementField, "text", nameText, nameText, "Введите значение", short, checkField.name, checkField.valueStr, checkField.fieldId, '', idField, checkField.enabled, checkField.required, '', '', 'checkClass');
                            }
                            if (checkField.fieldType === "TEXTAREA") {
                                var nameTextarea = "textarea_"+textId;
                                var textareaId = '';
                                var textValue = '';
                                if(checkField.id > 0) {
                                    textareaId = ' data-id="'+checkField.id+'"';
                                    textValue = checkField.valueStr;
                                }
                                $(elementField).append('<div class="row ml-1 mb-3">' +
                                    '<div class="col-md-3 text-left mt-3">' +
                                    '<div class="text-muted">'+checkField.name+requiredSup+'</div></div>' +
                                    '<div class="col-md-9 mt-3">' +
                                    '<textarea type="text" id="'+nameTextarea+'" class="checkClass form-control" data-field="'+checkField.fieldId+'"'+textareaId+'>'+textValue+'</textarea>'
                                    +requiredValidate+'</div></div>');
                            }
                            textId = textId+1;
                        }
                    }
                    checkedFields ('#'+row.field.tag, '#'+row.field.tag+'BlockDiv');
                    if (row.field.valueInt > 0) {$('.childBox').removeClass('d-none');}
                }
                var textId = i+1;
                if (row.field.fieldType === "TEXT") {
                    var textName = '_';
                    if (id > 0) {
                        idField = row.field.id;
                        textName = row.field.id+'_';
                    }
                    var nameText = "inputText_"+textName + textId;
                    createInput(filed, "text", nameText, nameText, "Введите значение", short, row.field.name, row.field.valueStr, row.field.fieldId, up, idField, row.field.enabled, row.field.required, '', '');
                    textId = textId+1;
                }
                // Если вид поля выборка
                var parentBlock = '';
                var parentCatalog = '';
                if(row.field.parentCatalogId > 0) {
                    parentCatalog = ' p'+row.field.parentCatalogId;
                    parentBlock = ' d-none';
                }
                if(id > 0) {
                    if(row.field.parentCatalogId > 0) {
                        parentCatalog = ' p'+row.field.parentCatalogId;
                        if(row.field.valueInt && row.field.valueInt > 0) {
                            parentBlock = '';
                        } else {
                            parentBlock = ' d-none';
                        }
                    }
                }
                if (row.field.fieldType === "CATALOG") {
                    if(id > 0) {idField = ' data-id="' + row.field.id + '"';}
                    var numberCatalog = ('#'+selectFieldName);
                    // Добавляем строку
                    $(filed).append('<div class="row blockRowUp ' + upElem + parentBlock + parentCatalog + ' ml-1 mb-3"><div class="col-md-3 text-left mt-3"><div' +
                        ' class="text-muted">' + row.field.name + requiredSup + '</div></div><div class="col-md-9"><select data-placeholder="Выберите вид документа" class="chosen-select" searchable=" Поиск" type="select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-catalog="' + row.field.catalogId + '" data-field="' + row.field.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select>' + requiredValidate + '</div></div>');
                    $(numberCatalog).chosen({
                        width: "100%",
                        no_results_text: "Ничего не найдено!"
                    });
                    // Формирование правильных полей
                    createOptionsValue(numberCatalog, filed, '.blockRowUp');
                    if(parentBlock == '') {
                        // Добавляем опции
                        createOptions("rest/profile/catalogs/" + row.field.catalogId + "/elems", numberCatalog, "valueStr", "id", numberField, '');
                    }
                }
                // Если вид поля справочник организаций
                if (row.field.fieldType === "CATALOG_ORGANIZATIONS") {
                    var numberCatalog = ('#'+selectFieldName);
                    // Добавляем строку
                    $(filed).append('<div class="row blockRowUp '+ upElem + parentBlock + parentCatalog + ' ml-1 mb-3 d-flex align-items-center"><div class="col-md-3 text-left mt-3"><div class="text-muted">' + row.field.name + requiredSup + '</div></div><div class="col-md-8 mt-3"><select data-placeholder="Выберите вид документа" class="chosen-select" type="select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-catalog="' + row.field.catalogId + '" data-field="' + row.field.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select></div><div class="col-md-1 mt-3 text-right"><button class="btn btn-primary btn-sm addElement rounded m-0 px-3 waves-effect" data-toggle="modal" data-target="#addElement" data-catalog="' + numberCatalog + '" type="button" title="Добавить организацию" ' + enaOpiton + '><i class="fas fa-plus white-text"></i></button></div><div class="col-md-3"></div><div class="col-9">' + requiredValidate + '</div></div>');
                    $(numberCatalog).chosen({
                        width: "100%",
                        no_results_text: "Ничего не найдено!"
                    });
                    // Добавляем опции при нажатии поля
                    createOptions ("rest/profile/organizations/", numberCatalog, "shortNameLf", "id", numberField, '');
                }
                // Если вид поля справочник организаций
                if (row.field.fieldType === "CATALOG_REGNUMBERS") {
                    if(id > 0) {idField = ' data-id="'+row.field.id+'"';}
                    // Добавляем строку
                    $(filed).append('<div class="row ' + upElem + ' ml-1 mb-3' +
                        ' d-flex align-items-center justify-content-center"><div' +
                        ' class="col-md-3 text-left mt-3"><div class="text-muted">'+row.field.name+requiredSup+'</div></div><div class="col-md-9"><select data-placeholder="Выберите вид документа" class="chosen-select" searchable=" Поиск" type="select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-field="' + row.field.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select>' + requiredValidate + '</div></div>');
                    var numberCatalog = ('#'+selectFieldName);
                    $(numberCatalog).chosen({
                        width: "100%",
                        no_results_text: "Ничего не найдено!"
                    });
                    // Добавляем опции
                    createOptions ("rest/profile/docs/regnumbers/", numberCatalog, "regNum", "id", numberField, '');
                }
                // Если вид поля HTML таблицы
                if (row.field.fieldType === "CATALOG_HTML_TABLES") {
                    if(id > 0) {
                        $(filed).append('' +
                            '<div class="row ml-1 mb-3 d-flex' +
                            ' align-items-center justify-content-center"' +
                            ' id="catalogTables" data-id="'+row.field.id+'" type="tableHtml"' +
                            ' data-field="'+row.field.fieldId+'" data-value="'+row.field.valueInt+'">' +
                            '   <div class="col-md-12 text-left newTable">' +
                            '       <div class="btn btn-primary btn-sm rounded" id="tableTemplates" data-click="">Изменить макет таблицы</div>' +
                            '       <div class="editTable">' +
                            '           <h6 class="my-3 text-center"></h6>' +
                            '           ' + row.field.valueStr +
                            '       </div>' +
                            '   </div>' +
                            '</div>');
                        var sumTop = parseInt($('.newTable table tbody tr:first td:last').html());
                        sumTop = sumTop+1;
                        $('.newTable table thead tr:first').append('<th' +
                            ' class="text-center deleteElem" rowspan="2">Удалить</th>');
                        $('.newTable table tbody tr:first').append('<td' +
                            ' class="text-center deleteElem">'+sumTop+'</td>');
                        $('.newTable table tbody tr:not(:first)').append('' +
                            '<td class="deleteElem">' +
                            '   <div class="btn btn-sm btn-danger table-remove rounded px-3 my-0">' +
                            '       <i class="fas fa-trash"></i>' +
                            '   </div>' +
                            '</td>');
                        $('.newTable').append('' +
                            '<div class="row">' +
                            '   <div class="col-12 text-right">' +
                            '       <div class="btn btn-primary btn-sm rounded" id="addTableRow">' +
                            '           <i class="fas fa-plus white-text mr-2"></i>Добавить строку' +
                            '       </div>' +
                            '   </div>' +
                            '</div>');
                        $('.newTable table th').css({
                            'text-align': 'center',
                            'vertical-align': 'middle',
                            'font-weight': 'bold'
                        });
                        $('.newTable table td').css('text-align', 'left');
                        $('.newTable table tbody tr:first td').css('text-align', 'center');
                        $('.newTable table tbody tr').each(function() {
                           $('td:first', this).css('text-align', 'center');
                        });
                    } else {
                        $(filed).append('' +
                            '<div class="row ml-1 mb-3 d-flex' +
                            ' align-items-center justify-content-center"' +
                            ' id="catalogTables" type="tableHtml"' +
                            ' data-field="'+row.field.fieldId+' data-value="">' +
                            '   <div class="col-md-3 text-left mt-3">' +
                            '       <div class="text-muted">'+row.field.name+requiredSup+'</div>' +
                            '   </div>' +
                            '   <div class="col-md-9 text-left newTable">' +
                            '       <div class="btn btn-primary btn-sm rounded" id="tableTemplates" data-click="">Создать таблицу</div>' +
                            '   </div>' +
                            '</div>');
                    }
                }
            }
        }).done(function(response) {
            var filedBlock = '#blockFields, #blockUp, #blockDown, #btnSave, #btnWordFile';
            if(block && block === 1) {filedBlock = '#blockFieldsNew, #blockUpNew, #blockDownNew, #btnSaveNew, #btnWordFileNew';}
            if (response.length == 0) {$(filedBlock).addClass('d-none');}
        });
    }

    // Получение нижнего стека полей
    function getDownFields(url, id, number, block, name) {
        var blockGroup = '#blockGroup';
        var idBlockGroup = 'blockGroup';
        var newBlockGroup = '#newBlockGroup';
        var blockName = '.blockName';
        var delGroup = 'delGroup';
        var cloneGroup = 'cloneGroup';
        var dataBlock = 1;
        var nameGroup = 'nameGroup';
        var addGroup = '.addGroup';
        if(block && block === 1) {
            blockGroup = '#blockGroupNew';
            idBlockGroup = 'blockGroupNew';
            newBlockGroup = '#newBlockGroupNew';
            blockName = '.blockNameNew';
            delGroup = 'delGroupNew';
            cloneGroup = 'cloneGroupNew';
            dataBlock = 2;
            nameGroup = 'nameGroupNew';
            addGroup = '.addGroupNew';
        }
        return $.getJSON (url, function(data) {
            var rowChild = data;
            if(id > 0 && block !== 1) {rowChild = data.childFields;}
            if(id > 0) {number = 1;}
            var idField = '';
            var idFiledInput = '';
            for(var key in rowChild) {
                var row = rowChild[key];
                if (row.field.fieldType === "GROUP_FIELDS") {
                    $('#blockDown').removeClass('d-none');
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
                    var cloneButton = ' d-none';
                    //var cloneButton = '';
                    // Кнопка удаления полей
                    if(id > 0) {
                        idField = ' data-id="' + rowFields.field.id + '"';
                        idFiledInput = rowFields.field.id;
                        if(number > 1) {
                            delButton = '';
                            //cloneButton = '';
                        }
                    } else {
                        if(number != '') {
                            delButton = '';
                            //cloneButton = '';
                        }
                    }
                    // Название блока
                    var blockNameVal = 'Блок ' + dubKey;
                    if(name) {
                        blockNameVal = 'Блок ' + name;
                    }
                    $(newBlockGroup).append('<div class="row card mb-3 ' + idBlockGroup + '" id="' + idBlockGroup + dubKey + '" data-field="' + dubKey + '"' + idField + '><div class="col-12"><div class="card-body"><div class="row"><div class="col-md-9 text-left"><h5 class="' + nameGroup + '">' + blockNameVal + '</h5></div><div class="col-md-3 text-right"><div id="' + cloneGroup + dubKey + '" class="btn btn-mdb-color btn-sm cloneGroup rounded' + cloneButton + '" title="Дублировать блок"><i class="fas fa-copy"></i></div><div id="' + delGroup + dubKey + '" data-toggle="modal" data-target="#deleteBlock" class="btn btn-danger btn-sm delGroup rounded' + delButton + ' ml-3" title="Удалить блок"><i class="fas fa-trash"></i></div></div></div><hr><div class="row"><div class="col-12 blockGroupFields" data-block="'+dataBlock+'"></div></div></div></div></div>');
                    $(blockName).html(rowFields.field.name).attr("data-block", rowFields.field.fieldId);
                    if(row.field.enabled === false) {$(addGroup + ', .' + nameGroup).addClass('d-none');}
                    if(row.field.enabled === true) {$(addGroup + ', .' + nameGroup).removeClass('d-none');}
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
                            idFiledInput = rowSelectField.id;
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
                        var selectFieldName = 'selectField_' + dubKey + '_' + dataBlock +/*rowSelectField.catalogId*/ y;
                        // Если вид поля SELECT
                        if (rowSelectField.fieldType === "CATALOG") {
                            // Добавляем строку
                            $(blockGroup + dubKey + ' .blockGroupFields').append('<div class="row blockRow' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3 d-flex align-items-center"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9 mt-3"><select data-placeholder="Выберите вид документа" class="chosen-select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-catalog="' + rowSelectField.catalogId + '" data-field="' + rowSelectField.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select>' + requiredValidate + '</div></div>');
                            var numberCatalog = ('#' + selectFieldName);
                            $(numberCatalog).chosen({
                                width: "100%",
                                no_results_text: "Ничего не найдено!"
                            });
                            // Формирование правильных полей
                            createOptionsValue(numberCatalog, blockGroup + dubKey, '.blockRow');
                            if(parentBlock == '') {
                                // Добавляем опции
                                createOptions("rest/profile/catalogs/" + rowSelectField.catalogId + "/elems", numberCatalog, "valueStr", "id", numberField, "");
                            }
                        }
                        // Если вид поля справочник организаций
                        if (rowSelectField.fieldType === "CATALOG_ORGANIZATIONS") {
                            var numberCatalog = ('#' + selectFieldName);
                            // Добавляем строку
                            $(blockGroup + dubKey + ' .blockGroupFields').append('<div class="row blockRow d-flex align-items-center' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-8 mt-3"><select data-placeholder="Выберите вид документа" class="chosen-select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-catalog="' + rowSelectField.catalogId + '" data-field="' + rowSelectField.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select></div><div class="col-md-1 mt-3 text-right"><button class="btn btn-primary btn-sm addElement rounded m-0 px-3 waves-effect" data-toggle="modal" data-target="#addElement" data-catalog="' + numberCatalog + '" type="button" title="Добавить организацию" ' + enaOpiton + '><i class="fas fa-plus white-text"></i></button></div><div class="col-md-3"></div><div class="col-9">' + requiredValidate + '</div></div>');
                            $(numberCatalog).chosen({
                                width: "100%",
                                no_results_text: "Ничего не найдено!"
                            });
                            // Добавляем опции при нажатии поля
                            createOptions ("rest/profile/organizations/", numberCatalog, "shortNameLf", "id", numberField, '');
                        }
                        // Если вид поля справочник пользователей
                        if (rowSelectField.fieldType === "CATALOG_USERS") {
                            var numberCatalog = ('#' + selectFieldName);
                            // Добавляем строку
                            $(blockGroup + dubKey + ' .blockGroupFields').append('<div class="row blockRow d-flex align-items-center' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9 mt-3"><select data-placeholder="Выберите вид документа" class="chosen-select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-catalog="' + rowSelectField.catalogId + '" data-field="' + rowSelectField.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select>' + requiredValidate + '</div></div>');
                            $(numberCatalog).chosen({
                                width: "100%",
                                no_results_text: "Ничего не найдено!"
                            });
                            // Добавляем опции
                            createOptions ("rest/profile/users/", numberCatalog, '', 'id', numberField, 'users');
                        }
                        if (rowSelectField.fieldType === "ATTACHMENT") {
                            // Добавляем строку
                            if(parentBlock == '') {
                                var attachment = 1;
                                if(id && id > 0) {attachment = 2;}
                                $(blockGroup + dubKey + ' .blockGroupFields').append('<div class="row blockRow d-flex align-items-center' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9"></div></div>');
                                createInput(".col-md-9:last", "file", 'inputFile', 'inputFile', "Загрузить файл", 0, '' + rowSelectField.name, rowSelectField.valueStr, rowSelectField.fieldId, 0, idFiledInput, rowSelectField.enabled, rowSelectField.required, attachment, '');
                                // Подсказки
                            }
                        }
                        if (rowSelectField.fieldType === "TEXTAREA") {
                            // Добавляем строку
                            if(parentBlock == '') {
                                $(blockGroup + dubKey + ' .blockGroupFields').append('<div class="row blockRow' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9 mt-3"><textarea></textarea>' + requiredValidate + '</div></div>');
                            }
                        }
                        if (rowSelectField.fieldType === "TEXT") {
                            // Добавляем строку
                            if(parentBlock == '') {
                                var textName = '';
                                var textId = y+1;
                                if(id > 0) {textName = rowSelectField.id + '_';}
                                var nameText = "inputText_" + dubKey + '_' + textName + textId;
                                $(blockGroup + dubKey + ' .blockGroupFields').append('<div class="row blockRow d-flex align-items-center' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9 mt-3"></div></div>');
                                createInput(".col-md-9:last", "text", nameText, nameText, "Введите значение", 0, '' + rowSelectField.name, rowSelectField.valueStr, rowSelectField.fieldId, 0, idFiledInput, rowSelectField.enabled, rowSelectField.required, '', 1);
                                textId = textId+1;
                            }
                        }
                    }
                    if(id > 0) {
                        number = number+1;
                    }
                } else {
                    $('#blockDown').addClass('d-none');
                }
            }
        }).done(function(response) {
            var filedBlock = '#blockFields, #blockUp, #blockDown, #btnSave, #btnWordFile';
            if(block && block === 1) {filedBlock = '#blockFieldsNew, #blockUpNew, #blockDownNew, #btnSaveNew, #btnWordFileNew';}
            if (response.length == 0) {$(filedBlock).addClass("d-none");}
        });
    }

    // Формирование массива элементов для JSON поля вне GROUP_FIELDS
    function createDataField (id, block) {
        var upElem = '.upElem';
        if(block && block === 1) {
            upElem = '.upElemNew';
        }
        var id = parseInt(id);
        var dataField = [];
        var field = '';
        var idField = null;
        var dataDate = '';
        var valueData = '';
        $(upElem).each(function(i) {
            var key = i+1;
            var attrElem = $(this).attr("type");
            var attrVal = $(this).val();
            var attrId = parseInt($(this).attr("data-field"));
            var attrSelect = $('.chosen-select', this).attr("type");
            var attrSelectId = parseInt($('.chosen-select', this).attr("data-field"));
            var attrSelectVal = parseInt($('.chosen-select', this).val());
            var value = null;
            var valueInt = null;
            var valueStr = null;
            if (attrElem === "date") {
                valueData = 1;
                if (attrVal != '') {
                    value = attrVal + "T00:00:00";
                    dataDate = attrVal;
                }
            }
            if (attrElem === "time") {
                valueData = 1;
                if (attrVal != '') {value = "1900-01-01" + "T" + attrVal + ":00";}
            }
            if (attrElem === "text") {
                valueData = 2;
                if (attrVal != '') {value = attrVal;}
            }
            if (attrSelect === "select") {
                valueData = 3;
                if (attrSelectVal > 0) {value = attrSelectVal;}
            }
            if (attrElem === "checkbox") {
                valueData = 4;
                if (attrVal > 0) {value = attrVal;}
            }
            if (attrElem === "tableHtml") {
                valueData = 5;
                valueInt = $('#catalogTables').attr('data-value');
                //$('.editTable table td').trim();
                $('.editTable .deleteElem, .editTable h6').remove();
                valueStr = $('.editTable').html();
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
                    "position": key
                }
            } else if (valueData === 2) {
                field = {
                    "field": {
                        "id" : idField,
                        "childFields": [],
                        "fieldId": attrId,
                        "valueStr" : value
                    },
                    "position": key
                }
            } else if (valueData === 3) {
                if(id > 0) {idField = parseInt($('.chosen-select', this).attr("data-id"));}

                field = {
                    "field": {
                        "id" : idField,
                        "childFields": [],
                        "fieldId": attrSelectId,
                        "valueInt" : value
                    },
                    "position": key
                }
            } else if (valueData === 4) {
                if(id > 0) {idField = parseInt($(this).attr("data-id"));}
                var childBox = [];
                var childField = '';
                $('.childBox .checkClass').each(function() {
                    var childFieldId = null;
                    var childFieldVal = $(this).val();
                    var childFieldField = parseInt($(this).attr("data-field"));
                    if (id > 0) {childFieldId = parseInt($(this).attr("data-id"));}
                    childField = {
                        "id" : childFieldId,
                        "childFields" : [],
                        "fieldId" : childFieldField,
                        "valueStr" : childFieldVal
                    }
                    childBox.push(childField);
                });
                field = {
                    "field": {
                        "id" : idField,
                        "childFields": childBox,
                        "fieldId": attrId,
                        "valueInt" : value
                    },
                    "position": key
                }
            } else if (valueData === 5) {
                field = {
                    "field": {
                        "id" : idField,
                        "childFields": [],
                        "fieldId": attrId,
                        "valueInt" : valueInt,
                        "valueStr" : valueStr
                    },
                    "position": key
                }
            }
            //console.log(field);
            dataField.push(field);
        });
        return dataField;
    }

    function createDataBlock (id, key, block) {
        //alert(key);
        var blockGroup = '.blockGroup';
        if(block && block === 1) {
            blockGroup = '.blockGroupNew';
        }
        var id = parseInt(id);
        var idField = null;
        var dataBlock = [];
        $('.BlockDiv').each(function() {
            var name = $(this).attr('id');
            $('#'+name+' .blockField '+blockGroup).each(function(item) {
                var i = parseInt(item)+1;
                if($(this).attr("data-field") == i) {
                    var elementBlock = '#'+name+' '+blockGroup+' .blockGroupFields .blockRow';
                    var blockName = $(this).attr('id');
                    //if(i !== 0) {elementBlock = elementBlock+i;}
                    var elementArray = [];
                    $('#'+blockName+' [data-field]').each(function() {
                        //alert(elementBlock);
                        var typeAttr = $(this).attr("type");
                        if(id > 0) {idField = parseInt($(this).attr("data-id"));}
                        if (typeAttr && typeAttr != '') {
                            if (typeAttr === "tableHtml") {
                                var valueInt = $(this).attr('data-value');
                                var tabId = '#'+$(this).attr('id');
                                $(tabId+' .editTable .deleteElem, '+tabId+' .tableHtml .editTable h6').remove();
                                var valueStr = $(tabId+' .editTable').html();
                                var elementBlockElem = {
                                    "id" : idField,
                                    "childFields" : [],
                                    "fieldId" : parseInt($(this).attr("data-field")),
                                    "valueInt": valueInt,
                                    "valueStr" : valueStr
                                }
                            } else if(typeAttr === "select") {
                                var elementBlockElem = {
                                    "id" : idField,
                                    "childFields" : [],
                                    "fieldId" : parseInt($(this).attr("data-field")),
                                    "valueInt" : parseInt($(this).val())
                                }
                            } else if(typeAttr === "text") {
                                var elementBlockElem = {
                                    "id" : idField,
                                    "childFields" : [],
                                    "fieldId" : parseInt($(this).attr("data-field")),
                                    "valueStr" : $(this).val()
                                }
                            }
                        } else {
                            var elementBlockElem = {
                                "id" : idField,
                                "childFields" : [],
                                "fieldId" : parseInt($(this).attr("data-field")),
                                "valueStr" : $(this).val()
                            }
                        }
                        elementArray.push(elementBlockElem);
                    });
                    var position = parseInt(key)+i;
                    var fieldId = parseInt($('#'+blockName).attr("data-div"));
                    if(id > 0) {idField = parseInt($('#'+blockName).attr("data-id"));}
                    var dataBlockElement = {
                        "field" : {
                            "id" : idField,
                            "childFields": elementArray,
                            "fieldId" : fieldId,
                        },
                        "position" : position
                    }
                } else {
                    alert($(this).attr("data-field")+' - '+i);
                }
                dataBlock.push(dataBlockElement);
            });
        });
        return dataBlock;
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

    // Формирование объекта JSON для отправки на сервер
    function createJSON (id,dataType,dataField,dataBlock,block) {
        var id = parseInt(id);
        if(id === 0) {id = null;}
        var childFields = [];
        var executorDepartmentsIds = [];
        var whomList = '#whomList';
        var userListBlock = '#userListBlock';
        if(block && block === 1) {
            whomList = '#whomListNew';
            userListBlock = '#userListBlockNew';
        }
        var finalUserId =  $(userListBlock + ' select:last').val();
        var whomList = ChosenOrder.getSelectionOrder($(whomList).get(0));
        for (var i = 0; i < whomList.length; i++) {
            var element = whomList[i];
            if(element !== '') {
                executorDepartmentsIds.push(element);
            }
        }
        if(block && block === 2) {
            finalUserId =  $('#userListBlockDiv .row:last').attr('data-value');
            $('#whomList div').each(function() {
                var element = $(this).attr('data-value');
                if(element !== '') {
                    executorDepartmentsIds.push(element);
                }
            });
        }
        if(dataField !== "") {for (var key in dataField) {childFields.push(dataField[key]);}}
        if(dataBlock !== "") {for (var key in dataBlock) {childFields.push(dataBlock[key]);}}
        if(block && block === 2)  {
            var comment = $('#commentText textarea').val();
            var valueObj = {
                "id" : id,
                "docTypeId" : parseInt(dataType),
                "executorDepartmentsIds" : executorDepartmentsIds,
                "finalUserId" : finalUserId,
                "comment" : comment,
                "childFields" : childFields
            }
        } else if(block && block === 1)  {
            var valueObj = {
                "id" : null,
                "docTypeId" : parseInt(dataType),
                "executorDepartmentsIds" : executorDepartmentsIds,
                "finalUserId" : finalUserId,
                "parentDocId" : id,
                "childFields" : childFields
            }
        } else {
            var valueObj = {
                "id" : id,
                "docTypeId" : parseInt(dataType),
                "executorDepartmentsIds" : executorDepartmentsIds,
                "finalUserId" : finalUserId,
                "childFields" : childFields
            }
        }
        return valueObj;
    }

    // Получение данных об управлении по id
    function getDepartments (url) {
        $.ajax({
            url: url,
            dataType: 'json',
            async: false,
            //data: myData,
            success: function(data) {
                $('#whomList').append('<div class="d-inline-block chip light-blue lighten-2 white-text my-1 mr-2" data-value="' + data.id + '">' + data.name + '</div>');
            }
        });
        /*return $.getJSON(url, function(data) {
            $('#whomList').append('<div class="d-inline-block chip light-blue lighten-2 white-text my-1 mr-2" data-value="' + data.id + '">' + data.name + '</div>');
        });*/
    }

    // Формирование списка управлений без фозможности редактирования
    function createWhomListDisabled (url) {
        for(var i in url) {
            var row = url[i];
            getDepartments('rest/profile/departments/'+row);
        }
    }

    // Формирование списка пользователей без возможности редактирования
    function createUserListDisabled (url, finalVersion) {
        return $.getJSON(url, function(data) {
            for(var i in data) {
                var row = data[i];
                var position = '';
                var comment = '';
                var agreedDateTime = '';
                var undoUser = '';
                if(row.agreedDateTime) {
                    var newDate = formatDate(row.agreedDateTime);
                    var newTime = formatTime(row.agreedDateTime);
                    agreedDateTime = newDate+'/'+newTime;
                }
                if(row.comment) {comment = row.comment;}
                if(row.position) {position = row.position;}
                var currentUser = '';
                if(row.currentUser === true) {
                    currentUser = '<i class="fas fa-user-clock text-warning" title="Текущий согласователь"></i>';
                    if(finalVersion !== 1) {
                        undoUser = '<button class="btn btn-primary btn-sm px-2 py-1 mx-3 btnReturn" type="button" data-undo="'+row.userId+'" title="Перенаправить на согласование"><i class="fas fa-undo-alt text-white"></i></button>';
                    }
                } else {
                    currentUser = '<i class="fas fa-ellipsis-h text-muted" title="Согласователь"></i>';
                }
                if(row.decisionType && row.decisionType === 'ACCEPTED') {
                    currentUser = '<i class="fas fa-check text-success" title="Согласование завершено"></i>';
                    if(row.finalUser === true) {
                        currentUser = '<i class="fas fa-check-circle text-success" title="Финальный согласователь"></i>';
                    }
                }
                if(row.decisionType && row.decisionType === 'REDIRECTED') {
                    currentUser = '<i class="fas fa-undo-alt text-primary" title="Перенаправление"></i>';
                }
                if(row.decisionType && row.decisionType === 'REJECTED') {
                    currentUser = '<i class="fas fa-times text-danger" title="Согалсование отменено"></i>';
                }
                $('#userListBlockDiv').append('<div class="row mb-3 d-flex align-items-center" data-value="'+row.userId+'"><div class="col-1 text-center">'+row.ordering+'</div><div class="col-1 text-center">'+currentUser+'</div><div class="col-4">'+row.fullName+undoUser+'<br><small class="text-muted">'+position+'</small></div><div class="col-3"><small>'+comment+'</small></div><div class="col-3"><small>'+agreedDateTime+'</small></div></div>');
            }
        });
    }