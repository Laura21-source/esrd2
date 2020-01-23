// Отправление запроса в поддержку
$('#btnHelpBlock').click(function(event) {
    event.preventDefault();
    var forms = $('.addHelpForm');
    var formsValue = $('.addHelpForm input,.addHelpForm textarea,.addHelpForm select').filter('[required]');
    var checkField = checkValidation(formsValue);
    if(checkField === false) {
        toastr["error"]("Заполните обязательные поля!");
        event.stopPropagation();
    } else {
        $('.bigFormLoader').removeClass("d-none").fadeIn();
        $('.addHelpForm, .bigIcon, .btnBlock').addClass('d-none');
        // Формируем JSON из полей
        var dataField = {
            "project": {"id":"0-4"},
            "summary": $('#helpTheme').val(),
            "description": $('#helpDescription').val(),
            "customFields": [
                {
                    "name":"IP",
                    "$type": "SimpleIssueCustomField",
                    "value": $('#helpIPAddress').val()
                },
                {
                    "name":"Логин",
                    "$type": "SimpleIssueCustomField",
                    "value": $('#helpLogin').val()
                },
                {
                    "name":"ФИО",
                    "$type": "SimpleIssueCustomField",
                    "value": $('#helpFIO').val()
                },
                {
                    "name":"Телефон",
                    "$type": "SimpleIssueCustomField",
                    "value": $('#helpPhone').val()
                },
                {
                    "name":"All related emails",
                    "$type": "SimpleIssueCustomField",
                    "value": $('#helpEmail').val()
                }
            ]
        };
        var data = JSON.stringify(dataField);
        //console.log(data);
        $.ajax({
            type: 'POST',
            url: 'http://neurosystems.net:8080/api/issues',
            headers: {
                'Authorization' : 'Bearer perm:YWRtaW4=.NDgtMQ==.Q709SlfGBgKLoQEei6wCmpPgtiVime',
                'Accept' : 'application/json'
            },
            data: data,
            contentType: 'application/json; charset=utf-8',
            success: function () {
                $(".bigFormLoader, .btnBlock, .addHelpForm").addClass("d-none").fadeOut();
                $('#helpBlock .modal-body').append('<div class="alert alert-success alertBlock"><i class="fas fa-thumbs-up mr-2 text-success"></i>Успешно! Ваше обращение отправлено!</div>');
                setTimeout(function() {
                    $('#helpBlock').modal('hide');
                }, 1500);
            },
            error: function () {
                $(".bigFormLoader, .btnBlock, .addHelpForm").addClass("d-none").fadeOut();
                $('#helpBlock .modal-body').append('<div class="alert alert-danger alertBlock"><i class="fas fa-exclamation-triangle mr-2 text-danger"></i>Ошибка! Ваше обращение не отправлено!</div>');
                setTimeout(function() {
                    $('#helpBlock').modal('hide');
                }, 3500);
            }
        });
    }
});