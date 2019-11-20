    // Получение стека из строки
    function getStack (url, pole) {
        $.getJSON (url, function(data) {
            var rowChild = data;
            if(pole && pole > 0) {rowChild = data[pole];}
            return rowChild;
        });
    }

    // Получение полей блока GROUP_FIELDS
    function groupNewFieldsValue (data, id, blockGroup, dubKey, dataBlock) {
        var idField = ''; var idFiledInput = '';
        for (var y in data) {
            var rowSelectField = data[y];
            // Получение полей

            // Есть ли родитель у блока
            var parentBlock = '';
            var parentCatalog = '';
            if (rowSelectField.parentCatalogId > 0) {
                parentCatalog = ' p'+rowSelectField.parentCatalogId;
                parentBlock = ' d-none';
            }
            var numberField = '';
            if (id > 0) {
                if (rowSelectField.parentCatalogId > 0) {
                    parentCatalog = ' p'+rowSelectField.parentCatalogId;
                    if (rowSelectField.valueInt && rowSelectField.valueInt > 0) {
                        parentBlock = '';
                    } else {
                        parentBlock = ' d-none';
                    }
                }
                idField = ' data-id="'+rowSelectField.id+'"';
                idFiledInput = rowSelectField.id;
                // Номер поля для отметки в селектах если нужно
                numberField = rowSelectField.valueInt;
            }
            var enaOpiton = '';
            if (rowSelectField.enabled == false) {
                enaOpiton = ' disabled';
            }
            var required = '';
            var requiredSup = '';
            var requiredValidate = '';
            if (rowSelectField.required == true) {
                required = ' required';
                requiredSup = '<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup>';
                requiredValidate = '<div class="invalid-feedback">Поле обязательно для заполнения</div>';
            }
            var selectFieldName = 'selectField_'+dubKey+'_'+dataBlock +y;
            // Если вид поля HTML таблицы
            if (rowSelectField.fieldType === "CATALOG_HTML_TABLES") {
                if(id > 0) {
                    $(blockGroup+dubKey+' .blockGroupFields').append('' +
                        '<div class="row ml-1 mb-3 d-flex' +
                        ' align-items-center justify-content-center"' +
                        ' id="catalogTables" data-id="'+rowSelectField.id+'" type="tableHtml"' +
                        ' data-field="'+rowSelectField.fieldId+'" data-value="'+rowSelectField.valueInt+'">' +
                        '   <div class="col-md-12 text-left newTable">' +
                        '       <div class="btn btn-primary btn-sm rounded" id="tableTemplates" data-click="">Изменить макет таблицы</div>' +
                        '       <div class="editTable">' +
                        '           <h6 class="my-3 text-center"></h6>' +
                        '           ' + rowSelectField.valueStr +
                        '       </div>' +
                        '   </div>' +
                        '</div>');
                    var sumTop = parseInt($('.newTable table tbody tr:first td:last').html());
                    sumTop = sumTop+1;
                    $('.newTable table thead tr:first').append('' +
                        '<th class="text-center deleteElem" rowspan="2">Удалить</th>');
                    $('.newTable table tbody tr:first').append('' +
                        '<td class="text-center deleteElem">'+sumTop+'</td>');
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
                    $(blockGroup+dubKey+' .blockGroupFields').append('' +
                        '<div class="row ml-1 mb-3 d-flex' +
                        ' align-items-center justify-content-center"' +
                        ' id="catalogTables" type="tableHtml"' +
                        ' data-field="'+rowSelectField.fieldId+' data-value="">' +
                        '   <div class="col-md-3 text-left mt-3">' +
                        '       <div class="text-muted">'+rowSelectField.name+requiredSup+'</div>' +
                        '   </div>' +
                        '   <div class="col-md-9 text-left newTable">' +
                        '       <div class="btn btn-primary btn-sm rounded" id="tableTemplates" data-click="">Создать таблицу</div>' +
                        '   </div>' +
                        '</div>');
                }
            }
            // Если вид поля SELECT
            if (rowSelectField.fieldType === "CATALOG") {
                // Добавляем строку
                $(blockGroup+dubKey+' .blockGroupFields').append('' +
                    '<div class="row blockRow'+parentBlock+parentCatalog+ '" data-row="'+y+'">' +
                    '   <div class="col-md-3 text-left mt-3 d-flex align-items-center">' +
                    '       <div class="text-muted">'+rowSelectField.name+requiredSup+'</div>' +
                    '   </div>' +
                    '   <div class="col-md-9 mt-3">' +
                    '       <select data-placeholder="Выберите вид документа" class="chosen-select" id="'+selectFieldName+'" name="'+selectFieldName+'" data-catalog="'+rowSelectField.catalogId+'" data-field="'+rowSelectField.fieldId+'"'+idField+enaOpiton+required+'>' +
                    '           <option value="">Выберите значение справочника</option>' +
                    '       </select>'+requiredValidate+'' +
                    '   </div>' +
                    '</div>');
                var numberCatalog = ('#'+selectFieldName);
                $(numberCatalog).chosen({
                    width: "100%",
                    no_results_text: "Ничего не найдено!"
                });
                // Формирование правильных полей
                createOptionsValue(numberCatalog, blockGroup+dubKey, '.blockRow');
                if (parentBlock == '') {
                    // Добавляем опции
                    createOptions("rest/profile/catalogs/" + rowSelectField.catalogId + "/elems", numberCatalog, "valueStr", "id", numberField, "");
                }
            }
            // Если вид поля справочник организаций
            if (rowSelectField.fieldType === "CATALOG_ORGANIZATIONS") {
                var numberCatalog = ('#'+selectFieldName);
                // Добавляем строку
                $(blockGroup+dubKey+' .blockGroupFields').append('' +
                    '<div class="row blockRow d-flex align-items-center'+parentBlock+parentCatalog+'" data-row="'+y+'">' +
                    '   <div class="col-md-3 text-left mt-3">' +
                    '       <div class="text-muted">'+rowSelectField.name+requiredSup+'</div>' +
                    '   </div>' +
                    '   <div class="col-md-8 mt-3">' +
                    '       <select data-placeholder="Выберите вид документа" class="chosen-select" id="'+selectFieldName+'" name="'+selectFieldName+'" data-catalog="'+rowSelectField.catalogId+'" data-field="'+rowSelectField.fieldId+'"'+idField+enaOpiton+required+'>' +
                    '           <option value="">Выберите значение справочника</option>' +
                    '       </select>' +
                    '   </div>' +
                    '   <div class="col-md-1 mt-3 text-right">' +
                    '       <button class="btn btn-primary btn-sm addElement rounded m-0 px-3 waves-effect" data-toggle="modal" data-target="#addElement" data-catalog="'+numberCatalog+'" type="button" title="Добавить организацию" '+enaOpiton+'>' +
                    '           <i class="fas fa-plus white-text"></i>' +
                    '       </button>' +
                    '   </div>' +
                    '   <div class="col-md-3"></div>' +
                    '   <div class="col-9">'+requiredValidate+'</div>' +
                    '</div>');
                $(numberCatalog).chosen({
                    width: "100%",
                    no_results_text: "Ничего не найдено!"
                });
                // Добавляем опции при нажатии поля
                createOptions("rest/profile/organizations/", numberCatalog, "shortNameLf", "id", numberField, '');
            }
            // Если вид поля справочник пользователей
            if (rowSelectField.fieldType === "CATALOG_USERS") {
                var numberCatalog = ('#'+selectFieldName);
                // Добавляем строку
                $(blockGroup+dubKey+' .blockGroupFields').append('' +
                    '<div class="row blockRow d-flex align-items-center'+parentBlock+parentCatalog+'" data-row="'+y+'">' +
                    '   <div class="col-md-3 text-left mt-3">' +
                    '       <div class="text-muted">'+rowSelectField.name+requiredSup+'</div>' +
                    '   </div>' +
                    '   <div class="col-md-9 mt-3">' +
                    '       <select data-placeholder="Выберите вид документа" class="chosen-select" id="'+selectFieldName+'" name="'+selectFieldName+'" data-catalog="'+rowSelectField.catalogId+'" data-field="'+rowSelectField.fieldId+'"'+idField+enaOpiton+required+'>' +
                    '           <option value="">Выберите значение справочника</option>' +
                    '       </select>'+requiredValidate+'' +
                    '   </div>' +
                    '</div>');
                $(numberCatalog).chosen({
                    width: "100%",
                    no_results_text: "Ничего не найдено!"
                });
                // Добавляем опции
                createOptions("rest/profile/users/", numberCatalog, '', 'id', numberField, 'users');
            }
            if (rowSelectField.fieldType === "ATTACHMENT") {
                // Добавляем строку
                if (parentBlock == '') {
                    var attachment = 1;
                    if (id && id > 0) {
                        attachment = 2;
                    }
                    $(blockGroup+dubKey+ ' .blockGroupFields').append('' +
                        '<div class="row blockRow d-flex align-items-center'+parentBlock+parentCatalog+'" data-row="'+y+'">' +
                        '   <div class="col-md-3 text-left">' +
                        '       <div class="text-muted">'+rowSelectField.name+requiredSup+'</div>' +
                        '   </div>' +
                        '   <div class="col-md-9"></div>' +
                        '</div>');
                    createInput(".col-md-9:last", "file", 'inputFile', 'inputFile', "Загрузить файл", 0, '' + rowSelectField.name, rowSelectField.valueStr, rowSelectField.fieldId, 0, idFiledInput, rowSelectField.enabled, rowSelectField.required, attachment, '');
                }
            }
            if (rowSelectField.fieldType === "TEXTAREA") {
                // Добавляем строку
                if (parentBlock == '') {
                    $(blockGroup+dubKey+' .blockGroupFields').append('' +
                        '<div class="row blockRow'+parentBlock+parentCatalog+'" data-row="'+y+'">' +
                        '   <div class="col-md-3 text-left mt-3">' +
                        '       <div class="text-muted">'+rowSelectField.name+requiredSup+'</div>' +
                        '   </div>' +
                        '   <div class="col-md-9 mt-3">' +
                        '       <textarea></textarea>'+requiredValidate+'' +
                        '   </div>' +
                        '</div>');
                }
            }
            if (rowSelectField.fieldType === "TEXT") {
                // Добавляем строку
                if (parentBlock == '') {
                    var textName = '';
                    var textId = y + 1;
                    if (id > 0) {
                        textName = rowSelectField.id+'_';
                    }
                    var nameText = "inputText_"+dubKey+'_'+textName+textId;
                    $(blockGroup+dubKey+' .blockGroupFields').append('' +
                        '<div class="row blockRow d-flex align-items-center'+parentBlock+parentCatalog+'" data-row="'+y+'">' +
                        '   <div class="col-md-3 text-left mt-3">' +
                        '       <div class="text-muted">'+rowSelectField.name+requiredSup+'</div>' +
                        '   </div>' +
                        '   <div class="col-md-9 mt-3"></div>' +
                        '</div>');
                    createInput(".col-md-9:last", "text", nameText, nameText, "Введите значение", 0, '' + rowSelectField.name, rowSelectField.valueStr, rowSelectField.fieldId, 0, idFiledInput, rowSelectField.enabled, rowSelectField.required, '', 1);
                    textId = textId + 1;
                }
            }
        }
    }

    // Подключение блока GROUP_FIELDS
    function groupNewFields (field, fieldId, dubKey, name, newKey, block) {
        var delGroup = 'delGroup';
        var cloneGroup = 'cloneGroup';
        var dataBlock = 1;
        var nameGroup = 'nameGroup';
        var blockGroup = '#blockGroup';
        if(block && block === 1) {
            delGroup = 'delGroupNew';
            cloneGroup = 'cloneGroupNew';
            dataBlock = 2;
            nameGroup = 'nameGroupNew';
            blockGroup = '#blockGroupNew';
        }
        // Кнопка удаления полей
        var delButton = ' d-none';
        var cloneButton = ' d-none';
        if (dubKey > 1) {
            delButton = '';
            //cloneButton = '';
        }
        var blockNameVal = 'Блок '+newKey;
        if(name) {blockNameVal = 'Блок '+name;}
        $(field).append('' +
            '<div class="row card mb-3 blockGroup" id="blockGroup'+dubKey+'" data-field="'+newKey+'" data-block="'+dataBlock+'">' +
            '   <div class="col-12">' +
            '       <div class="card-body">' +
            '           <div class="row">' +
            '               <div class="col-md-9 text-left"><h5 class="'+nameGroup+'">'+blockNameVal+'</h5></div>' +
            '               <div class="col-md-3 text-right">' +
            '                   <div id="'+cloneGroup+dubKey+'" class="btn btn-mdb-color btn-sm cloneGroup rounded'+cloneButton+'" title="Дублировать блок"><i class="fas fa-copy"></i></div>' +
            '                   <div id="'+delGroup+dubKey+'" data-toggle="modal" data-target="#deleteBlock" class="btn btn-danger btn-sm delGroup rounded'+delButton+' ml-3" title="Удалить блок"><i class="fas fa-trash"></i></div>' +
            '               </div>' +
            '           </div>' +
            '           <hr>' +
            '           <div class="row">' +
            '               <div class="col-12 blockGroupFields" data-block="'+dataBlock+'"></div>' +
            '           </div>' +
            '       </div>' +
            '   </div>' +
            '</div>');
    }

    // Получение стека полей
    function getNewFields (url, id, number, short, block, name, pole) {
        var field = '#blockBlock';
        var upElem = 'upElem';
        var inputDate = 'inputDate';
        var inputTime = 'inputTime';
        var blockDate = 'blockDate';
        var blockTime = 'blockTime';
        var up = 1;
        var blockGroup = '#blockGroup';
        var idBlockGroup = 'blockGroup';
        var newBlockGroup = '.newBlockGroup';
        var blockName = '.blockName';
        var delGroup = 'delGroup';
        var cloneGroup = 'cloneGroup';
        var dataBlock = 1;
        var nameGroup = 'nameGroup';
        var addGroup = 'addGroup';
        var blockDiv = 'blockDiv';
        if(block && block === 1) {
            field = '#blockBlockNew';
            upElem = 'upElemNew';
            inputDate = 'inputDateNew';
            inputTime = 'inputTimeNew';
            blockDate = 'blockDateNew';
            blockTime = 'blockTimeNew';
            up = 2;
            blockGroup = '#blockGroupNew';
            idBlockGroup = 'blockGroupNew';
            newBlockGroup = '.newBlockGroupNew';
            blockName = '.blockNameNew';
            delGroup = 'delGroupNew';
            cloneGroup = 'cloneGroupNew';
            dataBlock = 2;
            nameGroup = 'nameGroupNew';
            addGroup = 'addGroupNew';
            blockDiv = 'blockDivNew'
        }
        return $.getJSON (url, function(data) {
            var rowChild = data;
            if(id > 0 && block !== 1) {rowChild = data.childFields;}
            if(pole && pole > 0) {rowChild = data[pole];}
            console.log(data[pole]);
            for(var i in rowChild) {
                var row = rowChild[i];
                if(pole && pole > 0) {row = rowChild;}
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
                var selectFieldName = 'selectField'+row.field.fieldId;
                if (row.field.fieldType === "DATE") {
                    if (id > 0) {
                        idField = row.field.id;
                        if(row.field.valueDate !== '') {valueDate = formatDate(row.field.valueDate, 1);}
                    }
                    createInput (field, "date", blockDate,  inputDate, "Введите дату", short, '<i class="fas fa-calendar-alt mr-2"></i>'+row.field.name, valueDate, row.field.fieldId, up, idField, row.field.enabled, row.field.required, '', '');
                }
                if (row.field.fieldType === "TIME") {
                    if (id > 0) {
                        idField = row.field.id;
                        if(row.field.valueDate !== '') {valueDate = formatTime(row.field.valueDate);}
                    }
                    createInput (field, "time", blockTime,  inputTime, "Введите время", short, '<i class="fas fa-clock mr-2"></i>'+row.field.name, valueDate, row.field.fieldId, up, idField, row.field.enabled, row.field.required, '', '');
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
                    $(field).append('' +
                        '<div class="row my-3">' +
                        '   <div class="col-md-12 text-left">' +
                        '       <div class="form-check">' +
                        '           <input type="checkbox" class="form-check-input '+upElem+'" id="'+row.field.tag+'" data-field="'+idField+'" name="'+row.field.tag+'" '+valueInt+checekCheckBox+'>' +
                        '           <label class="form-check-label text-muted text-left" for="'+row.field.tag+'">'+row.field.name+'</label>' +
                        '       </div>' +
                        '   </div>' +
                        '</div>');
                    if(row.field.childFields.length > 0) {
                        $(field).append('<div id="'+row.field.tag+'BlockDiv" class="childBox d-none"></div>');
                        for(var y in row.field.childFields) {
                            var checkField = row.field.childFields[y];
                            idField = null;
                            if (id > 0) {idField = checkField.id;}
                            var elementField = field+' #'+row.field.tag+'BlockDiv';
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
                                $(elementField).append('' +
                                    '<div class="row ml-1 mb-3">' +
                                    '   <div class="col-md-3 text-left mt-3">' +
                                    '       <div class="text-muted">'+checkField.name+requiredSup+'</div>' +
                                    '   </div>' +
                                    '   <div class="col-md-9 mt-3">' +
                                    '       <textarea type="text" id="'+nameTextarea+'" class="checkClass form-control" data-field="'+checkField.fieldId+'"'+textareaId+'>'+textValue+'</textarea>'+requiredValidate+'' +
                                    '   </div>' +
                                    '</div>');
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
                        textName = row.field.id + '_';
                    }
                    var nameText = "inputText_" + textName + textId;
                    createInput(field, "text", nameText, nameText, "Введите значение", short, row.field.name, row.field.valueStr, row.field.fieldId, up, idField, row.field.enabled, row.field.required, '', '');
                    textId = textId+1;
                }
                // Если вид поля выборка
                var parentBlock = '';
                var parentCatalog = '';
                if(row.field.parentCatalogId > 0) {
                    parentCatalog = ' p' + row.field.parentCatalogId;
                    parentBlock = ' d-none';
                }
                if(id > 0) {
                    if(row.field.parentCatalogId > 0) {
                        parentCatalog = ' p' + row.field.parentCatalogId;
                        if(row.field.valueInt && row.field.valueInt > 0) {
                            parentBlock = '';
                        } else {
                            parentBlock = ' d-none';
                        }
                    }
                }
                if (row.field.fieldType === "CATALOG") {
                    if(id > 0) {idField = ' data-id="'+row.field.id+'"';}
                    var numberCatalog = ('#' + selectFieldName);
                    // Добавляем строку
                    $(field).append('' +
                        '<div class="row blockRowUp '+upElem+parentBlock+parentCatalog+' ml-1 mb-3">' +
                        '   <div class="col-md-3 text-left mt-3">' +
                        '       <div class="text-muted">'+row.field.name+requiredSup+'</div>' +
                        '   </div>' +
                        '   <div class="col-md-9">' +
                        '       <select data-placeholder="Выберите вид документа" class="chosen-select" searchable=" Поиск" type="select" id="'+selectFieldName+'" name="'+selectFieldName+'" data-catalog="'+row.field.catalogId+'" data-field="'+row.field.fieldId+'"'+idField+enaOpiton+required+'>' +
                        '           <option value="">Выберите значение справочника</option>' +
                        '       </select>'+requiredValidate+'' +
                        '   </div>' +
                        '</div>');
                    $(numberCatalog).chosen({
                        width: "100%",
                        no_results_text: "Ничего не найдено!"
                    });
                    // Формирование правильных полей
                    createOptionsValue(numberCatalog, field, '.blockRowUp');
                    if(parentBlock == '') {
                        // Добавляем опции
                        createOptions("rest/profile/catalogs/" + row.field.catalogId + "/elems", numberCatalog, "valueStr", "id", numberField, '');
                    }
                }
                // Если вид поля справочник организаций
                if (row.field.fieldType === "CATALOG_ORGANIZATIONS") {
                    var numberCatalog = ('#' + selectFieldName);
                    // Добавляем строку
                    $(field).append('' +
                        '<div class="row blockRowUp '+upElem+parentBlock+parentCatalog+' ml-1 mb-3 d-flex align-items-center">' +
                        '   <div class="col-md-3 text-left mt-3">' +
                        '       <div class="text-muted">'+row.field.name+requiredSup+'</div>' +
                        '   </div>' +
                        '   <div class="col-md-8 mt-3">' +
                        '       <select data-placeholder="Выберите вид документа" class="chosen-select" type="select" id="'+selectFieldName+'" name="'+selectFieldName+'" data-catalog="'+row.field.catalogId+'" data-field="'+row.field.fieldId+'"'+idField+enaOpiton+required+'>' +
                        '           <option value="">Выберите значение справочника</option>' +
                        '       </select>' +
                        '   </div>' +
                        '   <div class="col-md-1 mt-3 text-right">' +
                        '       <button class="btn btn-primary btn-sm addElement rounded m-0 px-3 waves-effect" data-toggle="modal" data-target="#addElement" data-catalog="'+numberCatalog+'" type="button" title="Добавить организацию" '+enaOpiton+'><i class="fas fa-plus white-text"></i></button>' +
                        '   </div>' +
                        '   <div class="col-md-3"></div>' +
                        '   <div class="col-9">'+requiredValidate+'</div>' +
                        '</div>');
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
                    $(field).append('' +
                        '<div class="row '+upElem+' ml-1 mb-3 d-flex align-items-center justify-content-center">' +
                        '   <div class="col-md-3 text-left mt-3">' +
                        '       <div class="text-muted">'+row.field.name+requiredSup+'</div>' +
                        '   </div>' +
                        '   <div class="col-md-9">' +
                        '       <select data-placeholder="Выберите вид документа" class="chosen-select" searchable=" Поиск" type="select" id="'+selectFieldName+'" name="'+selectFieldName+'" data-field="'+row.field.fieldId+'"'+idField+enaOpiton+required+'>' +
                        '           <option value="">Выберите значение справочника</option>' +
                        '       </select>'+requiredValidate+'' +
                        '   </div>' +
                        '</div>');
                    var numberCatalog = ('#'+selectFieldName);
                    $(numberCatalog).chosen({
                        width: "100%",
                        no_results_text: "Ничего не найдено!"
                    });
                    // Добавляем опции
                    createOptions ('rest/profile/docs/regnumbers/', numberCatalog, 'regNum', 'id', numberField, '');
                }
                // Если вид поля HTML таблицы
                if (row.field.fieldType === "CATALOG_HTML_TABLES") {
                    if(id > 0) {
                        $(field).append('' +
                            '<div class="row '+upElem+' ml-1 mb-3 d-flex' +
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
                        $(field).append('' +
                            '<div class="row '+upElem+' ml-1 mb-3 d-flex' +
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
                if (row.field.fieldType === "GROUP_FIELDS") {
                    var idFiledInput = '';
                    var newKey = 1;
                    var dubKey = row.field.fieldId+'_'+i;
                    var dataField = 0;
                    var fieldId = row.field.fieldId;
                    var blockDivId = 'blockDiv'+fieldId;
                    if (number != '') {
                        newKey = number;
                        dataField = number;
                        blockDivId = 'blockDiv'+fieldId+'_'+number;
                    }
                    $('#blockBlock').append('' +
                        '<div id="'+blockDivId+'" class="'+blockDiv+' card p-3 mb-3">' +
                        '   <h5 class="blockName" data-block="'+fieldId+'">'+row.field.name+'</h5>' +
                        '   <div class="card-body">' +
                        '       <div class="blockField"></div>' +
                        '       <div class="row">' +
                        '           <div class="col-12 text-right">' +
                        '               <div class="btn btn-primary btn-sm pointer '+addGroup+' mr-3 rounded" title="Добавить блок" data-value="'+i+'" data-block="'+fieldId+'">' +
                        '                   <i class="fas fa-plus mr-2"></i>Добавить' +
                        '               </div>' +
                        '           </div>' +
                        '       </div>' +
                        '   </div>' +
                        '</div>');
                    var rowChildFields = row.field.childFields;
                    groupNewFields('#'+blockDivId+' .blockField', fieldId, dubKey, name, newKey, block);
                    groupNewFieldsValue (rowChildFields, id, blockGroup, dubKey, dataBlock);
                    if (id > 0) {
                        number = number + 1;
                    }
                }
            }
        }).done(function(response) {
            var filedBlock = '#blockFields, #blockBlock, #btnSave, #btnWordFile';
            if(block && block === 1) {filedBlock = '#blockFieldsNew, #blockBlockNew, #btnSaveNew, #btnWordFileNew';}
            if (response.length == 0) {$(filedBlock).addClass('d-none');}
        });
    }