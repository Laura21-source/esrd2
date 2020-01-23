// Заполнение данных SELECT при поиске организаций
$(document).on('click', '#searchEgrul', function() {
    $('#resultYesEgrul, #resultNoEgrul').addClass('d-none');
    $('#resultList .active').remove();
    $('#resultBlock input, #resultBlock textarea').val('');
    var searchValue = $('.addElementForm #searchValue').val();
    searchValue = $.trim(searchValue);
    return $.getJSON ('rest/profile/organizations/getEGRULData?query='+searchValue, function(data) {
        if(countElem(data) > 0) {
            $('#resultList .active').remove();
            $('#resultYesEgrul').removeClass('d-none');
            for (var i in data) {
                $('#resultList').append(
                    '<option class="active" value="'+i+'">' +
                    '   <div>' +
                    '       <span class="mr-2">&bull;&nbsp;'+data[i].fullNameLf+'</span>' +
                    '       <span class="mr-2">&bull;&nbsp;'+data[i].inn+'</span>' +
                    '       <span class="mr-2">&bull;&nbsp;'+data[i].ogrn+'</span>' +
                    '       <span class="mr-2">&bull;&nbsp;'+data[i].kpp+'</span>' +
                    '       <span class="mr-2">&bull;&nbsp;'+data[i].address+'</span>' +
                    '       <span class="mr-2">&bull;&nbsp;'+data[i].fioManager+'</span>' +
                    '       <span class="mr-2">&bull;&nbsp;'+data[i].positionManager +
                    '   </div>' +
                    '</option>'
                );
            }
            $('#resultList').trigger("chosen:updated");
        } else {
            $('#resultNoEgrul').removeClass('d-none');
            setTimeout(function() {
                $('#resultNoEgrul').addClass('d-none');
            }, 3000);
            $('#resultList .active').remove();
        }
    });
});

// Заполнение данных организации
function getValueOrganisation (url, element, value) {
    console.log(url, element, value);
    return $.getJSON (url, function(data) {
        $(element+' #shortNameLf').val(data[value].shortNameLf);
        $(element+' #fullNameLf').val(data[value].fullNameLf);
        $(element+' #inn').val(data[value].inn);
        $(element+' #ogrn').val(data[value].ogrn);
        $(element+' #kpp').val(data[value].kpp);
        $(element+' #address').val(data[value].address);
        $(element+' #fioManager').val(data[value].fioManager);
        $(element+' #positionManager').val(data[value].positionManager);
        $(element+' #shortName').val(data[value].shortName);
        $(element+' #shortLegalForm').val(data[value].shortLegalForm);
        $(element+' #fullLegalForm').val(data[value].fullLegalForm);
        $(element+' #normalizedName').val(data[value].normalizedName);
    });
}

// Заполнение данных организации
$('#resultList').change(function() {
    var searchValue = $('.addElementForm #searchValue').val();
    searchValue = $.trim(searchValue);
    var resultValue = $(this).val();
    return getValueOrganisation ('rest/profile/organizations/getEGRULData?query='+searchValue, '.addElementForm', resultValue);
});

// Добавление номера поля в кнопку добавлению организации и очистка данных
$('#addElement').on('shown.bs.modal', function(event) {
    var catalogNumber = $(event.relatedTarget).attr('data-catalog');
    $('.btnAddElement').attr('data-catalog', catalogNumber);
    $('#resultYesEgrul, #resultNoEgrul').addClass('d-none');
    $('#resultList .active').remove();
    $('#resultBlock input, #resultBlock textarea').val('');
});

// Добавление элемента в список организаций
$('.btnAddElement').click(function(event) {
    var number = $(this).attr('data-catalog');
    event.preventDefault();
    var forms = $('.addElementForm');
    var formsValue = $('.addElementForm input,.addElementForm textarea,.addElementForm select').filter('[required]');
    var checkField = checkValidation(formsValue);
    if(checkField === false) {
        toastr["error"]("Заполните обязательные поля!");
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
                $('#addElement .modal-body').append(
                    '<div class="alert alert-success alertBlock">' +
                    '   <i class="fas fa-thumbs-up mr-2 text-success"></i>Успешно! Организация добавлена!' +
                    '</div>'
                );
                // Получаем id добавленной организации
                var numberField = data.id;
                $(number + ' option .active').remove();
                $('#resultList .active').remove();
                $('.addElementForm input, .addElementForm textarea').val('');
                // Обновляем опции списка организаций
                createOptions ("rest/profile/organizations/", number, "shortNameLf", "id", numberField, 'organisations');
                setTimeout(function() {
                    $('#addElement').modal('hide');
                }, 1000);
            },
            error: function () {
                $(".bigFormLoader, .btnBlock, .addElementForm").addClass("d-none").fadeOut();
                $('#addElement .modal-body').append(
                    '<div class="alert alert-danger alertBlock">' +
                    '   <i class="fas fa-exclamation-triangle mr-2 text-danger"></i>Ошибка! Организация не добавлена!' +
                    '</div>'
                );
                $('#resultList .active').remove();
            }
        });
    }
});

// Очищаем форму при закрытии модального окна
$('#addElement').on('hidden.bs.modal', function() {
    $('.addElementForm, .btnBlock').removeClass('d-none');
    $('.alertBlock').addClass('d-none');
    $('.addElementForm input, .addElementForm textarea').val('');
    $('#resultList .active').remove();
    $('#resultYesEgrul, #resultNoEgrul').addClass('d-none');
});