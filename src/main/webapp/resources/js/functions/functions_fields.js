    // Получение данных по виду поля
    function getFieldType (type, data, pole, id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, key, dubKey, idField, idFiledInput, selfClass, up, emptyData) {
        var numberCatalog = (pole+' #'+selectFieldName);
        var valueDate = ''; var classElem = ''; var idData = ''; var checkClass = ''; var dataValue = '';
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
                    if(emptyData == 1) {
                        dataValue = data.valueStr;
                    }
                }
                var nameText = "inputText_"+dubKey+'_'+textName+newId;
                $(pole).append(
                    '<div class="row blockRow d-flex align-items-center'+parentBlock+parentCatalog+' mb-3" data-row="'+key+'">' +
                    '   <div class="col-md-3 text-left">' +
                    '       <div class="text-muted">'+data.name+requiredSup+'</div>' +
                    '   </div>' +
                    '   <div class="col-md-9"></div>' +
                    '</div>');
                createInput(pole+" .col-md-9:last", "text", nameText, nameText, "Введите значение", 0, '' + data.name, dataValue, data.fieldId, up, idField, data.enabled, data.required, '', 1, selfClass);
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
                    '<div class="row mb-3 d-flex align-items-center' +
                    ' justify-content-center tableHtml blockElement"' +
                    ' id="catalogTables'+data.fieldId+dubKey+'" type="tableHtml"' +
                    ' data-id="'+data.id+'"' +
                    ' data-field="'+data.fieldId+'"' +
                    ' data-value="'+data.valueInt+'">' +
                    '   <div class="col-md-12 text-left newTable">' +
                    '       <div class="btn btn-primary btn-sm rounded tableTemplates" data-table="'+data.fieldId+dubKey+'" data-click="">Изменить макет таблицы</div>' +
                    /*'       <div class="btn btn-danger btn-sm mx-2 rounded" data-delete="'+data.valueInt+'">Удалить таблицу</div>' +*/
                    '       <div class="editTable">' +
                    '           <h6 class="my-3 text-center"></h6>' +
                    '           ' +data.valueStr+
                    '       </div>' +
                    '   </div>' +
                    '</div>');
                var tableId = '#catalogTables'+data.fieldId+dubKey+' .newTable';
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
                    '       <div class="btn btn-primary btn-sm rounded addTableRow" data-table="'+data.fieldId+dubKey+'">' +
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
                $(tableId+' table td').css('text-align', 'left').addClass('content');
                //$(tableId+' table tbody tr:first td').css('text-align', 'center');
                $(tableId+' table tbody tr').each(function() {
                    $('td:first', this).css('text-align', 'center');
                });
            } else {
                $(pole).append(
                    '<div class="row tableHtml mb-3 d-flex' +
                    ' align-items-center justify-content-center blockElement"' +
                    ' id="catalogTables'+data.fieldId+dubKey+'" type="tableHtml"' +
                    ' data-field="'+data.fieldId+'">' +
                    '   <div class="col-md-3 text-left">' +
                    '       <div class="text-muted">'+data.name+requiredSup+'</div>' +
                    '   </div>' +
                    '   <div class="col-md-9 text-left newTable">' +
                    '       <div class="btn btn-primary btn-sm rounded tableTemplates ml-0" data-table="'+data.fieldId+dubKey+'" data-click="">Создать таблицу</div>' +
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
        // MULTISELECT
        if (data.fieldType === "CATALOG_MULTI_SELECT") {
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
                ' class="chosen-select'+checkClass+'" multiple' +
                ' id="'+selectFieldName+'" name="'+selectFieldName+'" type="multiselect"' +
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
            if(data.valueStr && data.valueStr != '') {
                var selectArray = [];
                var newSelect = data.valueStr.split( "," );
                $.each(newSelect, function(event, item) {
                    selectArray.push(parseInt(item));
                });
            } else {
                var selectArray = '';
            }
            if (parentBlock == '') {
                createOptions("rest/profile/catalogs/" + data.catalogId + "/elems", numberCatalog, "valueStr", "id", selectArray, "", 1);
            }
        }
        // SELECT ДОБАВЛЕНИЕ ПОЛЯ
        if (data.fieldType === "CATALOG_ADDABLE") {
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
                '       <button class="btn btn-primary btn-sm addCatalogElement rounded m-0 px-3 waves-effect"' +
                ' data-toggle="modal" data-target="#addCatalogElement" data-catalog="'+numberCatalog+'" data-field="'+data.catalogId+'"' +
                ' type="button" title="Добавить элемент" '+enaOpiton+'>' +
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
            var poleBlockGroup = '';
            if(blockGroup != '') {
                poleBlockGroup = blockGroup+dubKey;
            }
            createOptionsValue(numberCatalog, poleBlockGroup, '.blockRow');
            if (parentBlock == '') {
                createOptions("rest/profile/catalogs/"+data.catalogId+"/elems", numberCatalog, "valueStr", "id", numberField, "");
            }
        }
    }