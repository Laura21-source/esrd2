    // Получение полей блока GROUP_FIELDS
    function groupNewFieldsValue (data, id, blockId, dubKey, dataBlock, emptyData) {
        var idField = ''; var idFiledInput = '';
        var blockDiv = 'blockDiv';
        var blockGroup = 'blockGroup';
        var idBlock = 1;
        if(dataBlock == 1) {
            blockDiv = 'blockDivNew';
            blockGroup = 'blockGroupNew';
            idBlock = 2;
        }
        var blockGroup = '#'+blockDiv+blockId+' #'+blockGroup;
        for (var y in data) {
            var rowSelectField = data[y];
            // Есть ли родитель у блока
            var parentBlock = '';
            var parentCatalog = '';
            if (rowSelectField.parentCatalogId > 0) {
                parentCatalog = ' p'+rowSelectField.parentCatalogId;
                parentBlock = ' d-none';
            }
            var numberField = '';
            var selectFieldName = 'selectField_'+blockId+'_'+dubKey+'_'+idBlock+y;
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
            // Добавляем поля
            getFieldType (rowSelectField.fieldType, rowSelectField, blockGroup+dubKey+' .blockGroupFields', id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, y, dubKey, idField, idFiledInput, 'blockElement','', emptyData);
        }
    }

    // Подключение блока GROUP_FIELDS
    function groupNewFields (field, fieldId, dubKey, name, newKey, block, id, dataId) {
        var delGroup = 'delGroup';
        var cloneGroup = 'cloneGroup';
        var dataBlock = 1;
        var nameGroup = 'nameGroup';
        var blockGroup = 'blockGroup';
        if(block && block === 1) {
            delGroup = 'delGroupNew';
            cloneGroup = 'cloneGroupNew';
            dataBlock = 2;
            nameGroup = 'nameGroupNew';
            blockGroup = 'blockGroupNew';
        }
        // Кнопка удаления полей
        var delButton = ' d-none';
        var cloneButton = ' d-none';
        if (dubKey > 1) {
            delButton = '';
            //cloneButton = '';
        }
        var idData = '';
        var blockNameVal = 'Блок '+newKey;
        if(name) {blockNameVal = 'Блок '+name;}
        if(id > 0) {idData = ' data-id="'+dataId+'"';}
        $(field).append(
            '   <div class="row mb-3 '+blockGroup+'" id="'+blockGroup+dubKey+'"' +
            ' data-field="'+newKey+'"' +
            ' data-move="'+dubKey+'"' +
            ' data-block="'+dataBlock+'"'+idData+'' +
            ' data-div="'+fieldId+'">' +
            '       <div class="col-12">' +
            '           <div class="card">' +
            '               <div class="card-body">' +
            '                   <div class="row">' +
            '                       <div class="col-md-9 text-left"><h5 class="'+nameGroup+'">'+blockNameVal+'</h5></div>' +
            '                       <div class="col-md-3 text-right">' +
            '                           <div id="'+cloneGroup+dubKey+'" title="Дублировать блок"' +
            ' class="btn btn-mdb-color btn-sm '+cloneGroup+' rounded'+cloneButton+'">' +
            '<i class="fas fa-copy"></i></div>' +
            '                           <div id="'+delGroup+dubKey+'" title="Удалить блок"' +
            ' data-toggle="modal"' +
            ' data-parent="'+fieldId+'"' +
            ' data-target="#deleteBlock"' +
            ' class="btn btn-danger btn-sm '+delGroup+' rounded'+delButton+' ml-3">' +
            '<i class="fas fa-trash"></i></div>' +
            '                       </div>' +
            '                   </div>' +
            '                   <hr>' +
            '                   <div class="row">' +
            '                       <div class="col-12 blockGroupFields" data-block="'+dataBlock+'"></div>' +
            '                   </div>' +
            '               </div>' +
            '           </div>' +
            '       </div>' +
            '   </div>');
    }

    // Добавление блока группы
    function groupNew (blockDivClass, id, field, fieldId, dubKey, name, newKey, block, nameBlock, poleId, agree) {
        var blockDiv = 'blockDiv';
        var addBlock = 'addBlock';
        var blockName = 'blockName';
        //var blockDivClass = 'BlockDiv';
        if(block && block === 1) {
            blockDiv = 'blockDivNew';
            addBlock = 'addBlockNew';
            blockName = 'blockNameNew';
            //blockDivClass = 'BlockDivNew';
        }
        blockDiv = blockDiv+fieldId;
        var dataId = '';
        if(id > 0) {}
        $(field).append(
            '<div class="card '+blockDivClass+' p-3 mb-3" id="'+blockDiv+'" data-block="'+fieldId+'">' +
            '   <h5 class="'+blockName+' my-3">'+nameBlock+'</h5>' +
            '   <div class="blockField"></div>' +
            '   <div class="row my-3">' +
            '       <div class="col-12 text-right">' +
            '           <div title="Добавить блок"' +
            ' class="btn btn-primary btn-sm pointer '+addBlock+' mr-3 rounded"' +
            ' data-group="'+fieldId+'"' +
            ' data-value="'+poleId+'">' +
            '<i class="fas fa-plus mr-2"></i>Добавить</div>' +
            '       </div>' +
            '   </div>' +
            '</div>');
        if(agree != 1) {
            groupNewFields ('#'+blockDiv+' .blockField', fieldId, dubKey, name, newKey, block, id, dataId);
        }
    }

    function getFiledTypeGroupField (id, BlockDivClass, fieldFieldName, field, fieldId, dubKey, name, newKey, block, fieldName, i, fieldField, poleFieldId, dataField, poleFieldFieldId) {
        //console.log(id, BlockDivClass, fieldFieldName, field, fieldId,
        // dubKey, name, newKey, block, fieldName, i, fieldField, poleFieldId, dataField, poleFieldFieldId);
        if(id > 0) {
            if($(BlockDivClass).length == 0) {
                groupNew (BlockDivClass, id, field, fieldId, dubKey, name, newKey, block, fieldName, i, 1);
                groupNewFields (fieldField, fieldId, dubKey, name, 1, block, id, poleFieldId);
                groupNewFieldsValue (dataField, id, fieldId, dubKey, block, 1);
            } else {
                var idBlockValue = $(BlockDivClass+':last').attr('data-block');
                var y = $(fieldFieldName).length + 1;
                if(idBlockValue == poleFieldFieldId) {
                    groupNewFields (fieldField, fieldId, y, name, y, block, id, poleFieldId);
                    groupNewFieldsValue (dataField, id, fieldId, y, block, 1);
                } else {
                    groupNew (BlockDivClass, id, field, fieldId, y, name, y, block, fieldName, i, 1);
                    groupNewFields (fieldField, fieldId, y, name, 1, block, id, poleFieldId);
                    groupNewFieldsValue (dataField, id, fieldId, dubKey, block, 1);
                }
            }
        } else {
            groupNew (BlockDivClass, id, field, fieldId, dubKey, name, newKey, block, fieldName, i, '');
            groupNewFieldsValue (dataField, id, fieldId, dubKey, block, 1);
            //console.log(dataField, id, fieldId, dubKey, block);
        }
    }

    function getFiledTypeGroupFieldCheckBox (id, BlockDivClass, fieldFieldName, field, fieldId, dubKey, name, newKey, block, fieldName, i, fieldField, poleFieldId, dataField, poleFieldFieldId) {
        //console.log(id, BlockDivClass, fieldFieldName, field, fieldId,
        // dubKey, name, newKey, block, fieldName, i, fieldField, poleFieldId, dataField, poleFieldFieldId);
        if(id > 0) {
            if($(BlockDivClass).length == 0) {
                groupNew (BlockDivClass, id, field, fieldId, dubKey, name, newKey, block, fieldName, i, 1);
                groupNewFields (fieldField, fieldId, dubKey, name, 1, block, id, poleFieldId);
                groupNewFieldsValue (dataField, id, fieldId, dubKey, block, 1);
            } else {
                var idBlockValue = $(BlockDivClass+':last').attr('data-block');
                var y = $(fieldFieldName).length + 1;
                if(idBlockValue == poleFieldFieldId) {
                    groupNewFields (fieldField, fieldId, y, name, y, block, id, poleFieldId);
                    groupNewFieldsValue (dataField, id, fieldId, y, block, 1);
                } else {
                    groupNew (BlockDivClass, id, field, fieldId, y, name, y, block, fieldName, i, 1);
                    groupNewFields (fieldField, fieldId, y, name, 1, block, id, poleFieldId);
                    groupNewFieldsValue (dataField, id, fieldId, dubKey, block, 1);
                }
            }
        } else {
            groupNew (BlockDivClass, id, field, fieldId, dubKey, name, newKey, block, fieldName, i, '');
            groupNewFieldsValue (dataField, id, fieldId, dubKey, block, 1);
            //console.log(dataField, id, fieldId, dubKey, block);
        }
    }
