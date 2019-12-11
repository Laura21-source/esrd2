    // Получение полей блока GROUP_FIELDS
    function groupNewFieldsValue (data, id, blockId, dubKey, dataBlock) {
        console.log(data, id, blockId, dubKey, dataBlock);
        var idField = ''; var idFiledInput = '';
        var blockDiv = 'blockDiv';
        var blockGroup = 'blockGroup';
        if(dataBlock == 1) {
            blockDiv = 'blockDivNew';
            blockGroup = 'blockGroupNew';
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
            var selectFieldName = 'selectField_'+blockId+'_'+dubKey+'_'+dataBlock+y;
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
            getFieldType (rowSelectField.fieldType, rowSelectField, blockGroup+dubKey+' .blockGroupFields', id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, y, dubKey, idField, idFiledInput, '','')
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
    function groupNew (id, field, fieldId, dubKey, name, newKey, block, nameBlock, poleId, agree) {
        var blockDiv = 'blockDiv';
        var addBlock = 'addBlock';
        var blockName = 'blockName';
        if(block && block === 1) {
            blockDiv = 'blockDivNew';
            addBlock = 'addBlockNew';
            blockName = 'blockNameNew';
        }
        blockDiv = blockDiv+fieldId;
        var dataId = '';
        if(id > 0) {}
        $(field).append(
            '<div class="card BlockDiv p-3" id="'+blockDiv+'" data-block="'+fieldId+'">' +
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

    // Получение стека полей
    function getNewFields (url, id, number, short, block, name, pole) {
        var field = '#blockBlock';
        var blockGroup = '#blockGroup';
        var blockDiv = 'blockDiv';
        if(block && block === 1) {
            field = '#blockBlockNew';
            blockGroup = '#blockGroupNew';
            blockDiv = 'blockDivNew';
        }
        //alert (field + ' - ' + blockGroup);
        return $.getJSON (url, function(data) {
            var rowChild = data;
            var agreeGroupFields = [];
            if(id > 0 && block !== 1) {rowChild = data.childFields;}
            if(id > 0) {number = 1;}
            if(pole && pole > 0) {rowChild = data[pole];}
            //console.log(rowChild);
            for(var i in rowChild) {
                var row = rowChild[i];
                if(pole && pole > 0) {row = rowChild;}
                // Переменная даты
                var valueDate = '';
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
                    fieldId = row.field.id;
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
                if (row.field.fieldType === "GROUP_FIELDS") {
                    if(id > 0) {
                        // Формиируем массив для отображения груповых полей у сформированного документа
                        var element = '';
                        /*element = {
                            "id" : row.field.fieldId,
                            "name" : row.field.name,
                            "field" : [row.field.childFields],
                            "poleId": row.field.id
                        }
                        agreeGroupFields.push(element);*/
                        if(agreeGroupFields.length == 0) {
                            element = {
                                "id" : row.field.fieldId,
                                "name" : row.field.name,
                                "field" : [row.field.childFields],
                                "poleId": row.field.id
                            }
                            agreeGroupFields.push(element);
                        } else {
                            for(var y in agreeGroupFields) {
                                //console.log(agreeGroupFields[y].field);
                                //console.log(agreeGroupFields);
                                //console.log(y+' - '+row.field.fieldId+' -
                                // '+agreeGroupFields[y].id);
                                if(row.field.fieldId == agreeGroupFields[y].id) {
                                    agreeGroupFields[y].field.push(row.field.childFields);
                                }  else {
                                    element = {
                                        "id" : row.field.fieldId,
                                        "name" : row.field.name,
                                        "field" : [row.field.childFields],
                                        "poleId": row.field.id
                                    }
                                    agreeGroupFields.push(element);
                                }
                            }
                        }
                    } else {
                        var nameBlock = row.field.name;
                        groupNew (id, field, fieldId, dubKey, name, newKey, block, nameBlock, i, '');
                        groupNewFieldsValue (row.field.childFields, id, fieldId, dubKey, block);
                    }
                } else if (row.field.fieldType === "GROUP_CHECKBOX") {
                    getFiledTypeGroup ("GROUP_CHECKBOX", row.field, field, id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, newKey, dubKey, fieldId, idFiledInput, 1)
                } else {
                    getFieldType (row.field.fieldType, row.field, field, id, selectFieldName, '', numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, newKey, dubKey, fieldId, idFiledInput, '',1)
                }
            }
            // Заполняем данные груповых полей из формированного массива
            /*$.each(agreeGroupFields, function(key, data) {
                $.each(data, function(index,value) {
                    console.log('Статья: id = ' + value['id'] + '; Название' + ' = '+ value['name']);
                });
            });*/

            if(id > 0 && agreeGroupFields.length > 0) {
                //console.log(agreeGroupFields);
                for(var h in agreeGroupFields) {
                    var rowGroup = agreeGroupFields[h];
                    var t = parseInt(h)+1;
                    var nameBlock = rowGroup.name;
                    groupNew (id, field, rowGroup.id, dubKey, name, t, block, nameBlock, t, 1);
                    for(var v in rowGroup.field) {
                        var rowGroupChild = rowGroup.field[v];
                        var dataId = rowGroup.poleId;
                        var newDubkey = parseInt(v)+1;
                        groupNewFields ('#'+blockDiv+rowGroup.id+' .blockField', rowGroup.id, newDubkey, name, newDubkey, block, id, dataId);
                        //console.log(rowGroupChild);
                        groupNewFieldsValue (rowGroupChild, id, rowGroup.id, newDubkey, block);
                    }
                }
            }
        }).done(function(response) {
            var filedBlock = '#blockFields, #blockBlock, #btnSave, #btnWordFile';
            if(block && block === 1) {filedBlock = '#blockFieldsNew, #blockBlockNew, #btnSaveNew, #btnWordFileNew';}
            if (response.length == 0) {$(filedBlock).addClass('d-none');}
        });
    }

    // Получение стека из строки
    function getStack (url, pole, linksOld, block) {
        //console.log(url, pole, linksOld);
        $.getJSON (url, function(data) {
            var rowChild = data;
            if(pole && pole > 0) {rowChild = data[pole].field.childFields;}
            var blockId = data[pole].field.fieldId;
            groupNewFieldsValue (rowChild, '', blockId, linksOld, block);
        });
    }