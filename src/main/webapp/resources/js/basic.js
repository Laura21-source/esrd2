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

  // Крутой селект
  $('.mdb-select').materialSelect();
  /*$('.select-wrapper.md-form.md-outline input.select-dropdown').bind('focus blur', function () {
    $(this).closest('.select-outline').find('label').toggleClass('active');
    $(this).closest('.select-outline').find('.caret').toggleClass('active');
  });*/

  // Валидация мультиселекта
  /*function validateSelect(e) {
    e.preventDefault();
    $('.needs-validation').addClass('was-validated');
    if ($('.needs-validation select').val() == '') {
      $('.needs-validation').find('.select-dropdown').val('').prop('placeholder', 'Поле обязательно для выбора!')
    }
  }
  $('.needs-validation select').on('change', e => validateSelect(e));
  $('.needs-validation').on('submit', e => validateSelect(e));*/

  // Всплывающие подсказки
  //$('[data-toggle="tooltip"]').tooltip();

  // Добавить блок
  $(document).on("click", ".addGroup", function() {
    var links = $("[data-block='1']").length;
    var links1 = links + 1;
    //var id = getId();
    var asd = $("#selectType").val();
    var newField = getDownFields("rest/profile/doctypes/" + asd + "/fields", '', links1);
    $('#newBlockGroup').append(newField);
  });

  $(document).on("click", ".addGroupNew", function() {
      var links = $("[data-block='2']").length;
      var links1 = links + 1;
      //var id = getId();
      var asd = $("#selectTypeNew").val();
      var newField = getDownFields("rest/profile/doctypes/" + asd + "/fields", '', links1, 1);
      $('#newBlockGroupNew').append(newField);
  });

  // Добавить пользователя
  $(document).on("click", ".addUser", function() {
    var links = $('[data-user="1"]').length;
    var links1 = links + 1;
    var fieldUser = '#userList'+links1;
    var newField = createOptions ('rest/profile/users/', fieldUser, '', 'id', '', 'usersList');
    $('#userListBlock').append('<div class="col-12 blockUser" id="blockUser'+links1+'"><div class="row d-flex align-items-center justify-content-center fontSmall userListBlock" data-user="1"><div class="col-md-1">'+links1+'</div><div class="col-md-1"><i class="fas fa-user"></i></div><div class="col-md-8 selectUser select-outline"><select class="mdb-select md-form md-outline colorful-select dropdown-primary userList" data-spisok="'+links1+'" id="userList'+links1+'" searchable=" Поиск" name="userList[]" required><option value="" selected>Выбрать</option></select><div class="fontSmall text-left" id="userListPost'+links1+'"></div></div><div class="col-md-2"><div id="delUser'+links1+'" class="btn btn-danger btn-sm pointer delUser rounded px-3" title="Удалить пользователя"><i class="fas fa-trash"></i></div></div></div></div>');
    $('#blockUser'+links1+' .mdb-select').materialSelect();
    //$('[data-toggle="tooltip"]').tooltip();
    $('#userList'+links1).append(newField);
  });

  $(document).on("click", ".addUserNew", function() {
      var links = $('[data-user="1"]').length;
      var links1 = links + 1;
      var fieldUser = '#userListNew'+links1;
      var newField = createOptions ('rest/profile/users/', fieldUser, '', 'id', '', 'usersList');
      $('#userListBlockNew').append('<div class="col-12 blockUserNew" id="blockUserNew'+links1+'"><div class="row d-flex align-items-center justify-content-center fontSmall userListBlockNew" data-user="1"><div class="col-md-1">'+links1+'</div><div class="col-md-1"><i class="fas fa-user"></i></div><div class="col-md-8 selectUser select-outline"><select class="mdb-select md-form md-outline colorful-select dropdown-primary userListNew" data-spisok="'+links1+'" id="userListNew'+links1+'" searchable=" Поиск" name="userListNew[]" required><option value="" selected>Выбрать</option></select><div class="fontSmall text-left" id="userListPostNew'+links1+'"></div></div><div class="col-md-2"><div id="delUserNew'+links1+'" class="btn btn-danger btn-sm pointer delUser rounded px-3" title="Удалить пользователя"><i class="fas fa-trash"></i></div></div></div></div>');
      $('#blockUserNew'+links1+' .mdb-select').materialSelect();
      //$('[data-toggle="tooltip"]').tooltip();
      $('#userListNew'+links1).append(newField);
  });

  // Удалить блок
  $(document).on("click", ".delGroup", function() {
    var id = $(this).attr("id");
    id = id.substr(8);
    $('#blockGroup' + id).remove();
  });

  $(document).on("click", ".delGroupNew", function() {
    var id = $(this).attr("id");
    id = id.substr(8);
    $('#blockGroupNew' + id).remove();
  });

  // Удалить пользователя
  $(document).on("click", ".delUser", function() {
    var id = $(this).attr("id");
    id = id.substr(7);
    $('#blockUser' + id).remove();
  });

  $(document).on("click", ".delUserNew", function() {
    var id = $(this).attr("id");
    id = id.substr(9);
    $('#blockUserNew' + id).remove();
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
      data: data,
      success: function (data) {

      },
      error: function () {
        toastr["error"]("Ошибка сохранения файла!");
      }
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
        toastr["error"]("Ошибка переформирования файла!");
      }
    });
  });

  // Заполнение данных организации
  $('#btnEgrul').click(function() {
    var searchValue = $('.addElementForm #searchValue').val();
    searchValue = $.trim(searchValue);
    return getValueOrganisation ('rest/profile/organizations/getEGRULData?INN='+searchValue, '.addElementForm');
  });

  // Добавление номера поля в кнопку добавлению организации
  $('#addElement').on('shown.bs.modal', function(event) {
    var catalogNumber = $(event.relatedTarget).attr('data-catalog');
    $('.btnAddElement').attr('data-catalog', catalogNumber);
  });

  // Добавление элемента в список организаций
  $('.btnAddElement').click(function(event) {
    var number = $(this).attr('data-catalog');
    event.preventDefault();
    var forms = $('.addElementForm');
    var formsValue = $('.addElementForm input,.addElementForm textarea,.addElementForm select').filter('[required]');
    event.preventDefault();
    var checkField = checkValidation(formsValue);
    if(checkField === false) {
      toastr["error"]("Заполните обязательные поля!");
      $(forms).addClass('was-validated');
      event.stopPropagation();
    } else {
      $('.bigFormLoader').removeClass("d-none").fadeIn();
      $('.addElementForm').addClass('d-none');
      // Формируем JSON из полей
      var dataField = {
        "id": null,
        "shortNameLf": $('#shortNameLf').val(),
        "fullNameLf": $('#fullNameLf').val(),
        "shortName": $('#shortName').val(),
        "fullName": $('#fullName').val(),
        "shortLegalForm": $('#shortLegalForm').val(),
        "fullLegalForm": $('#fullLegalForm').val(),
        "normalizedName": $('#normalizedName').val(),
        "ogrn": $('#ogrn').val(),
        "inn": $('#inn').val(),
        "kpp": $('#kpp').val(),
        "address": $('#address').val(),
        "fioManager": $('#fioManager').val(),
        "positionManager": $('#positionManager').val()
      };
      var data = JSON.stringify(dataField);
      //console.log("number - " + number);
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
          $(number + ' option').remove();
          // Обновляем опции списка организаций
          createOptions ("rest/profile/organizations/", number, "shortNameLf", "id", numberField, 'organisations');
          setTimeout(function() {
            $('#addElement').modal('hide');
          }, 1000);
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
    $('.addElementForm input, .addElementForm textarea').val('');
  });

  // Раскрытие и сокрытие полей основного документа
  $(document).on('click', '.btnView', function() {
    $(this).removeClass('btnView').addClass('btnClose');
    $(this).html('<i class="fas fa-minus mr-2"></i>Свернуть');
    $('.blockDocument').removeClass('d-none');
  });
  $(document).on('click', '.btnClose', function() {
    $(this).removeClass('btnClose').addClass('btnView');
    $(this).html('<i class="fas fa-plus mr-2"></i>Развернуть');
    $('.blockDocument').addClass('d-none');
  });

  // Раскрытие и сокрытие полей вторичного документа
  $(document).on('click', '.btnViewNew', function() {
    $(this).removeClass('btnViewNew').addClass('btnCloseNew');
    $(this).html('<i class="fas fa-minus mr-2"></i>Свернуть');
    $('.newDocumentForm').removeClass('d-none');
  });
  $(document).on('click', '.btnCloseNew', function() {
    $(this).removeClass('btnCloseNew').addClass('btnViewNew');
    $(this).html('<i class="fas fa-plus mr-2"></i>Развернуть');
    $('.newDocumentForm').addClass('d-none');
  });
});