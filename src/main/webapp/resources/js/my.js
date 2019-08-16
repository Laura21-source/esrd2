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
  $('.addQuestion').click(function(){
    var links = $("[req='true']").length;
    var links1 = links + 1;
    $('#blockQuestion').clone(true).attr('id', 'blockQuestion' + links).appendTo('.marginBlock');
    // Добавляем к id полей номер добавляемого вопроса
    $('#blockQuestion' + links + ' #nameQuestion').attr('id', 'nameQuestion' + links);
    $('#blockQuestion' + links + ' #delQuestion').attr('id', 'delQuestion' + links);
    $('#blockQuestion' + links).find('select').each(function(){
      var selectAttr = $(this).attr("name");
      $('#blockQuestion' + links + selectAttr).attr('name', selectAttr + links);
    });
    $('#blockQuestion' + links + ' select option').prop('selected', false);
    $('#nameQuestion' + links).html("Вопрос " + links1);
    $('#delQuestion' + links).removeClass('d-none');
  });
  $('.addRecipient').click(function(){
    var links = $("[req='true']").length;
    var links1 = links + 1;
    $('#blockRecipient').clone(true).attr('id', 'blockRecipient' + links).appendTo('.marginBlock');
    $('#blockRecipient' + links + ' #nameRecipient').attr('id', 'nameRecipient' + links);
    $('#blockRecipient' + links + ' #delRecipient').attr('id', 'delRecipient' + links);
    $('#blockRecipient' + links + ' select option').prop('selected', false);
    $('#nameRecipient' + links).html("Получатель " + links1);
    $('#delRecipient' + links).removeClass('d-none');
  });

  // Удалить вопрос, получателя
  $('.delQuestion').click(function(){
    var id = $(this).attr("id");
    id = id.substr(11);
    $('#blockQuestion' + id).remove();
  });
  $('.delRecipient').click(function(){
    var id = $(this).attr("id");
    id = id.substr(12);
    $('#blockRecipient' + id).remove();
  });

  // Отмена закрытия полей
  $("#editDocument").click(function(e){
    e.preventDefault();
    $(this).addClass("d-none");
    $("#saveDocument").removeClass("d-none");
    $("#closeDocument").addClass("d-none");
    $("option, #inputDate, #inputTime, .addQuestion").attr("disabled",false);
  });

  $("#saveDocument").click(function(e){
    e.preventDefault();
    $(this).addClass("d-none");
    $("#editDocument").removeClass("d-none");
    $("#closeDocument").removeClass("d-none");
    $("option, #inputDate, #inputTime, .addQuestion").attr("disabled",true);
  });

  $(".reloadButton").click(function(){
    location.reload();
  });

});