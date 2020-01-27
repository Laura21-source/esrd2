// Дублировать GROUP_FIELDS
$(document).on("click", ".cloneGroup", function () {
    var id = $(this).attr('data-group');
    var poleId = $(this).attr('data-clone');
    var idBlock = '#blockDiv'+id+' .blockField';
    var newField = $('#blockDiv'+id+' #blockGroup'+poleId+' .blockGroupFields').children().clone();
    //alert(idBlock);
    var linksOld = parseInt($(idBlock + " .blockGroup [data-block='1']").length);
    linksOld = linksOld + 1;
    // Найдем максимальное значение атрибута
    var links = null;
    $(idBlock+" .blockGroup").each(function () {
        var value = parseInt($(this).attr('data-move'));
        links = (value > links) ? value : links;
    });
    links = links + 1;
    groupNewFields(idBlock, id, links, '', linksOld, '');
    $('#blockDiv'+id+' #blockGroup'+links+' .blockGroupFields').append(newField);
    window.location.hash = 'blockGroup'+links;
    window.location.href;
    // Переписываем INPUTS
    var newInput = $('#blockDiv'+id+' #blockGroup'+links).find('input.form-control');
    newInput.each(function() {
        var oldId = $(this).attr('id');
        var newId = oldId.replace("_"+poleId+"_","_"+links+"_");
        $(this).attr({
            'id': newId,
            'name': newId
        });
    });
    // Переписываем CHECKBOX
    var newCheckboxBlock = $('#blockDiv'+id+' #blockGroup'+links).find('.checkboxBlock');
    newCheckboxBlock.each(function() {
        var oldId = $(this).attr('id');
        var newIdBlock = oldId.replace("_"+poleId,"_"+links);
        var newId = newIdBlock.replace("Field","");
        $(this).attr({
            'id': newIdBlock,
            'name': newIdBlock
        });
        $('.form-check-input', this).attr({
            'id': newId,
            'name': newId
        });
        $('label', this).attr({
            'for': newId
        });
        $('.childBox', this).attr({
            'id': newId+'BlockDiv'
        });
        checkedFields ('#'+newId, '#'+newId+'BlockDiv');
    });
    // Переписываем поля SELECT CHOSEN
    var newSelect = $('#blockDiv'+id+' #blockGroup'+links).find('select');
    newSelect.each(function() {
        var oldId = $(this).attr('id');
        var newId = oldId.replace("selectField"+id+"_"+poleId+"_","selectField"+id+"_"+links+"_");
        $('#blockDiv'+id+' #blockGroup'+links+' #'+oldId+'_chosen').remove();
        var valueElement = $('#'+oldId).val();
        $(this).attr({
            'id': newId,
            'name': newId
        });
        //alert('#'+newId);
        $('#'+newId).chosen({
            width: "100%",
            no_results_text: "Ничего не найдено!"
        });
        $('#'+newId).val(valueElement).trigger("chosen:updated");
    });
    // Переписываем поля HTML Tables
    var newTable = $('#blockDiv'+id+' #blockGroup'+links).find('.tableHtml');
    newTable.each(function() {
        var oldId = $(this).attr('id');
        var newId = parseInt(oldId.substr( 13))+1;
        var newString = "catalogTables"+newId;
        $(this).attr({
            'id': newString
        });
        $('#'+newString+' .btn.addTableRow').attr('data-table', newId);
        $('#'+newString+' .btn.tableTemplates').attr('data-table', newId);
    });
});

// Дублировать GROUP_FIELDS в дочернем документе
$(document).on("click", ".cloneGroupNew", function () {
    var id = $(this).attr('data-group');
    var poleId = $(this).attr('data-clone');
    var idBlock = '#blockDivNew'+id+' .blockField';
    var newField = $('#blockDivNew'+id+' #blockGroupNew'+poleId+' .blockGroupFields').children().clone();
    //alert(idBlock);
    var linksOld = parseInt($(idBlock + " .blockGroupNew [data-block='2']").length);
    linksOld = linksOld + 1;
    // Найдем максимальное значение атрибута
    var links = null;
    $(idBlock+" .blockGroupNew").each(function () {
        var value = parseInt($(this).attr('data-move'));
        links = (value > links) ? value : links;
    });
    links = links + 1;
    groupNewFields(idBlock, id, links, '', linksOld, 1);
    $('#blockDivNew'+id+' #blockGroupNew'+links+' .blockGroupFields').append(newField);
    window.location.hash = 'blockGroupNew'+links;
    window.location.href;
    // Переписываем INPUTS
    var newInput = $('#blockDivNew'+id+' #blockGroupNew'+links).find('input.form-control');
    newInput.each(function() {
        var oldId = $(this).attr('id');
        var newId = oldId.replace("_"+poleId+"_","_"+links+"_");
        $(this).attr({
            'id': newId,
            'name': newId
        });
    });
    // Переписываем CHECKBOX
    var newCheckboxBlock = $('#blockDivNew'+id+' #blockGroupNew'+links).find('.checkboxBlock');
    newCheckboxBlock.each(function() {
        var oldId = $(this).attr('id');
        var newIdBlock = oldId.replace("_"+poleId,"_"+links);
        var newId = newIdBlock.replace("Field","");
        $(this).attr({
            'id': newIdBlock,
            'name': newIdBlock
        });
        $('.form-check-input', this).attr({
            'id': newId,
            'name': newId
        });
        $('label', this).attr({
            'for': newId
        });
        $('.childBox', this).attr({
            'id': newId+'BlockDiv'
        });
        checkedFields ('#'+newId, '#'+newId+'BlockDiv');
    });
    // Переписываем поля SELECT CHOSEN
    var newSelect = $('#blockDivNew'+id+' #blockGroupNew'+links).find('select');
    newSelect.each(function() {
        var oldId = $(this).attr('id');
        var newId = oldId.replace("selectField"+id+"_"+poleId+"_","selectField"+id+"_"+links+"_");
        $('#blockDivNew'+id+' #blockGroupNew'+links+' #'+oldId+'_chosen').remove();
        var valueElement = $('#'+oldId).val();
        $(this).attr({
            'id': newId,
            'name': newId
        });
        //alert('#'+newId);
        $('#'+newId).chosen({
            width: "100%",
            no_results_text: "Ничего не найдено!"
        });
        $('#'+newId).val(valueElement).trigger("chosen:updated");
    });
    // Переписываем поля HTML Tables
    var newTable = $('#blockDivNew'+id+' #blockGroupNew'+links).find('.tableHtml');
    newTable.each(function() {
        var oldId = $(this).attr('id');
        var newId = parseInt(oldId.substr( 13))+1;
        var newString = "catalogTables"+newId;
        $(this).attr({
            'id': newString
        });
        $('#'+newString+' .btn.addTableRow').attr('data-table', newId);
        $('#'+newString+' .btn.tableTemplates').attr('data-table', newId);
    });
});
