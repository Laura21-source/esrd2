    // Количество элементов в массиве
    function countElem (array) {return array.length;}

    // Оставляем только уникальные значения в массиве
    /*function uniqueVal (value, index, self) {
        return self.indexOf(value) === index;
    }*/

    // Отображаем пользователя для секретаря за кем работаем
    $.getJSON('rest/profile/users/getDelegatedUser', function(data) {
        if(data.length !== 0) {
            var newFirstname = data.firstname.substr(0,1)+'.';
            var newPatronym = data.patronym.substr(0,1)+'.';
            var currentName = data.lastname+' '+newFirstname+' '+newPatronym;
            $('#currentUser').html('полномочия пользователя: '+currentName);
        }
    });

    // Получение id документа из адресной строки
    function getId() {return new URL(window.location.href).searchParams.get("id");}
    function getPage() {return new URL(window.location.href).searchParams.get("page");}

    // Получение IP адреса клиента
    $.getJSON('https://api.ipify.org/?format=json', function(e) {
        $('#hiddenUserBlock1').html('<input type="hidden" id="helpIPAddress" value="'+e.ip+'">');
    });

    // Получение данных текущего пользователя
    function getName (url, element) {
        return $.getJSON (url, function(data) {
            var userName = data.lastname+' '+data.firstname+' '+data.patronym;
            $(element).html(userName);
            $('#hiddenUserBlock2').html(
                '<input type="hidden" id="helpLogin" value="'+data.name+'">' +
                '<input type="hidden" id="helpFIO" value="'+userName+'">' +
                '<input type="hidden" id="helpPhone" value="'+data.phone+'">' +
                '<input type="hidden" id="helpEmail" value="'+data.email+'">'
            );
        });
    }

    // Меняем имя пользователя на нормальное
    var templateUser = $('#templateUser').html();
    getName ('rest/profile/users/getByName?name='+templateUser, '#templateUser');

    // Получаем данные для отображения слева в меню
    function getMenuPils (url, element) {
        var sumPole = '';
        return $.getJSON (url , function(data) {
            sumPole = countElem(data);
            $(element).html(sumPole);
        });
    }

    // Прооверка полей на заполняемость
    function checkValidation (value) {
        var validation = true;
        $(value).each(function() {
            var attrValue = $(this).attr('id');
            var newAttrValue = '#'+attrValue+'_chosen';
            if($(this).val() == ''/* && $(this).is(':visible')*/) {
                $('#'+attrValue).addClass('chosen-invalid');
                $(newAttrValue+' .chosen-single').addClass('chosen-invalid');
                $(newAttrValue+' .chosen-choices').addClass('chosen-invalid');
                validation = false;
            } else {
                $('#'+attrValue).removeClass('chosen-invalid');
                $(newAttrValue+' .chosen-single').removeClass('chosen-invalid');
                $(newAttrValue+' .chosen-choices').removeClass('chosen-invalid');
            }
            //var newErrorValue = '#'+attrValue+'_invalid';
            //$(newErrorValue).show();
        });
        return validation;
    }

    // Убираем красное обрамление вокруг валидированного поля
    function changeValidationField (element) {
        $(element).change(function() {
            if($(this).val() != '') {
                $(this).removeClass('chosen-invalid');
                /*$('#'+attrValue).removeClass('chosen-invalid');
                $(newAttrValue+' .chosen-single').removeClass('chosen-invalid');
                $(newAttrValue+' .chosen-choices').removeClass('chosen-invalid');*/
            }
        });
    }

    // Конфигурация сообщения об ошибках
    toastr.options = {
        "closeButton": false,
        "debug": false,
        "newestOnTop": false,
        "progressBar": false,
        "positionClass": "md-toast-bottom-right",
        "preventDuplicates": false,
        "onclick": null,
        "showDuration": 300,
        "hideDuration": 1000,
        "timeOut": 5000,
        "extendedTimeOut": 1000,
        "showEasing": "swing",
        "hideEasing": "linear",
        "showMethod": "fadeIn",
        "hideMethod": "fadeOut"
    }

    // Параметр финальности документа для отображени или скрытия блока Адресат
    function getFinalStage(url, field) {
        return $.getJSON (url, function(data) {
            if(data.finalDoc === true) {
                $(field).addClass('d-none');
                $(field+' select').removeAttr('required');
            }
            if(data.finalDoc === false) {
                $(field).removeClass('d-none');
                $(field+' select').attr('required', true);
            }
        });
    }

    // Добавление должности при изменении пользователя
    function createUserList (url, field) {
        return $.getJSON (url, function(data) {
            $(field).html(data.position);
        });
    }

    // Получение правильного и обратного формата даты
    function formatDate (date, reverse) {
        var date = new Date(date);
        var day = date.getDate();
        if (day < 10) {day = '0'+day;}
        var month = date.getMonth()+1;
        if (month < 10) {month = '0'+month;}
        var year = date.getFullYear();
        if (reverse == 1) {return year+'-'+month+'-'+day;} else {return day+'-'+month+'-'+year;}
    }

    // Получение правильного и обратного формата времени
    function formatTime (date) {
        var date = new Date(date);
        var hours = date.getHours();
        if (hours < 10) {hours = '0'+hours;}
        var minutes = date.getMinutes();
        if (minutes < 10) {minutes = '0'+minutes;}
        return hours+':'+minutes;
    }