    // Получение данных по виду поля
    function getFieldType (type, data, pole, id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, key, dubKey, idField, idFiledInput, selfClass, up) {
        var numberCatalog = ('#'+selectFieldName);
        var valueDate = ''; var classElem = ''; var idData = ''; var checkClass = '';
        if(selfClass && selfClass != '') {checkClass = ' '+selfClass;}
        // DATE
        if (data.fieldType === "DATE") {
            var textName = '';
            var newId = key+1;
            if (id > 0) {
                idField = data.id;
                if(data.valueDate !== '') {valueDate = formatDate(data.valueDate, 1);}
                textName = data.id+'_';
            }
            var blockDate = "blockDate_"+dubKey+'_'+textName+newId;
            createInput (pole, "date", blockDate,  blockDate, "Введите дату", 0, '<i class="fas fa-calendar-alt mr-2"></i>'+data.name, valueDate, data.fieldId, up, idField, data.enabled, data.required, '', '', selfClass);
            newId = parseInt(newId)+1;
        }
        // TIME
        if (data.fieldType === "TIME") {
            var textName = '';
            var newId = key+1;
            if (id > 0) {
                idField = data.id;
                if(data.valueDate !== '') {valueDate = formatTime(data.valueDate);}
                textName = data.id+'_';
            }
            var blockTime = "blockTime_"+dubKey+'_'+textName+newId;
            createInput (pole, "time", blockTime,  blockTime, "Введите время", 0, '<i class="fas fa-clock mr-2"></i>'+data.name, valueDate, data.fieldId, up, idField, data.enabled, data.required, '', '', selfClass);
            newId = parseInt(newId)+1;
        }
        // TEXT
        if (data.fieldType === "TEXT") {
            if (parentBlock == '') {
                var textName = '';
                var newId = key+1;
                if (id > 0) {
                    idField = data.id;
                    textName = data.id+'_';
                }
                var nameText = "inputText_"+dubKey+'_'+textName+newId;
                $(pole).append('' +
                    '<div class="row blockRow d-flex align-items-center'+parentBlock+parentCatalog+' mb-3" data-row="'+key+'">' +
                    '   <div class="col-md-3 text-left">' +
                    '       <div class="text-muted">'+data.name+requiredSup+'</div>' +
                    '   </div>' +
                    '   <div class="col-md-9"></div>' +
                    '</div>');
                createInput(".col-md-9:last", "text", nameText, nameText, "Введите значение", 0, '' + data.name, data.valueStr, data.fieldId, up, idField, data.enabled, data.required, '', 1, selfClass);
                newId = newId + 1;
            }
        }
        // TEXTAREA
        if (data.fieldType === "TEXTAREA") {
            if(up == 1) {classElem = ' upElem';}
            var textData = '';
            if(id > 0) {
                textData = data.valueStr;
                idData = ' data-id="'+data.id+'"';
            }
            if (parentBlock == '') {
                var textName = '';
                var newId = key+1;
                if (id > 0) {textName = data.id+'_';}
                var nameText = "textarea_"+dubKey+'_'+textName+newId;
                $(pole).append(
                    '<div class="row blockRow'+parentBlock+parentCatalog+' mb-3" data-row="'+key+'">' +
                    '   <div class="col-md-3 text-left">' +
                    '       <div class="text-muted">'+data.name+requiredSup+'</div>' +
                    '   </div>' +
                    '   <div class="col-md-9'+classElem+'">' +
                    '       <textarea class="form-control'+checkClass+'" id="'+nameText+'"' +
                    ' data-field="'+data.fieldId+'"'+idData+'>'+textData+'</textarea>'+requiredValidate+'' +
                    '   </div>' +
                    '</div>');
                newId = newId + 1;
            }
        }
        // ВЛОЖЕНИЯ
        if (data.fieldType === "ATTACHMENT") {
            if (parentBlock == '') {
                var textName = '';
                var newId = key+1;
                if (id > 0) {textName = data.id+'_';}
                var inputFile = "inputFile_"+dubKey+'_'+textName+newId;
                var attachment = 1;
                if (id && id > 0) {attachment = 2;}
                $(data).append(
                    '<div class="row blockRow d-flex align-items-center'+parentBlock+parentCatalog+'" data-row="'+key+'">' +
                    '   <div class="col-md-3 text-left">' +
                    '       <div class="text-muted">'+data.name+requiredSup+'</div>' +
                    '   </div>' +
                    '   <div class="col-md-9"></div>' +
                    '</div>');
                createInput(".col-md-9:last", "file", inputFile, inputFile, "Загрузить файл", 0, '' + data.name, data.valueStr, data.fieldId, up, idFiledInput, data.enabled, data.required, attachment, '');
                newId = newId + 1;
            }
        }
        // HTML ТАБЛИЦЫ
        if (data.fieldType === "CATALOG_HTML_TABLES") {
            if(id > 0 && data.valueStr && data.valueStr !== '') {
                $(pole).append(
                    '<div class="row mb-3 d-flex align-items-center justify-content-center tableHtml"' +
                    ' id="catalogTables'+dubKey+'" type="tableHtml"' +
                    ' data-id="'+data.id+'"' +
                    ' data-field="'+data.fieldId+'"' +
                    ' data-value="'+data.valueInt+'">' +
                    '   <div class="col-md-12 text-left newTable">' +
                    '       <div class="btn btn-primary btn-sm rounded tableTemplates" data-table="'+dubKey+'" data-click="">Изменить макет таблицы</div>' +
                    /*'       <div class="btn btn-danger btn-sm mx-2 rounded" data-delete="'+data.valueInt+'">Удалить таблицу</div>' +*/
                    '       <div class="editTable">' +
                    '           <h6 class="my-3 text-center"></h6>' +
                    '           ' +data.valueStr+
                    '       </div>' +
                    '   </div>' +
                    '</div>');
                var tableId = '#catalogTables'+dubKey+' .newTable';
                console.log(tableId);
                var sumTop = parseInt($(tableId+' table tbody tr:first td:last').html());
                sumTop = sumTop+1;
                $(tableId+' table thead tr:first').append(
                    '<th class="text-center deleteElem" rowspan="2">Удалить</th>');
                if($(tableId+' table thead').hasClass('noNumber')) {
                    $(tableId+' table tbody tr:first').append(
                        '<td class="deleteElem">' +
                        '   <div class="btn btn-sm btn-danger table-remove rounded px-3 my-0">' +
                        '       <i class="fas fa-trash"></i>' +
                        '   </div>' +
                        '</td>'
                    );
                } else {
                    $(tableId+' table tbody tr:first').append(
                        '<td class="text-center deleteElem">'+sumTop+'</td>'
                    );
                    $(tableId+' table tbody tr:first td').css('text-align', 'center');
                }
                $(tableId+' table tbody tr:not(:first)').append(
                    '<td class="deleteElem">' +
                    '   <div class="btn btn-sm btn-danger table-remove rounded px-3 my-0">' +
                    '       <i class="fas fa-trash"></i>' +
                    '   </div>' +
                    '</td>'
                );
                $(tableId).append(
                    '<div class="row">' +
                    '   <div class="col-12 text-right">' +
                    '       <div class="btn btn-primary btn-sm rounded addTableRow" data-table="'+dubKey+'">' +
                    '           <i class="fas fa-plus white-text mr-2"></i>Добавить строку' +
                    '       </div>' +
                    '   </div>' +
                    '</div>'
                );
                $('.newTable table th').css({
                    'text-align': 'center',
                    'vertical-align': 'middle',
                    'font-weight': 'bold'
                });
                $(tableId+' table td').css('text-align', 'left');
                //$(tableId+' table tbody tr:first td').css('text-align', 'center');
                $(tableId+' table tbody tr').each(function() {
                    $('td:first', this).css('text-align', 'center');
                });
            } else {
                $(pole).append(
                    '<div class="row tableHtml mb-3 d-flex align-items-center justify-content-center"' +
                    ' id="catalogTables'+dubKey+'" type="tableHtml"' +
                    ' data-field="'+data.fieldId+'">' +
                    '   <div class="col-md-3 text-left">' +
                    '       <div class="text-muted">'+data.name+requiredSup+'</div>' +
                    '   </div>' +
                    '   <div class="col-md-9 text-left newTable">' +
                    '       <div class="btn btn-primary btn-sm rounded tableTemplates ml-0" data-table="'+dubKey+'" data-click="">Создать таблицу</div>' +
                    /*'       <div class="btn btn-danger btn-sm mx-2 rounded delTab d-none" id="delTab'+dubKey+'" data-delete="'+dubKey+'">Удалить таблицу</div>' +*/
                    '   </div>' +
                    '</div>');
            }
        }
        // SELECT ОБЩИЙ
        if (data.fieldType === "CATALOG") {
            if(up == 1) {classElem = ' upElem';}
            if(id > 0) {idData = ' data-id="'+data.id+'"';}
            $(pole).append(
                '<div class="row blockRow'+parentBlock+parentCatalog+' mb-3" data-row="'+key+'">' +
                '   <div class="col-md-3 text-left d-flex align-items-center">' +
                '       <div class="text-muted">'+data.name+requiredSup+'</div>' +
                '   </div>' +
                '   <div class="col-md-9'+classElem+'">' +
                '       <select data-placeholder="Выберите вид документа"' +
                ' class="chosen-select'+checkClass+'"' +
                ' id="'+selectFieldName+'" name="'+selectFieldName+'" type="select"' +
                ' data-catalog="'+data.catalogId+'"' +
                ' data-field="'+data.fieldId+'"' +
                ''+idData+enaOpiton+required+'>' +
                '           <option value="">Выберите значение справочника</option>' +
                '       </select>'+requiredValidate+'' +
                '   </div>' +
                '</div>');
            $(numberCatalog).chosen({
                width: "100%",
                no_results_text: "Ничего не найдено!"
            });
            var poleBlockGroup = '';
            if(blockGroup != '') {
                poleBlockGroup = blockGroup+dubKey;
            }
            createOptionsValue(numberCatalog, poleBlockGroup, '.blockRow');
            if (parentBlock == '') {
                createOptions("rest/profile/catalogs/" + data.catalogId + "/elems", numberCatalog, "valueStr", "id", numberField, "");
            }
        }
        // SELECT ОРГАНИЗАЦИИ
        if (data.fieldType === "CATALOG_ORGANIZATIONS") {
            if(up == 1) {classElem = ' upElem';}
            if(id > 0) {idData = ' data-id="'+data.id+'"';}
            $(pole).append(
                '<div class="row blockRow d-flex align-items-center'+parentBlock+parentCatalog+' mb-3"' +
                ' data-row="'+key+'">' +
                '   <div class="col-md-3 text-left">' +
                '       <div class="text-muted">'+data.name+requiredSup+'</div>' +
                '   </div>' +
                '   <div class="col-md-8'+classElem+'">' +
                '       <select data-placeholder="Выберите вид документа"' +
                ' class="chosen-select'+checkClass+'"' +
                ' id="'+selectFieldName+'" name="'+selectFieldName+'" type="select"' +
                ' data-catalog="'+data.catalogId+'"' +
                ' data-field="'+data.fieldId+'"' +
                ''+idData+enaOpiton+required+'>' +
                '           <option value="">Выберите значение справочника</option>' +
                '       </select>' +
                '   </div>' +
                '   <div class="col-md-1 text-right">' +
                '       <button class="btn btn-primary btn-sm addElement rounded m-0 px-3 waves-effect"' +
                ' data-toggle="modal" data-target="#addElement" data-catalog="'+numberCatalog+'"' +
                ' type="button" title="Добавить организацию" '+enaOpiton+'>' +
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
        // SELECT ПОЛЬЗОВАТЕЛИ
        if (data.fieldType === "CATALOG_USERS") {
            if(up == 1) {classElem = ' upElem';}
            if(id > 0) {idData = ' data-id="'+data.id+'"';}
            $(pole).append(
                '<div class="row blockRow d-flex align-items-center'+parentBlock+parentCatalog+' mb-3"' +
                ' data-row="'+key+'">' +
                '   <div class="col-md-3 text-left">' +
                '       <div class="text-muted">'+data.name+requiredSup+'</div>' +
                '   </div>' +
                '   <div class="col-md-9'+classElem+'">' +
                '       <select data-placeholder="Выберите вид документа"' +
                ' class="chosen-select'+checkClass+'"' +
                ' id="'+selectFieldName+'" name="'+selectFieldName+'" type="select"' +
                ' data-catalog="'+data.catalogId+'"' +
                ' data-field="'+data.fieldId+'"' +
                ''+idData+enaOpiton+required+'>' +
                '           <option value="">Выберите значение справочника</option>' +
                '       </select>'+requiredValidate+'' +
                '   </div>' +
                '</div>');
            $(numberCatalog).chosen({
                width: "100%",
                no_results_text: "Ничего не найдено!"
            });
            createOptions("rest/profile/users/", numberCatalog, '', 'id', numberField, 'users');
        }
    }

    // Получение данных групового поля
    function getFiledTypeGroup (type, data, pole, id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, key, dubKey, idField, idFiledInput, up) {
        var classElem = '';
        if(up == 1) {classElem = ' upElem';}
        // GROUP_CHECKBOX
        if (data.fieldType === "GROUP_CHECKBOX") {
            var valueInt = 'value="0"';
            var checekCheckBox = '';
            if (data.valueInt > 0) {
                valueInt = 'value="'+data.valueInt+'"';
                checekCheckBox = ' checked';
            }
            var idData = '';
            if (id > 0) {idData = ' data-id="'+data.id+'"';}
            var nameBlock = data.tag+data.fieldId;
            $(pole).append(
                '<div class="row my-3">' +
                '   <div class="col-md-12 text-left">' +
                '       <div class="form-check">' +
                '           <input class="form-check-input'+classElem+'"' +
                ' id="'+nameBlock+'" type="checkbox"' +
                ' data-field="'+data.fieldId+'"'+idData+'' +
                ' name="'+nameBlock+'"' +
                ' '+valueInt+checekCheckBox+'>' +
                '           <label class="form-check-label text-muted' +
                ' text-left" for="'+nameBlock+'">'+data.name+'</label>' +
                '       </div>' +
                '   </div>' +
                '</div>'
            );
            if(data.childFields.length > 0) {
                $(pole).append('<div id="'+nameBlock+'BlockDiv" class="childBox mb-4 d-none"></div>');
                for(var y in data.childFields) {
                    var checkField = data.childFields[y];
                    idField = null;
                    if (id > 0) {
                        idField = checkField.id;
                        numberField = checkField.valueInt;
                    }
                    var elementField = pole+' #'+nameBlock+'BlockDiv';
                    var textId = y+1;
                    getFieldType (checkField.fieldType, checkField, elementField, id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, y, dubKey, idField, idFiledInput, 'checkClass');
                    /*if (checkField.fieldType === "TEXT") {
                        getFieldType (checkField.fieldType, checkField, elementField, id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, y, dubKey, idField, idFiledInput, selfClass)
                    }
                    if (checkField.fieldType === "TEXTAREA") {
                        getFieldType ("TEXTAREA", checkField, elementField, id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, y, dubKey, idField, idFiledInput)
                    }*/
                    textId = textId+1;
                }
            }
            checkedFields ('#'+nameBlock, '#'+nameBlock+'BlockDiv');
            if (data.valueInt > 0) {$('.childBox').removeClass('d-none');}
        }
        // GROUP_FIELDS
        if (data.fieldType === "GROUP_FIELDS") {
            data = data.childFields;
            getFieldType (data.fieldType, data, pole, id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, key, dubKey, idField, idFiledInput)
        }
    }