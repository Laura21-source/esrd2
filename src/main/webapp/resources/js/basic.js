$(function() {
    "use strict";
    // Эффекты MDB при появлении
    new WOW().init();

    // Боковая панель меню
    $(".button-collapse").sideNav();

    // Получаем данные для отображения слева в меню
    getMenuPils('rest/profile/docs/inwork', '.inWorkSum');
    getMenuPils('rest/profile/docs/agreement', '.agreementSum');
    getMenuPils('rest/profile/docs/distribution', '.distributionSum');

    // Крутой селект
    //$('.mdb-select').materialSelect();
    $(".chosen-select").chosen({
        width: "100%",
        no_results_text: "Ничего не найдено!"
    });

    // Всплывающие подсказки
    //$('[data-toggle="tooltip"]').tooltip();

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