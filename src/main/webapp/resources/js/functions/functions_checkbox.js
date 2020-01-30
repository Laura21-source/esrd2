// Получение данных поля чекбокса
function getFiledTypeCheckBox (type, data, pole, id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, newKey, dubKey, idField, idFiledInput, up, BlockDivClass, fieldFieldName, block, fieldName, fieldField, poleFieldId, dataField, poleFieldFieldId, name, fieldId, i, mergePole) {
    var classElem = ''; var mergePoleName = ''; var disabledAttr = '';
    if(mergePole && mergePole != '') {
        mergePoleName = mergePole.replace('#', '');
        disabledAttr = ' disabled';
    }
    if(up == 1) {classElem = ' upElem';}
    if(up == 0) {classElem = ' blockElement';}
    var blockGroupField =  '.blockGroup';
    var nameBlock = mergePoleName+data.tag+data.fieldId+'_'+dubKey;
    if(block == 1) {
        if(up == 1) {classElem = ' upElemNew';}
        if(up == 0) {classElem = ' blockElementNew';}
        blockGroupField =  '.blockGroupNew';
        nameBlock = mergePoleName+data.tag+'New'+data.fieldId+'_'+dubKey;
    }
    var valueInt = 'value="0"';
    var checekCheckBox = '';
    if (data.valueInt > 0) {
        valueInt = 'value="'+data.valueInt+'"';
        checekCheckBox = ' checked';
    }
    var idData = '';
    if (id > 0) {idData = ' data-id="'+data.id+'"';}
    $(pole).append(
        '<div class="my-3 card p-3 text-left">' +
        '   <div id="'+nameBlock+'Field" class="checkboxBlock">' +
        '       <div class="form-check">' +
        '           <input class="form-check-input'+classElem+'"' +
        ' id="'+nameBlock+'" type="checkbox"' +
        ' data-field="'+data.fieldId+'"'+idData +
        ' name="'+nameBlock+'" '+disabledAttr+
        ' '+valueInt+checekCheckBox+'>' +
        '           <label class="form-check-label text-muted' +
        ' text-left" for="'+nameBlock+'">'+data.name+'</label>' +
        '       </div>' +
        '   </div>' +
        '</div>'
    );
    if(data.childFields.length > 0) {
        $('#'+nameBlock+'Field').append('<div id="'+nameBlock+'BlockDiv" class="childBox my-4 d-none"></div>');
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
                var fieldIdGroup = checkField.fieldId;
                var fieldName = checkField.name;
                fieldFieldName = '#'+blockGroup+fieldIdGroup+' '+blockGroupField;
                poleFieldFieldId = checkField.fieldId;
                fieldField = '#'+blockGroup+fieldIdGroup+' .blockField';
                getFiledTypeGroupFieldCheckBox (id, BlockDivClass, fieldFieldName, elementField, fieldIdGroup, dubKey, name, newKey, block, fieldName, i, fieldField, poleFieldId, checkField.childFields, poleFieldFieldId);
            } else {
                getFieldType (checkField.fieldType, checkField, elementField, id, selectFieldName, blockGroup, numberField, parentBlock, parentCatalog, requiredSup, requiredValidate, enaOpiton, required, y, dubKey, idField, idFiledInput, 'checkClass', 0, 1);
            }
            textId = textId+1;
        }
    }
    checkedFields ('#'+nameBlock, '#'+nameBlock+'BlockDiv');
    if (data.valueInt > 0) {$('#'+nameBlock+'BlockDiv'+'.childBox').removeClass('d-none');}
}