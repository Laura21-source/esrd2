$(function() {
  "use strict";
  // Эффекты MDB при появлении
  new WOW().init();

  // Боковая панель меню
  $(".button-collapse").sideNav();

  // Привязка к верхнему краю документа
  $(".sticky-content").sticky({
    topSpacing: 0,
    minWidth: 992,
    stopper: "#footer",
  });

  // Добавить вопрос, получателя
  $('.addGroup').click(function(){
    var links = $("[data-block='1']").length;
    var links1 = links + 1;
    $('#blockGroup').clone(true).attr('id', 'blockGroup' + links).appendTo('.marginBlock');
    // Добавляем к id полей номер добавляемого вопроса
    $('#blockGroup' + links + ' #nameGroup').attr('id', 'nameGroup' + links);
    $('#blockGroup' + links + ' #delGroup').attr('id', 'delGroup' + links);
    $('#blockGroup' + links).attr('data-field', links);
    $('#blockGroup' + links).find('select').each(function(){
      var selectName = $(this).attr("name");
      $('#blockGroup' + links + selectName).attr('name', selectName + links);
    });
    $('#blockGroup' + links + ' *').prop('selected', false);
    $('#nameGroup' + links).html("Вопрос " + links1);
    $('#delGroup' + links).removeClass('d-none');
  });

  // Удалить вопрос
  $('.delGroup').click(function(){
    var id = $(this).attr("id");
    id = id.substr(8);
    alert('#blockGroup' + id);
    $('#blockGroup' + id).remove();
  });

  // Отмена закрытия полей
  $("#editDocument").click(function(e){
    e.preventDefault();
    $(this).addClass("d-none");
    $("#saveDocument, #cancelDocument").removeClass("d-none");
    $("#closeDocument").addClass("d-none");
    $("select, #inputDate, #inputTime, .addGroup").attr("disabled",false);
  });

  $("#saveDocument").click(function(e){
    e.preventDefault();
    $(this).addClass("d-none");
    $("#cancelDocument").addClass("d-none");
    $("#editDocument").removeClass("d-none");
    $("#closeDocument").removeClass("d-none");
    $("select, #inputDate, #inputTime, .addGroup").attr("disabled",true);
  });

  // Сортируемые таблицы
  /*$('#dataTable').DataTable({
    "order": [[ 3, "desc" ]]
  });
  $('.dataTables_length').addClass('bs-select');*/

});