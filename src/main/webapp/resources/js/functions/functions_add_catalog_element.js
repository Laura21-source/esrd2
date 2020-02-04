// Добавление номера поля в кнопку добавлению организации и очистка данных
$('#addCatalogElement').on('shown.bs.modal', function(event) {
    var catalogNumber = $(event.relatedTarget).attr('data-catalog');
    var catalogId = $(event.relatedTarget).attr('data-field');
    $('.btnAddCatalogElement').attr('data-catalog', catalogNumber);
    $('.btnAddCatalogElement').attr('data-field', catalogId);
});

// Добавление элемента в список каталога
$('.btnAddCatalogElement').click(function(event) {
    var number = $(this).attr('data-catalog');
    var catalogId = parseInt($(this).attr('data-field'));
    event.preventDefault();
    var formsValue = $('.addCatalogElementForm input, .addCatalogElementForm textarea, .addCatalogElementForm select').filter('[required]');
    var checkField = checkValidation(formsValue);
    if(checkField === false) {
        toastr["error"]("Заполните обязательные поля!");
        event.stopPropagation();
    } else {
        $('.bigFormLoader').removeClass("d-none").fadeIn();
        $('.addCatalogElementForm').addClass('d-none');
        // Формируем JSON из полей
        var dataField = {
            "valueStr": $('#addCatalogElementName').val(),
            "valueStrPreposition": $('#addCatalogElementName2').val()
        };
        var data = JSON.stringify(dataField);
        //console.log("number - " + number);
        $.ajax({
            type: "POST",
            url: "rest/profile/catalogs/"+catalogId+"/elems",
            data: data,
            contentType: 'application/json; charset=utf-8',
            success: function (data) {
                $(".bigFormLoader, .btnBlock, .addElementForm").addClass("d-none").fadeOut();
                $('#addCatalogElement .modal-body').append(
                    '<div class="alert alert-success alertBlock">' +
                    '   <i class="fas fa-thumbs-up mr-2 text-success"></i>Новый элемент успешно добавлен!' +
                    '</div>'
                );
                // Получаем id добавленного поля
                var numberField = data.id;
                $(number + ' option .active').remove();
                $('.addCatalogElementForm input, .addCatalogElementForm textarea').val('');
                // Обновляем опции списка организаций
                createOptions ("rest/profile/catalogs/"+catalogId+"/elems", number, "valueStr", "id", numberField, '');
                setTimeout(function() {
                    $('#addCatalogElement').modal('hide');
                }, 1000);
            },
            error: function () {
                $(".bigFormLoader, .btnBlock, .addCatalogElementForm").addClass("d-none").fadeOut();
                $('#addCatalogElement .modal-body').append(
                    '<div class="alert alert-danger alertBlock">' +
                    '   <i class="fas fa-exclamation-triangle mr-2 text-danger"></i>Ошибка! Элемент не добавлен!' +
                    '</div>'
                );
            }
        });
    }
});

// Очищаем форму при закрытии модального окна
$('#addCatalogElement').on('hidden.bs.modal', function() {
    $('.addCatalogElementForm, .btnBlock').removeClass('d-none');
    $('.alertBlock').addClass('d-none');
    $('.addCatalogElementForm input, .addCatalogElementForm textarea').val('');
});