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
    //$('#blockQuestion' + links + ' #selectTheme').attr('id', 'selectTheme' + links);
    //$('#blockQuestion' + links + ' #selectOrganisation').attr('id', 'selectOrganisation' + links);
    //$('#blockQuestion' + links + ' #selectCrucial').attr('id', 'selectCrucial' + links);
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
    //$(".mdb-select li").removeClass("disabled");
  });

  $("#saveDocument").click(function(e){
    e.preventDefault();
    $(this).addClass("d-none");
    $("#editDocument").removeClass("d-none");
    $("#closeDocument").removeClass("d-none");
    $("option, #inputDate, #inputTime, .addQuestion").attr("disabled",true);
    //$(".mdb-select li").addClass("disabled");
  });

  // Кнопка отправки
  $('.submitBtn').click(function(event){
    event.preventDefault();
    $('#formField').css('display','none').fadeOut(1000);
  });

});