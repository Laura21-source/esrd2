// Удалить блок
$(document).on("click", ".delGroup", function() {
    var id = $(this).attr("id");
    id = id.substr(8);
    var blockId = $(this).attr('data-parent');
    $('#btnDeleteBlock').attr({
        'data-delete':'blockGroup'+id,
        'data-block': 'blockDiv'+blockId,
        'block-id': 0
    });
});

// Удалить блок во вторичном документе
$(document).on("click", ".delGroupNew", function() {
    var id = $(this).attr("id");
    id = id.substr(11);
    var blockId = $(this).attr('data-parent');
    $('#btnDeleteBlock').attr({
        'data-delete':'blockGroupNew'+id,
        'data-block': 'blockDivNew'+blockId,
        'block-id': 1
    });
});

// Удаление блока при нажатии OK в модальном окне
$(document).on('click', '#btnDeleteBlock', function() {
    var id = $(this).attr('data-delete');
    var blockId = $(this).attr('data-block');
    var idBlock = $(this).attr('block-id');
    $('#deleteBlock').modal('hide');
    // Удаляем нужный блок
    $('#'+blockId+' '+'#'+id).remove();
    // Переименовываем название блоков и атрибуты data-field
    var i = 1;
    if(idBlock == 0) {
        $('#'+blockId+' .blockField .blockGroup').each(function() {
            $(this).attr('data-field', i);
            $('.nameGroup', this).html('Блок '+i);
            i = i+1;
        });
    } else if(idBlock == 1) {
        $('#'+blockId+' .blockField .blockGroupNew').each(function() {
            $(this).attr('data-field', i);
            $('.nameGroupNew', this).html('Блок '+i);
            i = i+1;
        });
    }

});

// Удалить пользователя
$(document).on("click", ".delUser", function() {
    var id = $(this).attr("id");
    id = id.substr(7);
    $('#blockUser' + id).remove();
});

// Удалить пользователя во вторичном документе
$(document).on("click", ".delUserNew", function() {
    var id = $(this).attr("id");
    id = id.substr(9);
    $('#blockUserNew' + id).remove();
});