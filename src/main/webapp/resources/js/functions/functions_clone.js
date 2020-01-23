// Дублировать GROUP_FIELDS
$(document).on("click", ".cloneGroup", function () {
    var id = $(this).attr('data-group');
    var poleId = $(this).attr('data-clone');
    var idBlock = '#blockDiv' + id + ' .blockField';
    var newField = $('#blockDiv' + id + ' #blockGroup' + poleId + ' .blockGroupFields').clone(true);
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
        alert('#'+newId+'Field'+' #'+newId+'BlockDiv');
        checkedFields ('#'+newId+'Field', '#'+newId+'BlockDiv');
    });
    // Переписываем поля SELECT
    var newSelect = $('#blockDiv' + id + ' #blockGroup'+links).find('select');
    newSelect.each(function() {
        var oldId = $(this).attr('id');
        //var oldIdNumber = oldId.substr(12).split( "_", 1);
        var newId = oldId.replace("selectField"+id+"_"+poleId+"_","selectField"+id+"_"+links+"_");
        $(this).attr({
            'id': newId,
            'name': newId
        });
        //alert('#'+newId);
        $('#'+newId).chosen({
            width: "100%",
            no_results_text: "Ничего не найдено!"
        });
    });
    // Переписываем поля CHOSEN
    var newChosen = $('#blockDiv' + id + ' #blockGroup'+links).find('div.chosen-container');
    newChosen.each(function() {
        var oldId = $(this).attr('id');
        //var oldIdNumber = oldId.substr(12).split( "_", 1);
        var newId = oldId.replace("selectField"+id+"_"+poleId+"_","selectField"+id+"_"+links+"_");
        $(this).attr('id', newId);
        $('#'+newId).trigger("chosen:updated");
    });
});

/*
$(document).on("click", ".cloneGroup", function() {
    var linksOld = parseInt($("[data-block='1']").length);
    linksOld = linksOld + 1;
    var id = $(this).attr("id");
    id = id.substr(10);
    var links = $(".blockGroup:last").attr('id');
    links = parseInt(links.substr(10));
    var links1 = links + 1;
    var newField = $('#blockGroup'+id).clone(true);
    //var asd = $("#selectType").val();
    //var newField = getDownFields("rest/profile/doctypes/" + asd + "/fields", '', links1);
    $('#newBlockGroup').append(newField);
    $(".blockGroup:last").attr('id','blockGroup'+links1);
    $('#blockGroup'+links1+' .nameGroup').html('Блок '+linksOld);
    $('#blockGroup'+links1+' .cloneGroup').attr('id', 'cloneGroup'+links1);
    $('#blockGroup'+links1+' .delGroup').attr('id', 'delGroup'+links1);
    $('#blockGroup'+links1).attr('data-field',links1);
    window.location.hash = 'blockGroup'+links1;
    window.location.href;
    // Переписываем INPUTS
    var newInput = $('#blockGroup'+links1).find('input.form-control');
    newInput.each(function() {
        var oldId = $(this).attr('id');
        var oldIdNumber = oldId.substr(10).split( "_", 1);
        var newId = oldId.replace("_"+oldIdNumber+"_","_"+links1+"_");
        $(this).attr({
            'id': newId,
            'name': newId
        });
    });
    // Переписываем поля SELECT
    var newSelect = $('#blockGroup'+links1).find('select');
    newSelect.each(function() {
        var oldId = $(this).attr('id');
        var oldIdNumber = oldId.substr(12).split( "_", 1);
        var newId = oldId.replace("selectField_"+oldIdNumber+"_","selectField_"+links1+"_");
        $(this).attr({
            'id': newId,
            'name': newId
        });
        $('#'+newId).trigger("chosen:updated");
    });
    // Переписываем поля CHOSEN
    var newChosen = $('#blockGroup'+links1).find('div.chosen-container');
    newChosen.each(function() {
        var oldId = $(this).attr('id');
        var oldIdNumber = oldId.substr(12).split( "_", 1);
        var newId = oldId.replace("selectField_"+oldIdNumber+"_","selectField_"+links1+"_");
        $(this).attr('id', newId);
        $('#'+newId).trigger("chosen:updated");
    });
});*/
