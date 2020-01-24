// Дублировать GROUP_FIELDS
$(document).on("click", ".cloneGroup", function () {
    var id = $(this).attr('data-group');
    var poleId = $(this).attr('data-clone');
    var idBlock = '#blockDiv' + id + ' .blockField';
    var newField = $('#blockDiv' + id + ' #blockGroup' + poleId + ' .blockGroupFields').children().clone();
    //alert(idBlock);
    var linksOld = parseInt($(idBlock + " .blockGroup [data-block='1']").length);
    //var pole = $(this).attr('data-value');
    linksOld = linksOld + 1;
    // Найдем максимальное значение атрибута
    var links = null;
    $(idBlock + " .blockGroup").each(function () {
        var value = parseInt($(this).attr('data-move'));
        links = (value > links) ? value : links;
    });
    links = links + 1;
    groupNewFields(idBlock, id, links, '', linksOld, '');
    $('#blockDiv' + id + ' #blockGroup' + links + ' .blockGroupFields').append(newField);
    window.location.hash = 'blockGroup' + links;
    window.location.href;
    // Переписываем INPUTS
    var newInput = $('#blockDiv' + id + ' #blockGroup'+links).find('input.form-control');
    newInput.each(function() {
        var oldId = $(this).attr('id');
        //var oldIdNumber = oldId.substr(10).split( "_", 1);
        var newId = oldId.replace("_"+poleId+"_","_"+links+"_");
        $(this).attr({
            'id': newId,
            'name': newId
        });
    });
    // Переписываем CHECKBOX
    var newCheckboxBlock = $('#blockDiv' + id + ' #blockGroup'+links).find('.checkboxBlock');
    newCheckboxBlock.each(function() {
        var oldId = $(this).attr('id');
        //var oldIdNumber = oldId.substr(10).split( "_", 1);
        var newId = oldId.replace("_"+poleId,"_"+links);
        $(this).attr({
            'id': newId,
            'name': newId
        });
        $('.form-check-input', this).attr({
            'id': newId+'Field',
            'name': newId+'Field'
        });
        $('label', this).attr({
            'for': newId+'Field'
        });
        $('.childBox', this).attr({
            'id': newId+'BlockDiv'
        });
        checkedFields ('#'+newId+'Field', '#'+newId+'BlockDiv');
    });
    // Переписываем поля CHOSEN
    /*var newChosen = $('#blockDiv' + id + ' #blockGroup'+links).find('div.chosen-container');
    newChosen.each(function() {
        var oldId = $(this).attr('id');
        //var oldIdNumber = oldId.substr(12).split( "_", 1);
        var newId = oldId.replace("selectField"+id+"_"+poleId+"_","selectField"+id+"_"+links+"_");
        $(this).attr('id', newId);
        $(this).removeClass('chosen-container-active');
        //alert('#'+newId);
        //$('#'+newId).trigger("chosen:updated");
    });*/
    // Переписываем поля SELECT
    //var newSelect = $('#blockDiv' + id + ' #blockGroup'+links).find('select');
    var newSelect = $('#blockDiv' + id + ' #blockGroup'+links).find('select');
    newSelect.each(function() {
        var oldId = $(this).attr('id');
        //var oldIdNumber = oldId.substr(12).split( "_", 1);
        var newId = oldId.replace("selectField"+id+"_"+poleId+"_","selectField"+id+"_"+links+"_");
        $('#blockDiv' + id + ' #blockGroup'+links + ' #'+oldId+'_chosen').remove();
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

});

