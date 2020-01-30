    // Получение стека полей
    function getNewFields (url, id, number, short, block, name, pole, mergePole) {
        //console.log(url, id, number, short, block, name, pole);
        var field = '#blockBlock';
        if(mergePole && mergePole !='') {
            field = mergePole+' #blockBlock';
        }
        var blockGroup = '#blockGroup';
        var blockDiv = 'blockDiv';
        var blockGroupClass = 'blockGroup';
        var BlockDivClass = '.BlockDiv';
        var arrayBlock = '#arrayBlock';
        var up = 1;
        if(block && block === 1) {
            field = '#blockBlockNew';
            if(mergePole && mergePole !='') {
                field = mergePole+' #blockBlockNew';
            }
            blockGroup = '#blockGroupNew';
            blockDiv = 'blockDivNew';
            blockGroupClass = 'blockGroupNew';
            BlockDivClass = '.BlockDivNew';
            arrayBlock = '#arrayBlockNew';
            up = 2;
        }
        return $.getJSON (url, function(data) {
            var rowChild = data;
            if(id > 0 && block !== 1) {rowChild = data.childFields;}
            if(id > 0) {number = 1;}
            if(pole && pole > 0) {rowChild = data[pole];}
            //console.log(rowChild);
            for(var i in rowChild) {
                var row = rowChild[i];
                if(pole && pole > 0) {row = rowChild;}
                // Переменная поля
                var idFiledInput = '';
                var newKey = 1;
                //var dubKey = row.field.fieldId+'_'+i;
                var dubKey = 1;
                var dataField = 0;
                var fieldId = row.field.fieldId;
                //var poleId = row.field.id;
                var blockDivId = blockDiv+fieldId;
                if (number != '') {
                    newKey = number;
                    dubKey = number;
                    dataField = number;
                    /*blockDivId = 'blockDiv'+fieldId+'_'+number;*/
                }
                var idField = '';
                var enaOpiton = '';
                var numberField = '';
                if(id > 0) {
                    blockDivId = blockDiv+fieldId+'_'+newKey;
                    //fieldId = row.field.fieldId;
                    idField = ' data-id="' + row.field.id + '"';
                    var poleId = row.field.id;
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
                var selectFieldName = 'selectField'+fieldId;
                var fieldName = row.field.name;
                var fieldField = field+' #'+blockDiv+row.field.fieldId+' .blockField';
                var fieldFieldName = field+' #'+blockDiv+row.field.fieldId+' .'+blockGroupClass;
                var poleFieldId = row.field.id;
                var dataField = row.field.childFields;
                var poleFieldFieldId = row.field.fieldId;
                if (row.field.fieldType === "GROUP_FIELDS") {
                    //console.log(row.field.childFields.length);
                    BlockDivClass = 'BlockDiv';
                    if(block && block === 1) {
                        BlockDivClass = 'BlockDivNew';
                    }
                    getFiledTypeGroupField (id, BlockDivClass, fieldFieldName, field, fieldId, dubKey, name, newKey, block, fieldName, i, fieldField, poleFieldId, dataField, poleFieldFieldId, mergePole);
                } else if (row.field.fieldType === "GROUP_CHECKBOX") {
                    BlockDivClass = 'BlockDivCheckBox';
                    if(block && block === 1) {
                        BlockDivClass = 'BlockDivCheckBoxNew';
                    }
                    getFiledTypeCheckBox ("GROUP_CHECKBOX", row.field, field, id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, newKey, dubKey, fieldId, idFiledInput, up, BlockDivClass, fieldFieldName, block, fieldName, fieldField, poleFieldId, dataField, poleFieldFieldId, name, fieldId, i, mergePole);
                } else {
                    getFieldType (row.field.fieldType, row.field, field, id, selectFieldName, '', numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, newKey, dubKey, fieldId, idFiledInput, '', up, 1);
                }
            }
        }).done(function(response) {
            var filedBlock = '#blockFields, #blockBlock, #btnSave, #btnWordFile';
            if(block && block === 1) {filedBlock = '#blockFieldsNew, #blockBlockNew, #btnSaveNew, #btnWordFileNew';}
            if (response.length == 0) {$(filedBlock).addClass('d-none');}
        });
    }

    // Получение стека из строки
    function getStack (url, pole, linksOld, block, id) {
        $.getJSON (url, function(data) {
            var rowChild = data;
            var emptyData = 1;
            if(id && id > 0) {
                if(pole && pole > 0) {
                    if(data.childFields[pole].field.childFields[0].fieldType == "GROUP_FIELDS") {
                        rowChild = data.childFields[pole].field.childFields[0].childFields;
                        var blockId = data.childFields[pole].field.childFields[0].fieldId;
                    } else {
                        rowChild = data.childFields[pole].field.childFields;
                        var blockId = data.childFields[pole].field.fieldId;
                    }
                }
                emptyData = 0;
            } else {
                if(pole && pole > 0) {
                    if(data[pole].field.childFields[0].fieldType == "GROUP_FIELDS") {
                        //rowChild =
                        // data[pole].field.childFields[0].childFields;
                        rowChild = data[pole].field.childFields[0].childFields;
                        var blockId = data[pole].field.childFields[0].fieldId;
                    } else {
                        rowChild = data[pole].field.childFields;
                        var blockId = data[pole].field.fieldId;
                    }
                }
            }
            groupNewFieldsValue (rowChild, '', blockId, linksOld, block, emptyData);
            //console.log(blockId, linksOld, block, emptyData);
        });
    }