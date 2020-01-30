// Список макетов таблиц
$(document).on("click", '.tableTemplates',  function(event) {
    event.preventDefault();
    var newEdit = $(this).attr('data-click');
    var tabId = $(this).attr('data-table');
    if(newEdit && newEdit == 1) {
        $('.addTableVal').append('' +
            '<div class="col-12 mt-2 dangerVoid">' +
            '   <div class="alert alert-danger">' +
            '       Внимание! При изменении макета таблицы все введенные данные будут удалены' +
            '   </div>' +
            '</div>');
    }
    $('#addTableSave').attr('data-table', tabId);
    $('#btnAddTable').modal('show');
    createOptions ('rest/profile/htmltables/', '#addTable1001', 'name', 'id', '', '');
});

// Получение данных в таблицу
function getTablesFields (url, pole) {
    $.ajax({
        url: url,
        dataType: 'json',
        async: false,
        success: function(data) {
            $(pole).append('<div class="editTable"><h6 class="my-3 text-center">'+data.name+'</h6>'+data.htmlContent+'</div>');
        }
    });
}

// Превью таблиц по выбору опции
$('#addTable1001').change(function() {
    $('#tableTemplateView').empty();
    var tableId = $('#addTable1001').val();
    if(tableId > 0) {
        getTablesFields('rest/profile/htmltables/'+tableId, '#tableTemplateView');
        $('#tableTemplateView table').addClass('table table-sm table-bordered').attr('width', '100%');
        $('#tableTemplateView table th').css({
            'text-align': 'center',
            'vertical-align': 'middle',
            'font-weight': 'bold'
        });
        $('#tableTemplateView table tbody tr:first td').css('text-align', 'center');
        $('#tableTemplateView table tbody tr').each(function() {
            $('td:first', this).css('text-align', 'center');
        });
    }
});

// Убираем превью макета таблицы при закрытии модального окна
$('#btnAddTable').on('hidden.bs.modal', function () {
    $('#tableTemplateView, .dangerVoid').empty();
    $('#addTable1001').val('').empty().html('' +
        '<div class="col-12 mb-3">' +
        '   <select data-placeholder="Выберите из справочника" class="chosen-select addTable1001" id="addTable1001" name="addTable1001" required>' +
        '       <option value="">Выберите из справочника</option>' +
        '   </select>' +
        '</div>');
});

// Добавляем таблицу
$('#addTableSave').click(function(event) {
    event.preventDefault();
    var tabId = $(this).attr('data-table');
    var newTabField = '#catalogTables'+tabId;
    $('#tableTemplateView, .dangerVoid').empty();
    $(newTabField+' .newTable .editTable').remove();
    $(newTabField+' .newTable .addTableRow').remove();
    var tableId = $('#addTable1001').val();
    $(newTabField).attr('data-value', tableId);
    $('#addTable1001').empty().html('' +
        '<div class="col-12 mb-3">' +
        '   <select data-placeholder="Выберите из справочника" class="chosen-select addTable1001" id="addTable1001" name="addTable1001" required>' +
        '       <option value="">Выберите из справочника</option>' +
        '   </select>' +
        '</div>');
    $('#btnAddTable').modal('hide');
    $(newTabField+' .tableTemplates').html('Изменить макет таблицы').attr('data-click', 1);
    if(tableId > 0) {
        $(newTabField+' .col-md-3').remove();
        $(newTabField+' .newTable').removeClass('col-md-9').addClass('col-md-12');
        getTablesFields('rest/profile/htmltables/' + tableId, newTabField+' .newTable');
        $(newTabField+' .newTable table').addClass('table table-sm table-bordered table-editable table-hover').attr('width', '100%');
        var sumTop = parseInt($(newTabField+' .newTable table tbody tr:first td:last').html());
        sumTop = sumTop+1;
        if($(newTabField+' .newTable table thead').hasClass('noNumber')) {
            $(newTabField+' .newTable table tbody tr:first').append(
                '<td class="deleteElem">' +
                '   <div class="btn btn-sm btn-danger table-remove rounded px-3 my-0">' +
                '       <i class="fas fa-trash"></i>' +
                '   </div>' +
                '</td>'
            );
        } else {
            $(newTabField+' .newTable table tbody tr:first').append('<td class="text-center deleteElem">'+sumTop+'</td>');
            $(newTabField+' .newTable table tbody tr:first td').css('text-align', 'center');
        }
        $(newTabField+' .newTable table thead tr:first').append('<th class="text-center deleteElem" rowspan="2">Удалить</th>');
        $(newTabField+' .newTable table tbody tr:not(:first)').append(
            '<td class="deleteElem">' +
            '   <div class="btn btn-sm btn-danger table-remove rounded px-3 my-0">' +
            '       <i class="fas fa-trash"></i>' +
            '   </div>' +
            '</td>'
        );
        $(newTabField+' .newTable').append('' +
            '<div class="row addTableRow">' +
            '   <div class="col-12 text-right">' +
            '       <div class="btn btn-primary btn-sm rounded addTableRow" data-table="'+tabId+'">' +
            '           <i class="fas fa-plus white-text mr-2"></i>Добавить строку' +
            '       </div>' +
            '   </div>' +
            '</div>'
        );
    }
    // Делаем столбцы редактируемыми
    $(newTabField+' .newTable table th').css({
        'text-align': 'center',
        'vertical-align': 'middle',
        'font-weight': 'bold'
    });
    $(newTabField+' .newTable table td').attr('contenteditable', true).addClass('content');
    //$(newTabField+' .newTable table tbody tr:first td').css('text-align', 'center');
    $(newTabField+' .newTable table tbody tr').each(function() {
        $('td:first', this).css('text-align', 'center');
    });
});

// Добавляем строку
$(document).on('click', '.addTableRow', function() {
    var tabId = $(this).attr('data-table');
    $('#catalogTables'+tabId+' .newTable table').find('tbody tr:last').clone().appendTo('#catalogTables'+tabId+' .newTable table').find('td:not(:last)').html('&nbsp;');
});

// Удаляем строку
$(document).on('click', '.table-remove', function() {
    $(this).parents('tr').detach();
});

// Возможность вставки данных в таблицу
/*$(document).on('paste', '.content', function(e) {
    var $this = $(this);
    $.each(e.originalEvent.clipboardData.items, function(i, v){
        if (v.type === 'text/plain'){
            v.getAsString(function(text){
                var x = $this.closest('td').index(),
                    y = $this.closest('tr').index(),
                    obj = {};
                text = text.trim('\r\n');
                $.each(text.split('\r\n'), function(i2, v2){
                    $.each(v2.split('\t'), function(i3, v3){
                        var row = y+i2, col = x+i3;
                        obj['cell-'+row+'-'+col] = v3;
                        $this.closest('table tbody').find('tr:eq('+row+') .content:eq('+col+')').val(v3);
                    });
                });
                console.log(JSON.stringify(obj));
            });
        }
    });
    return false;
});*/