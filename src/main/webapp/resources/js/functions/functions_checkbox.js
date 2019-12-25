    // Получение данных поля чекбокса
    function getFiledTypeCheckBox (type, data, pole, id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, newKey, dubKey, idField, idFiledInput, up, BlockDivClass, fieldFieldName, block, fieldName, fieldField, poleFieldId, dataField, poleFieldFieldId, name, fieldId, i) {
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
                '<div class="my-3 card p-3">' +
                '   <div class="'+nameBlock+' text-left">' +
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
                $('.'+nameBlock).append('<div id="'+nameBlock+'BlockDiv"' +
                    ' class="childBox my-4 d-none"></div>');
                for(var y in data.childFields) {
                    var checkField = data.childFields[y];
                    idField = null;
                    if (id > 0) {
                        idField = checkField.id;
                        numberField = checkField.valueInt;
                    }
                    var elementField = pole+' #'+nameBlock+'BlockDiv';
                    var textId = y+1;
                    // GROUP_FIELDS
                    if (checkField.fieldType === "GROUP_FIELDS") {
                        /*for(var m in checkField.childFields) {
                            var childCheckField = checkField.childFields[m];*/
                            getFiledTypeGroupField (id, BlockDivClass, fieldFieldName, elementField, fieldId, dubKey, name, newKey, block, fieldName, i, fieldField, poleFieldId, checkField.childFields, poleFieldFieldId);
                       /* }*/
                    } else {
                        getFieldType (checkField.fieldType, checkField, elementField, id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, y, dubKey, idField, idFiledInput, 'checkClass', 0, 1);
                    }
                    textId = textId+1;
                }
            }
            checkedFields ('#'+nameBlock, '#'+nameBlock+'BlockDiv');
            if (data.valueInt > 0) {$('.childBox').removeClass('d-none');}
        }
    }