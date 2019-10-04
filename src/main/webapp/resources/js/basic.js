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

  // Валидация
  /*window.addEventListener('load', function() {
    var forms = document.getElementsByClassName('needs-validation');
    var validation = Array.prototype.filter.call(forms, function(form) {
      form.addEventListener('submit', function(event) {
        if (form.checkValidity() === false) {
          event.preventDefault();
          event.stopPropagation();
        }
        form.classList.add('was-validated');
      }, false);
    });
  }, false);*/

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

  // Заполнение данных организации
  $('#btnEgrul').click(function() {
    var inn = $('.addElementForm #inn').val();
    return getValueOrganisation ('rest/profile/organizations/getEGRULData?INN='+inn, '.addElementForm');
  });

  // Добавление элемента в список организаций
  $('.btnAddElement').click(function(event) {
    var number = $('.addElement').attr('data-catalog');
    event.preventDefault();
    var forms = $('.addElementForm');
    var formsValue = $('.addElementForm input,.addElementForm textarea,.addElementForm select').filter('[required]');
    $(forms).addClass('was-validated');
    event.preventDefault();
    var checkField = checkValidation(formsValue);
    if(checkField === false) {
      event.stopPropagation();
    } else {
      $('.bigFormLoader').removeClass("d-none").fadeIn();
      $('.addElementForm').addClass('d-none');
      // Формируем JSON из полей
      var dataField = {
        "id": null,
        "shortName": $('#shortName').val(),
        "fullName": $('#fullName').val(),
        "ogrn": $('#ogrn').val(),
        "inn": $('#inn').val(),
        "kpp": $('#kpp').val(),
        "address": $('#address').val(),
        "fioManager": $('#fioManager').val(),
        "positionManager": $('#positionManager').val()
      };
      var data = JSON.stringify(dataField);
      console.log("number - " + number);
      $.ajax({
        type: "POST",
        url: "rest/profile/organizations",
        data: data,
        contentType: 'application/json; charset=utf-8',
        success: function (data) {
          $(".bigFormLoader, .btnBlock, .addElementForm").addClass("d-none").fadeOut();
          $('#addElement .modal-body').append('<div class="alert alert-success alertBlock"><i class="fas fa-thumbs-up mr-2 text-success"></i>Успешно! Организация добавлена!</div>');
          // Получаем id добавленной организации
          var numberField = data.id;
          // Обновляем опции списка организаций
          /*$('select[data-catalog="'+ number +'"]').each(function() { 'select[data-catalog="'+ number +'"]'*/
            createOptions ("rest/profile/organizations/", number, "shortName", "id", numberField, 'organisations');
          /*});*/
        },
        error: function () {
          $(".bigFormLoader, .btnBlock, .addElementForm").addClass("d-none").fadeOut();
          $('#addElement .modal-body').append('<div class="alert alert-danger alertBlock"><i class="fas fa-exclamation-triangle mr-2 text-danger"></i>Ошибка! Организация не добавлена!</div>');
        }
      });
    }
  });
  // Очищаем форму при закрытии модального окна
  $('#addElement').on('hidden.bs.modal', function() {
    $('.addElementForm, .btnBlock').removeClass('d-none');
    $('.alertBlock').addClass('d-none');
    $('.addElementForm input').val('');
  });
});