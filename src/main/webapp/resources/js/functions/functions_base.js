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

    // Заполнение данных организации
    function getValueOrganisation (url, element) {
        return $.getJSON (url, function(data) {
            $(element+' #shortNameLf').val(data.shortNameLf);
            $(element+' #fullNameLf').val(data.fullNameLf);
            $(element+' #inn').val(data.inn);
            $(element+' #ogrn').val(data.ogrn);
            $(element+' #kpp').val(data.kpp);
            $(element+' #address').val(data.address);
            $(element+' #fioManager').val(data.fioManager);
            $(element+' #positionManager').val(data.positionManager);
            $(element+' #shortName').val(data.shortName);
            $(element+' #shortLegalForm').val(data.shortLegalForm);
            $(element+' #fullLegalForm').val(data.fullLegalForm);
            $(element+' #normalizedName').val(data.normalizedName);
        });
    }

    // Функция получения текстового поля
    /*
    1. element - Элемент к которому добавляется поле
    2. type - Тип поля (data, time, text);
    3. id - Значение атрибута id;
    4. name - Название поля (англ.)
    5. title - Поясняющая надпись (рус);
    6. short - Цифровое значения короткого поля (1 - да)
    7. iconName - обозначение иконки справа
    8. value - Значение атрибута value
    9. field - Значение атрибута data-field
    10. up - Присвоени класса upElem (1 - да)
    11. idField - Значение атрибута data-id
    12. enabled - Значение атрибута enabled (true - да)
    13. required - Значение атрибута required (true - да)
    14. attachment - Поле инпут для ввода данных
    15. text - Поле инпут для ввода текста
    16. selfClass - Название собственного класса
    */
    function createInput (element, type, id, name, title, short, iconName, value, field, up, idField, enabled, required, attachment, text, selfClass) {
        //console.log(idField);
        var idVal = "";
        if (idField) {idVal = ' data-id="'+idField+'"';}
        var inputVal = '';
        if (value) {inputVal = " value='"+value+"'";}
        var col = '<div class="col-md-12">';
        var colShort = '';
        if (short == 1) {
            col = '<div class="col-md-6">';
            colShort = '<div class="col-md-6">&nbsp;</div>';
        }
        var upClass = '';
        if (up == 1) {upClass = ' upElem';}
        if (up == 2) {upClass = ' upElemNew';}
        var enaBled = '';
        if (enabled == false) {
            enaBled = ' disabled';
            upClass = upClass+' disableElem';
        }
        if(selfClass !='') {upClass = upClass+' '+selfClass;}
        var reqUired = '';
        var requiredSup = '';
        var requiredValidate = '';
        if (required == true) {
            reqUired = ' required';
            requiredSup = '<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup>';
            requiredValidate = '<div class="invalid-feedback">Поле обязательно для заполнения</div>';
        }
        if (attachment == 1) {
            $(element).append(
                '<div class="md-form file-field mb-2">' +
                '   <div class="btn btn-primary btn-sm float-left"><span>Обзор</span>' +
                '       <input title="'+title+'" type="'+type+'" id="'+name+'" name="'+name+'"' +
                ' data-field="'+field+'" '+idVal+enaBled+reqUired+' class="inputFile'+upClass+'"'+inputVal+'>' +
                '   </div>' +
                '   <div class="file-path-wrapper btnLoad">' +
                '       <input class="file-path validate" type="text" placeholder="Выберите файл">'+requiredValidate+'' +
                '   </div>' +
                '</div>');
        } else if (attachment == 2) {
            $(element).append(
                '<div class="row d-flex align-items-center">' +
                '   <div class="col-md-9">' +
                '       <div class="md-form file-field">' +
                '           <div class="btn btn-primary btn-sm float-left"><span>Обзор</span>' +
                '               <input title="'+title+'" type="'+type+'" id="'+name+'" name="'+name+'"' +
                ' data-field="'+field+'" '+idVal+enaBled+reqUired+' class="inputFile'+upClass+'"'+inputVal+'>' +
                '           </div>' +
                '       <div class="file-path-wrapper btnLoad">' +
                '           <input class="file-path validate" type="text" placeholder="Выберите файл">'+requiredValidate+'</div>' +
                '       </div>' +
                '   </div>' +
                '   <div class="col-md-3">' +
                '       <a href="#" id="btnLoad" class="btn btn-default btn-sm rounded" target="_blank"' +
                ' data-toggle="tooltip" title="Скачать файл">' +
                '           <i class="fas fa-download"></i>' +
                '       </a>' +
                '   </div>' +
                '</div>');
        } else if (text == 1) {
            $(element).append(
                '<input title="'+title+'" type="'+type+'" id="'+name+'" name="'+name+'"' +
                ' data-field="'+field+'"' +
                ' '+idVal+enaBled+reqUired+' class="white form-control'+upClass+'"'+inputVal+'>' +
                ''+requiredValidate);
        } else {
            $(element).append(
                '<div class="row d-flex align-items-center mb-3">'+col+'' +
                '<div class="row">' +
                '   <div class="col-md-3 text-left">' +
                '       <div for="'+name+'" class="text-muted">'+iconName+requiredSup+'</div>' +
                '   </div>' +
                '   <div class="col-md-9">' +
                '       <input title="'+title+'" type="'+type+'" id="'+name+'" name="'+name+'" ' +
                'data-field="'+field+'" '+idVal+enaBled+reqUired+'' +
                ' class="white form-control'+upClass+'"'+inputVal+'>'+requiredValidate+'</div>' +
                '   </div>' +
                '</div>'+colShort+'</div>');
        }
    }

    // Добавить блок по чеку на чекбокс
    function checkedFields (element, block) {
        $(element).click(function() {
            if ($(this).is(':checked')) {
                $(block).removeClass('d-none');
                $(this).val(1);
            } else {
                $(block).addClass('d-none');
                $(this).val(0);
            }
        });
    }


    // Подсчёт OPTION в поле SELECT
    function sumOptions (url, field, parent) {
        return $.getJSON (url, function(data) {
            var jsonZapros = data.length;
            if(jsonZapros > 0) {
                $(field).parents(parent).attr("data-option",jsonZapros)/*.removeClass('d-none')*/;
            } else {
                $(field).parents(parent).attr("data-option",jsonZapros).addClass('d-none');
            }
        })
    }

    // Функция получения полей по выбору SELECT
    /*
    1. url - Запрос
    2. field - Элемент SELECT к которому добавляется поле
    3. name - Название поля
    4. id - Название атрибута у выбранного поля
    5. select - Значение выбранного атрибута value
    */
    function createOptions (url, field, name, id, select, spisok) {
        return $.getJSON (url, function(data) {
            for (var i in data) {
                var selectedField = '';
                var nameField = data[i][name];
                // Получаем отмеченные поля если есть необходимость
                if (select != '') {if (select === data[i][id]) {selectedField = ' selected="selected"';}}
                if(spisok === 'users') {
                    var userName = data[i]['fullName'];
                    var phone = '';
                    if(data[i]['phone'] && data[i]['phone'] != '') {phone = ' , тел. '+data[i]['phone'];}
                    nameField = userName + phone;
                } else if(spisok === 'usersList') {nameField = data[i]['fullName'];}
                $(field).append('<option class="active" value="'+data[i][id]+'"'+selectedField+'>'+nameField+'</option>');
                $(field).trigger("chosen:updated");
            }
        })
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

    // Иерархические справочники (изменение элемента, к какому применить)
    function createOptionsValue (element, block, parent) {
        $(element).on('change', function () {
            //console.log(element, block, parent);
            var numberSelectField = $(this).val();
            //console.log(numberSelectField);
            if(numberSelectField) {
                //$(block+' .parent').addClass('d-none');
                var idParent = $(this).attr("data-catalog");
                //console.log(block + " .p" + idParent);
                $(block + " .p" + idParent).each(function () {
                    $(this).removeClass('d-none');
                    $(this).find("select").each(function () {
                        var tempCatalogField = $(this).attr("id");
                        var numberCatalogField = $(this).attr("data-catalog");
                        var nameCatalogField = '#'+tempCatalogField;
                        //console.log(nameCatalogField);
                        // Количество опций по запросу, тут же в функции прячем ненужные
                        //console.log("rest/profile/catalogs/"+numberCatalogField+"/elems/parent/"+numberSelectField+" - "+nameCatalogField);
                        sumOptions ("rest/profile/catalogs/"+numberCatalogField+"/elems/parent/"+numberSelectField, nameCatalogField, parent);
                        // Открываем опции и закрываем
                        $(this).find('option.active').remove();
                        createOptions ("rest/profile/catalogs/"+numberCatalogField+"/elems/parent/"+numberSelectField, nameCatalogField, "valueStr", "id", "", "");
                    });
                });
            }
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