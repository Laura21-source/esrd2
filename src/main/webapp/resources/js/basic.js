$(function() {
  "use strict";
  // Эффекты MDB при появлении
  new WOW().init();

  // Боковая панель меню
  $(".button-collapse").sideNav();

  // Привязка к верхнему краю документа
  /*$(".sticky-content").sticky({
    topSpacing: 0,
    minWidth: 992,
    stopper: "#footer",
  });*/

  // Всплывающие подсказки
  $('[data-toggle="tooltip"]').tooltip();

  // Добавить вопрос, получателя
  $(document).on("click", ".addGroup", function() {
    var links = $("[data-block='1']").length;
    var links1 = links + 1;
    //var id = getId();
    var asd = $("#selectType").val();
    var newField = getDownFields("rest/profile/doctypes/" + asd + "/fields", '', links1);
    $('#newBlockGroup').append(newField);
  });

  // Удалить вопрос
  $(document).on("click", ".delGroup", function() {
    var id = $(this).attr("id");
    id = id.substr(8);
    $('#blockGroup' + id).remove();
  });

  // Отправка файла на сервер
  $(document).on("change", "#inputFile", function() {
    //var filename = $('#inputFile').val();
    //var data = $(this).val();
    var data = new FormData($('.registrationForm')[0]);
    $.ajax({
      type: "POST",
      url: "rest/profile/docs/uploadfile",
      enctype: 'multipart/form-data',
      processData: false, //prevent jQuery from automatically transforming the data into a query string
      contentType: false,
      cache: false,
      timeout: 600000,
      data: data
    });
  });

  $('#btnLoadPDF').on("click", function(e) {
    e.preventDefault();
    $(".bigLoader").removeClass("d-none").fadeIn(500);
    var data = new FormData($('.registrationForm')[0]);
    $.ajax({
      type: "POST",
      url: "rest/profile/docs/uploadfile",
      enctype: 'multipart/form-data',
      processData: false, //prevent jQuery from automatically transforming the data into a query string
      contentType: false,
      cache: false,
      timeout: 600000,
      data: data,
      success: function (data) {
        var dataFile =  data.fileUrl +".pdf";
        $(".bigLoader").addClass("d-none").fadeOut(1000);
        $('.pdfSRC').attr('src', dataFile);
      },
      error: function () {
        alert("Error!");
      }
    });
  });

  // Валидация формы
    window.addEventListener('load', function() {
// Fetch all the forms we want to apply custom Bootstrap validation styles to
      var forms = document.getElementsByClassName('needs-validation');
// Loop over them and prevent submission
      var validation = Array.prototype.filter.call(forms, function(form) {
        form.addEventListener('submit', function(event) {
          if (form.checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
          }
          form.classList.add('was-validated');
        }, false);
      });
    }, false);

  // Добавление элемента в список организаций
  $('.btnAddElement').click(function(e){
    e.preventDefault();
    $(".bigFormLoader").removeClass("d-none").fadeIn(500);
    $('.addElementForm').addClass('d-none');
    // Формируем JSON из полей
    //var dataField = [];
    var dataField = {
      "id" : null,
      "shortName" : $('#shortName').val(),
      "fullName" : $('#fullName').val(),
      "ogrn" : $('#ogrn').val(),
      "inn" : $('#inn').val(),
      "kpp" : $('#kpp').val(),
      "address" : $('#address').val(),
      "fioManager" : $('#fioManager').val(),
      "positionManager" : $('#positionManager').val()
    };
    //dataField.push(field);
    var data = JSON.stringify(dataField);
    console.log(data);
    $.ajax({
      type: "POST",
      url: "rest/profile/organizations",
      data: data,
      success: function (data) {
        $(".bigFormLoader").addClass("d-none").fadeOut(1000);
        $('.addElementForm').removeClass('d-none');
      },
      error: function () {
        alert("Error!");
        $(".bigFormLoader").addClass("d-none").fadeOut(1000);
        $('.addElementForm').removeClass('d-none');
      }
    });
  });
  // Очищаем форму при закрытии модального окна
  $('#addElement').on('hidden.bs.modal', function() {
    $('.addElementForm input').val('');
  });
  // Отмена закрытия полей
  /*$("#editDocument").on("click", function(e) {
    e.preventDefault();
    $(this).addClass("d-none");
    $("#saveDocument, #cancelDocument").removeClass("d-none");
    $("#closeDocument").addClass("d-none");
    $("select, #inputDate, #inputTime, .addGroup").attr("disabled",false);
  });*/

  /*$("#saveDocument").on("click", function(e) {
    e.preventDefault();
    $(this).addClass("d-none");
    $("#cancelDocument").addClass("d-none");
    $("#editDocument").removeClass("d-none");
    $("#closeDocument").removeClass("d-none");
    $("select, #inputDate, #inputTime, .addGroup").attr("disabled",true);
  });*/
});