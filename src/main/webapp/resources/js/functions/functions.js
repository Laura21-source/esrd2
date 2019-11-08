    // Количество элементов в массиве
    function countElem (array) {return array.length;}

    // Отображаем пользователя для секретаря за кем работаем
    $.getJSON('rest/profile/users/getDelegatedUser', function(data) {
        if(data.length !== 0) {
            var newFirstname = data.firstname.substr(0,1)+'.';
            var newPatronym = data.patronym.substr(0,1)+'.';
            var currentName = data.lastname+' '+newFirstname+' '+newPatronym;
            $('#currentUser').html('полномочия пользователя: '+currentName);
        }
    });

    // Отображение размера плашек от значений показателей
    function statisticBlock (array) {
        var countMax = /*Math.max.apply(null, array);*/ array[0]['value'];
        //var sum75 = parseInt(countMax*0.75);
        var sum50 = countMax*0.5;
        var sum25 = countMax*0.25;
        console.log(countMax+' - '+sum50+' - '+sum25);
        var sumClass = 'w-25';
        for(var i in array) {
            var value = array[i]['value'];
            var pole = array[i]['pole'];
            if(value == countMax) {sumClass = 'w-100';}
            if(value < countMax && value > sum50) {sumClass = 'w-75';}
            if(value < sum50 && value > sum25) {sumClass = 'w-50';}
            if(value < sum25) {sumClass = 'w-25';}
            $(pole).addClass(sumClass).html(value);
        }
    }

    // Массив JSON со всеми значениями сумм элементов
    function countElemJSON (url, value) {
        $.getJSON(url, function(data) {
            if(value == 1) {
                var mySum = data.atThisMounthOnControl;
                var mySuccess = data.atThisMounthOnControlCompletedInTime;
                var myWarning = data.atThisMounthOnControlCompletedAfterTime;
                var myDanger = data.atThisMounthOnControlNotCompleted;
                var myDis = [{'pole':'#mySum','value':mySum},{'pole':'#mySuccess','value':mySuccess},{'pole':'#myWarning','value':myWarning},{'pole':'#myDanger','value':myDanger}];
                statisticBlock(myDis);
            }
            if(value == 2) {
                var inWorkSuccess = data.inworkMoreDeadlineByDepartment;
                var inWorkDanger = data.inworkLessDeadlineByDepartment;
                var allInWork = [{'pole':'#inWorkSuccess','value':inWorkSuccess},{'pole':'#inWorkDanger','value':inWorkDanger}];
                statisticBlock(allInWork);
                // Для бубликов
                var inWorkSuccessUser = data.inworkMoreDeadlineByUserName;
                var inWorkDangerUser = data.inworkLessDeadlineByUserName;
                am4core.ready(function () {
                    am4core.useTheme(am4themes_dark);
                    am4core.useTheme(am4themes_animated);
                    var chart = am4core.create("chartDiv1", am4charts.PieChart3D);
                    chart.hiddenState.properties.opacity = 0; // this creates initial fade-in

                    chart.data = [
                        {
                            country: "Срок контроля 3 дня и меньше",
                            litres: inWorkDangerUser,
                            color: am4core.color("#ff4444")
                        },
                        {
                            country: "Срок контроля более 3 дней",
                            litres: inWorkSuccessUser,
                            color: am4core.color("#2BBBAD")
                        }
                    ];
                    var pieSeries = chart.series.push(new am4charts.PieSeries3D());
                    pieSeries.dataFields.value = "litres";
                    pieSeries.dataFields.category = "country";
                    pieSeries.slices.template.propertyFields.fill = "color";
                    chart.innerRadius = am4core.percent(63);
                    pieSeries.labels.template.disabled = true;
                    pieSeries.ticks.template.disabled = true;
                });
            }
            if(value == 3) {
                var agreeSuccess = data.agreementMoreDeadlineByDepartment;
                var agreeDanger = data.agreementLessDeadlineByDepartment;
                var allAgrre = [{'pole':'#agreeSuccess','value':agreeSuccess},{'pole':'#agreeDanger','value':agreeDanger}];
                statisticBlock(allAgrre);
                // Для бубликов
                var agreeSuccessUser = data.agreementLessDeadlineByUserName;
                var agreeDangerUser = data.agreementMoreDeadlineByUserName;
                am4core.ready(function() {
                    am4core.useTheme(am4themes_dark);
                    am4core.useTheme(am4themes_animated);
                    var chart = am4core.create("chartDiv2", am4charts.PieChart3D);
                    chart.hiddenState.properties.opacity = 0; // this creates initial fade-in
                    chart.data = [
                        {
                            country: "Срок контроля 3 дня и меньше",
                            litres: agreeDangerUser,
                            color: am4core.color("#ff4444")
                        },
                        {
                            country: "Срок контроля более 3 дней",
                            litres: agreeSuccessUser,
                            color: am4core.color("#2BBBAD")
                        }
                    ];
                    var pieSeries = chart.series.push(new am4charts.PieSeries3D());
                    pieSeries.dataFields.value = "litres";
                    pieSeries.dataFields.category = "country";
                    pieSeries.slices.template.propertyFields.fill = "color";
                    chart.innerRadius = am4core.percent(63);
                    pieSeries.labels.template.disabled = true;
                    pieSeries.ticks.template.disabled = true;
                });
            }
            if(value == 4) {
                var distSuccess = data.distributionMoreDeadlineByDepartment;
                var distDanger = data.distributionLessDeadlineByDepartment;
                var allDist = [{'pole':'#distSuccess','value':distSuccess},{'pole':'#distDanger','value':distDanger}];
                statisticBlock(allDist);
                var distSuccessUser = data.distributionMoreDeadlineByChiefUserName;
                var distDangerUser = data.distributionLessDeadlineByChiefUserName;
                am4core.ready(function() {
                    am4core.useTheme(am4themes_dark);
                    am4core.useTheme(am4themes_animated);
                    var chart = am4core.create("chartDiv3", am4charts.PieChart3D);
                    chart.hiddenState.properties.opacity = 0; // this creates initial fade-in
                    chart.data = [
                        {
                            country: "Срок контроля 3 дня и меньше",
                            litres: distDangerUser,
                            color: am4core.color("#ff4444")
                        },
                        {
                            country: "Срок контроля более 3 дней",
                            litres: distSuccessUser,
                            color: am4core.color("#2BBBAD")
                        }
                    ];
                    var pieSeries = chart.series.push(new am4charts.PieSeries3D());
                    pieSeries.dataFields.value = "litres";
                    pieSeries.dataFields.category = "country";
                    pieSeries.slices.template.propertyFields.fill = "color";
                    chart.innerRadius = am4core.percent(63);
                    pieSeries.labels.template.disabled = true;
                    pieSeries.ticks.template.disabled = true;
                });
            }
        })
    }

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
            $('#hiddenUserBlock2').html('<input type="hidden" id="helpLogin" value="'+data.name+'"><input type="hidden" id="helpFIO" value="'+userName+'"><input type="hidden" id="helpPhone" value="'+data.phone+'"><input type="hidden" id="helpEmail" value="'+data.email+'">');
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
        var idVal = "";
        if (idField) {idVal = ' data-id="'+idField+'"';}
        var inputVal = '';
        //if (value) {inputVal = ' value="'+value+'"';}
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
        if(selfClass !='') {
            upClass = upClass+' '+selfClass;
        }
        var reqUired = '';
        var requiredSup = '';
        var requiredValidate = '';
        if (required == true) {
            reqUired = ' required';
            requiredSup = '<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup>';
            requiredValidate = '<div class="invalid-feedback">Поле обязательно для заполнения</div>';
        }
        if (attachment == 1) {
            $(element).append('<div class="md-form file-field mb-2">' +
                '<div class="btn btn-primary btn-sm float-left"><span>Обзор</span>' +
                '<input title="'+title+'" type="'+type+'" id="'+name+'" name="'+name+'" data-field="'+field+'" '+idVal+enaBled+reqUired+' class="inputFile'+upClass+'"'+inputVal+'>' +
                '</div>' +
                '<div class="file-path-wrapper btnLoad">' +
                '<input class="file-path validate" type="text" placeholder="Выберите файл">'+requiredValidate+'</div></div>');
        } else if (attachment == 2) {
            $(element).append('<div class="row d-flex align-items-center">' +
                '<div class="col-md-9">' +
                '<div class="md-form file-field">' +
                '<div class="btn btn-primary btn-sm float-left"><span>Обзор</span>' +
                '<input title="'+title+'" type="'+type+'" id="'+name+'" name="'+name+'" data-field="'+field+'" '+idVal+enaBled+reqUired+' class="inputFile'+upClass+'"'+inputVal+'>' +
                '</div>' +
                '<div class="file-path-wrapper btnLoad">' +
                '<input class="file-path validate" type="text" placeholder="Выберите файл">'+requiredValidate+'</div></div></div>' +
                '<div class="col-md-3"><a href="#" id="btnLoad" class="btn btn-default btn-sm rounded" target="_blank" data-toggle="tooltip" title="Скачать файл"><i class="fas fa-download"></i></a></div></div>');
        } else if (text == 1) {
            $(element).append('<input title="'+title+'" type="'+type+'" id="'+name+'" name="'+name+'" data-field="'+field+'" '+idVal+enaBled+reqUired+' class="white form-control'+upClass+'"'+inputVal+'>'+requiredValidate);
        } else {
            $(element).append('<div class="row ml-1 mb-3">'+col+'<div class="row">' +
                '<div class="col-md-3 text-left">' +
                '<div for="'+name+'" class="text-muted">'+iconName+requiredSup+'</div>' +
                '</div><div class="col-md-9">' +
                '<input title="'+title+'" type="'+type+'" id="'+name+'" name="'+name+'" data-field="'+field+'" '+idVal+enaBled+reqUired+' class="white form-control'+upClass+'"'+inputVal+'>'+requiredValidate+'</div></div></div>'+colShort+'</div>');
        }
    }

    // Добавить блок по чеку на чекбокс
    function checkedFields (element, block) {
        $(element).click(function() {
            if ($(this).is(':checked')){
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
        //console.log(element);
        $(element).on('change', function () {
            var numberSelectField = $(this).val();
            if(numberSelectField) {
                //$(block+' .parent').addClass('d-none');
                var idParent = $(this).attr("data-catalog");
                $(block + " .p" + idParent).each(function () {
                    $(this).removeClass('d-none');
                    $(this).find("select").each(function () {
                        var tempCatalogField = $(this).attr("id");
                        var numberCatalogField = $(this).attr("data-catalog");
                        var nameCatalogField = '#'+tempCatalogField;
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

    // Получение верхнего стека полей
    function getUpFields(url, id, short, block) {
        var filed = '#blockUp';
        var upElem = 'upElem';
        var inputDate = 'inputDate';
        var inputTime = 'inputTime';
        var blockDate = 'blockDate';
        var blockTime = 'blockTime';
        var up = 1;
        if(block && block === 1) {
            filed = '#blockUpNew';
            upElem = 'upElemNew';
            inputDate = 'inputDateNew';
            inputTime = 'inputTimeNew';
            blockDate = 'blockDateNew';
            blockTime = 'blockTimeNew';
            up = 2;
        }
        return $.getJSON (url, function(data) {
            var rowChild = data;
            if(id > 0 && block !== 1) {rowChild = data.childFields;}
            for(var i in rowChild) {
                var row = rowChild[i];
                // Переменная даты
                var valueDate = '';
                // Переменная поля
                var idField = '';
                var enaOpiton = '';
                var numberField = '';
                if(id > 0) {
                    //idField = ' data-id="' + row.field.id + '"';
                    // Номер поля для отметки в селектах если нужно
                    numberField = row.field.valueInt;
                }
                if(row.field.enabled == false) {enaOpiton = ' disabled';}
                var required = '';
                var requiredSup = '';
                var requiredValidate = '';
                if(row.field.required == true) {
                    required = ' required';
                    requiredSup = '<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup>';
                    requiredValidate = '<div class="invalid-feedback">Поле обязательно для заполнения</div>';
                }
                var selectFieldName = 'selectField' + row.field.fieldId;
                if (row.field.fieldType === "DATE") {
                    if (id > 0) {
                        idField = row.field.id;
                        if(row.field.valueDate !== '') {valueDate = formatDate(row.field.valueDate, 1);}
                    }
                    createInput (filed, "date", blockDate,  inputDate, "Введите дату", short, '<i class="fas fa-calendar-alt mr-2"></i>'+row.field.name, valueDate, row.field.fieldId, up, idField, row.field.enabled, row.field.required, '', '');
                }
                if (row.field.fieldType === "TIME") {
                    if (id > 0) {
                        idField = row.field.id;
                        if(row.field.valueDate !== '') {valueDate = formatTime(row.field.valueDate);}
                    }
                    createInput (filed, "time", blockTime,  inputTime, "Введите время", short, '<i class="fas fa-clock mr-2"></i>'+row.field.name, valueDate, row.field.fieldId, up, idField, row.field.enabled, row.field.required, '', '');
                }
                if (row.field.fieldType === "GROUP_CHECKBOX") {
                    var valueInt = 'value="0"';
                    var checekCheckBox = '';
                    idField = row.field.fieldId;
                    if (row.field.valueInt > 0) {
                        valueInt = 'value="'+row.field.valueInt+'"';
                        checekCheckBox = ' checked';
                    }
                    /*id="'+row.field.tag+'Block"*/
                    $(filed).append('<div class="row my-3">' +
                        '<div class="col-md-12 text-left"><div class="form-check">' +
                        '<input type="checkbox" class="form-check-input ' + upElem + '"' +
                        ' id="'+row.field.tag+'" data-field="'+idField+'" name="'+row.field.tag+'" '+valueInt+checekCheckBox+'>' +
                        '<label class="form-check-label text-muted text-left"' +
                        ' for="'+row.field.tag+'">'+row.field.name+'</label>' +
                        '</div></div></div>'
                    );
                    if(row.field.childFields.length > 0) {
                        $(filed).append('<div id="'+row.field.tag+'BlockDiv" class="childBox d-none"></div>');
                        for(var y in row.field.childFields) {
                            var checkField = row.field.childFields[y];
                            idField = null;
                            if (id > 0) {idField = checkField.id;}
                            var elementField = filed+' #'+row.field.tag+'BlockDiv';
                            var textId = y+1;
                            if (checkField.fieldType === "TEXT") {
                                var nameText = "checkText_"+textId;
                                createInput(elementField, "text", nameText, nameText, "Введите значение", short, checkField.name, checkField.valueStr, checkField.fieldId, '', idField, checkField.enabled, checkField.required, '', '', 'checkClass');
                            }
                            if (checkField.fieldType === "TEXTAREA") {
                                var nameTextarea = "textarea_"+textId;
                                var textareaId = '';
                                var textValue = '';
                                if(checkField.id > 0) {
                                    textareaId = ' data-id="'+checkField.id+'"';
                                    textValue = checkField.valueStr;
                                }
                                $(elementField).append('<div class="row ml-1 mb-3">' +
                                    '<div class="col-md-3 text-left mt-3">' +
                                    '<div class="text-muted">'+checkField.name+requiredSup+'</div></div>' +
                                    '<div class="col-md-9 mt-3">' +
                                    '<textarea type="text" id="'+nameTextarea+'" class="checkClass form-control" data-field="'+checkField.fieldId+'"'+textareaId+'>'+textValue+'</textarea>'
                                    +requiredValidate+'</div></div>');
                            }
                            textId = textId+1;
                        }
                    }
                    checkedFields ('#'+row.field.tag, '#'+row.field.tag+'BlockDiv');
                    if (row.field.valueInt > 0) {$('.childBox').removeClass('d-none');}
                }
                var textId = i+1;
                if (row.field.fieldType === "TEXT") {
                    var textName = '_';
                    if (id > 0) {
                        idField = row.field.id;
                        textName = row.field.id + '_';
                    }
                    var nameText = "inputText_" + textName + textId;
                    createInput(filed, "text", nameText, nameText, "Введите значение", short, row.field.name, row.field.valueStr, row.field.fieldId, up, idField, row.field.enabled, row.field.required, '', '');
                    textId = textId+1;
                }
                // Если вид поля выборка
                var parentBlock = '';
                var parentCatalog = '';
                if(row.field.parentCatalogId > 0) {
                    parentCatalog = ' p' + row.field.parentCatalogId;
                    parentBlock = ' d-none';
                }
                if(id > 0) {
                    if(row.field.parentCatalogId > 0) {
                        parentCatalog = ' p' + row.field.parentCatalogId;
                        if(row.field.valueInt && row.field.valueInt > 0) {
                            parentBlock = '';
                        } else {
                            parentBlock = ' d-none';
                        }
                    }
                }
                if (row.field.fieldType === "CATALOG") {
                    if(id > 0) {idField = ' data-id="' + row.field.id + '"';}
                    var numberCatalog = ('#' + selectFieldName);
                    // Добавляем строку
                    $(filed).append('<div class="row blockRowUp ' + upElem + parentBlock + parentCatalog + ' ml-1 mb-3"><div class="col-md-3 text-left mt-3"><div' +
                        ' class="text-muted">' + row.field.name + requiredSup + '</div></div><div class="col-md-9"><select data-placeholder="Выберите вид документа" class="chosen-select" searchable=" Поиск" type="select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-catalog="' + row.field.catalogId + '" data-field="' + row.field.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select>' + requiredValidate + '</div></div>');
                    $(numberCatalog).chosen({
                        width: "100%",
                        no_results_text: "Ничего не найдено!"
                    });
                    // Формирование правильных полей
                    createOptionsValue(numberCatalog, filed, '.blockRowUp');
                    if(parentBlock == '') {
                        // Добавляем опции
                        createOptions("rest/profile/catalogs/" + row.field.catalogId + "/elems", numberCatalog, "valueStr", "id", numberField, '');
                    }
                }
                // Если вид поля справочник организаций
                if (row.field.fieldType === "CATALOG_ORGANIZATIONS") {
                    var numberCatalog = ('#' + selectFieldName);
                    // Добавляем строку
                    $(filed).append('<div class="row blockRowUp '+ upElem + parentBlock + parentCatalog + ' ml-1 mb-3 d-flex align-items-center"><div class="col-md-3 text-left mt-3"><div class="text-muted">' + row.field.name + requiredSup + '</div></div><div class="col-md-8 mt-3"><select data-placeholder="Выберите вид документа" class="chosen-select" type="select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-catalog="' + row.field.catalogId + '" data-field="' + row.field.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select></div><div class="col-md-1 mt-3 text-right"><button class="btn btn-primary btn-sm addElement rounded m-0 px-3 waves-effect" data-toggle="modal" data-target="#addElement" data-catalog="' + numberCatalog + '" type="button" title="Добавить организацию" ' + enaOpiton + '><i class="fas fa-plus white-text"></i></button></div><div class="col-md-3"></div><div class="col-9">' + requiredValidate + '</div></div>');
                    $(numberCatalog).chosen({
                        width: "100%",
                        no_results_text: "Ничего не найдено!"
                    });
                    // Добавляем опции при нажатии поля
                    createOptions ("rest/profile/organizations/", numberCatalog, "shortNameLf", "id", numberField, '');
                }
                // Если вид поля справочник организаций
                if (row.field.fieldType === "CATALOG_REGNUMBERS") {
                    if(id > 0) {idField = ' data-id="' + row.field.id + '"';}
                    // Добавляем строку
                    $(filed).append('<div class="row ' + upElem + ' ml-1 mb-3' +
                        ' d-flex align-items-center justify-content-center"><div' +
                        ' class="col-md-3 text-left mt-3"><div class="text-muted">' + row.field.name + requiredSup + '</div></div><div class="col-md-9"><select data-placeholder="Выберите вид документа" class="chosen-select" searchable=" Поиск" type="select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-field="' + row.field.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select>' + requiredValidate + '</div></div>');
                    var numberCatalog = ('#' + selectFieldName);
                    $(numberCatalog).chosen({
                        width: "100%",
                        no_results_text: "Ничего не найдено!"
                    });
                    // Добавляем опции
                    createOptions ("rest/profile/docs/regnumbers/", numberCatalog, "regNum", "id", numberField, '');
                }
            }
        }).done(function(response) {
            var filedBlock = '#blockFields, #blockUp, #blockDown, #btnSave, #btnWordFile';
            if(block && block === 1) {filedBlock = '#blockFieldsNew, #blockUpNew, #blockDownNew, #btnSaveNew, #btnWordFileNew';}
            if (response.length == 0) {$(filedBlock).addClass('d-none');}
        });
    }

    // Получение нижнего стека полей
    function getDownFields(url, id, number, block, name) {
        var blockGroup = '#blockGroup';
        var idBlockGroup = 'blockGroup';
        var newBlockGroup = '#newBlockGroup';
        var blockName = '.blockName';
        var delGroup = 'delGroup';
        var cloneGroup = 'cloneGroup';
        var dataBlock = 1;
        var nameGroup = 'nameGroup';
        var addGroup = '.addGroup';
        if(block && block === 1) {
            blockGroup = '#blockGroupNew';
            idBlockGroup = 'blockGroupNew';
            newBlockGroup = '#newBlockGroupNew';
            blockName = '.blockNameNew';
            delGroup = 'delGroupNew';
            cloneGroup = 'cloneGroupNew';
            dataBlock = 2;
            nameGroup = 'nameGroupNew';
            addGroup = '.addGroupNew';
        }
        return $.getJSON (url, function(data) {
            var rowChild = data;
            if(id > 0 && block !== 1) {rowChild = data.childFields;}
            if(id > 0) {number = 1;}
            var idField = '';
            var idFiledInput = '';
            for(var key in rowChild) {
                var row = rowChild[key];
                if (row.field.fieldType === "GROUP_FIELDS") {
                    $('#blockDown').removeClass('d-none');
                    key = parseInt(key);
                    var rowFields = data[key];
                    if(id > 0) {rowFields = row;}
                    var dubKey = 1;
                    var dataField = 0;
                    if(number != '') {
                        dubKey = number;
                        dataField = number;
                    }
                    var delButton = ' d-none';
                    var cloneButton = ' d-none';
                    //var cloneButton = '';
                    // Кнопка удаления полей
                    if(id > 0) {
                        idField = ' data-id="' + rowFields.field.id + '"';
                        idFiledInput = rowFields.field.id;
                        if(number > 1) {
                            delButton = '';
                            //cloneButton = '';
                        }
                    } else {
                        if(number != '') {
                            delButton = '';
                            //cloneButton = '';
                        }
                    }
                    // Название блока
                    var blockNameVal = 'Блок ' + dubKey;
                    if(name) {
                        blockNameVal = 'Блок ' + name;
                    }
                    $(newBlockGroup).append('<div class="row card mb-3 ' + idBlockGroup + '" id="' + idBlockGroup + dubKey + '" data-field="' + dubKey + '"' + idField + '><div class="col-12"><div class="card-body"><div class="row"><div class="col-md-9 text-left"><h5 class="' + nameGroup + '">' + blockNameVal + '</h5></div><div class="col-md-3 text-right"><div id="' + cloneGroup + dubKey + '" class="btn btn-mdb-color btn-sm cloneGroup rounded' + cloneButton + '" title="Дублировать блок"><i class="fas fa-copy"></i></div><div id="' + delGroup + dubKey + '" data-toggle="modal" data-target="#deleteBlock" class="btn btn-danger btn-sm delGroup rounded' + delButton + ' ml-3" title="Удалить блок"><i class="fas fa-trash"></i></div></div></div><hr><div class="row"><div class="col-12 blockGroupFields" data-block="'+dataBlock+'"></div></div></div></div></div>');
                    $(blockName).html(rowFields.field.name).attr("data-block", rowFields.field.fieldId);
                    if(row.field.enabled === false) {$(addGroup + ', .' + nameGroup).addClass('d-none');}
                    if(row.field.enabled === true) {$(addGroup + ', .' + nameGroup).removeClass('d-none');}
                    for (var y in rowFields.field.childFields) {
                        //console.log(rowFields.field.childFields);
                        var rowSelectField = rowFields.field.childFields[y];
                        // Есть ли родитель у блока
                        var parentBlock = '';
                        var parentCatalog = '';
                        if(rowSelectField.parentCatalogId > 0) {
                            parentCatalog = ' p' + rowSelectField.parentCatalogId;
                            parentBlock = ' d-none';
                        }
                        var numberField = '';
                        if(id > 0) {
                            if(rowSelectField.parentCatalogId > 0) {
                                parentCatalog = ' p' + rowSelectField.parentCatalogId;
                                if(rowSelectField.valueInt && rowSelectField.valueInt > 0) {
                                    parentBlock = '';
                                } else {
                                    parentBlock = ' d-none';
                                }
                            }
                            idField = ' data-id="' + rowSelectField.id + '"';
                            idFiledInput = rowSelectField.id;
                            // Номер поля для отметки в селектах если нужно
                            numberField = rowSelectField.valueInt;
                        }
                        var enaOpiton = '';
                        if(rowSelectField.enabled == false) {enaOpiton = ' disabled';}
                        var required = '';
                        var requiredSup = '';
                        var requiredValidate = '';
                        if(rowSelectField.required == true) {
                            required = ' required';
                            requiredSup = '<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup>';
                            requiredValidate = '<div class="invalid-feedback">Поле обязательно для заполнения</div>';
                        }
                        var selectFieldName = 'selectField_' + dubKey + '_' + dataBlock +/*rowSelectField.catalogId*/ y;
                        // Если вид поля SELECT
                        if (rowSelectField.fieldType === "CATALOG") {
                            // Добавляем строку
                            $(blockGroup + dubKey + ' .blockGroupFields').append('<div class="row blockRow' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3 d-flex align-items-center"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9 mt-3"><select data-placeholder="Выберите вид документа" class="chosen-select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-catalog="' + rowSelectField.catalogId + '" data-field="' + rowSelectField.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select>' + requiredValidate + '</div></div>');
                            var numberCatalog = ('#' + selectFieldName);
                            $(numberCatalog).chosen({
                                width: "100%",
                                no_results_text: "Ничего не найдено!"
                            });
                            // Формирование правильных полей
                            createOptionsValue(numberCatalog, blockGroup + dubKey, '.blockRow');
                            if(parentBlock == '') {
                                // Добавляем опции
                                createOptions("rest/profile/catalogs/" + rowSelectField.catalogId + "/elems", numberCatalog, "valueStr", "id", numberField, "");
                            }
                        }
                        // Если вид поля справочник организаций
                        if (rowSelectField.fieldType === "CATALOG_ORGANIZATIONS") {
                            var numberCatalog = ('#' + selectFieldName);
                            // Добавляем строку
                            $(blockGroup + dubKey + ' .blockGroupFields').append('<div class="row blockRow d-flex align-items-center' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-8 mt-3"><select data-placeholder="Выберите вид документа" class="chosen-select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-catalog="' + rowSelectField.catalogId + '" data-field="' + rowSelectField.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select></div><div class="col-md-1 mt-3 text-right"><button class="btn btn-primary btn-sm addElement rounded m-0 px-3 waves-effect" data-toggle="modal" data-target="#addElement" data-catalog="' + numberCatalog + '" type="button" title="Добавить организацию" ' + enaOpiton + '><i class="fas fa-plus white-text"></i></button></div><div class="col-md-3"></div><div class="col-9">' + requiredValidate + '</div></div>');
                            $(numberCatalog).chosen({
                                width: "100%",
                                no_results_text: "Ничего не найдено!"
                            });
                            // Добавляем опции при нажатии поля
                            createOptions ("rest/profile/organizations/", numberCatalog, "shortNameLf", "id", numberField, '');
                        }
                        // Если вид поля справочник пользователей
                        if (rowSelectField.fieldType === "CATALOG_USERS") {
                            var numberCatalog = ('#' + selectFieldName);
                            // Добавляем строку
                            $(blockGroup + dubKey + ' .blockGroupFields').append('<div class="row blockRow d-flex align-items-center' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9 mt-3"><select data-placeholder="Выберите вид документа" class="chosen-select" id="' + selectFieldName + '" name="' + selectFieldName + '" data-catalog="' + rowSelectField.catalogId + '" data-field="' + rowSelectField.fieldId + '"' + idField + enaOpiton + required + '><option value="">Выберите значение справочника</option></select>' + requiredValidate + '</div></div>');
                            $(numberCatalog).chosen({
                                width: "100%",
                                no_results_text: "Ничего не найдено!"
                            });
                            // Добавляем опции
                            createOptions ("rest/profile/users/", numberCatalog, '', 'id', numberField, 'users');
                        }
                        if (rowSelectField.fieldType === "ATTACHMENT") {
                            // Добавляем строку
                            if(parentBlock == '') {
                                var attachment = 1;
                                if(id && id > 0) {attachment = 2;}
                                $(blockGroup + dubKey + ' .blockGroupFields').append('<div class="row blockRow d-flex align-items-center' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9"></div></div>');
                                createInput(".col-md-9:last", "file", 'inputFile', 'inputFile', "Загрузить файл", 0, '' + rowSelectField.name, rowSelectField.valueStr, rowSelectField.fieldId, 0, idFiledInput, rowSelectField.enabled, rowSelectField.required, attachment, '');
                                // Подсказки
                            }
                        }
                        if (rowSelectField.fieldType === "TEXTAREA") {
                            // Добавляем строку
                            if(parentBlock == '') {
                                $(blockGroup + dubKey + ' .blockGroupFields').append('<div class="row blockRow' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9 mt-3"><textarea></textarea>' + requiredValidate + '</div></div>');
                            }
                        }
                        if (rowSelectField.fieldType === "TEXT") {
                            // Добавляем строку
                            if(parentBlock == '') {
                                var textName = '';
                                var textId = y+1;
                                if(id > 0) {textName = rowSelectField.id + '_';}
                                var nameText = "inputText_" + dubKey + '_' + textName + textId;
                                $(blockGroup + dubKey + ' .blockGroupFields').append('<div class="row blockRow d-flex align-items-center' + parentBlock + parentCatalog + '" data-row="' + y + '"><div class="col-md-3 text-left mt-3"><div class="text-muted">' + rowSelectField.name + requiredSup + '</div></div><div class="col-md-9 mt-3"></div></div>');
                                createInput(".col-md-9:last", "text", nameText, nameText, "Введите значение", 0, '' + rowSelectField.name, rowSelectField.valueStr, rowSelectField.fieldId, 0, idFiledInput, rowSelectField.enabled, rowSelectField.required, '', 1);
                                textId = textId+1;
                            }
                        }
                    }
                    if(id > 0) {
                        number = number+1;
                    }
                } else {
                    $('#blockDown').addClass('d-none');
                }
            }
        }).done(function(response) {
            var filedBlock = '#blockFields, #blockUp, #blockDown, #btnSave, #btnWordFile';
            if(block && block === 1) {filedBlock = '#blockFieldsNew, #blockUpNew, #blockDownNew, #btnSaveNew, #btnWordFileNew';}
            if (response.length == 0) {$(filedBlock).addClass("d-none");}
        });
    }

    // Формирование массива элементов для JSON
    function createDataField (id, block) {
        var upElem = '.upElem';
        if(block && block === 1) {
            upElem = '.upElemNew';
        }
        var id = parseInt(id);
        var dataField = [];
        var field = '';
        var idField = null;
        var dataDate = '';
        var valueData = '';
        $(upElem).each(function(i) {
            var key = i+1;
            var attrElem = $(this).attr("type");
            var attrVal = $(this).val();
            var attrId = parseInt($(this).attr("data-field"));
            var attrSelect = $('.chosen-select', this).attr("type");
            var attrSelectId = parseInt($('.chosen-select', this).attr("data-field"));
            var attrSelectVal = parseInt($('.chosen-select', this).val());
            var value = null;
            if (attrElem === "date") {
                valueData = 1;
                if (attrVal != '') {
                    value = attrVal + "T00:00:00";
                    dataDate = attrVal;
                }
            }
            if (attrElem === "time") {
                valueData = 1;
                if (attrVal != '') {value = "1900-01-01" + "T" + attrVal + ":00";}
            }
            if (attrElem === "text") {
                valueData = 2;
                if (attrVal != '') {value = attrVal;}
            }
            if (attrSelect === "select") {
                valueData = 3;
                if (attrSelectVal > 0) {value = attrSelectVal;}
            }
            if (attrElem === "checkbox") {
                valueData = 4;
                if (attrVal > 0) {value = attrVal;}
            }
            if (id > 0) {idField = parseInt($(this).attr("data-id"));}
            if (valueData === 1) {
                field = {
                    "field": {
                        "id" : idField,
                        "childFields": [],
                        "fieldId": attrId,
                        "valueDate" : value
                    },
                    "position": key
                }
            } else if (valueData === 2) {
                field = {
                    "field": {
                        "id" : idField,
                        "childFields": [],
                        "fieldId": attrId,
                        "valueStr" : value
                    },
                    "position": key
                }
            } else if (valueData === 3) {
                field = {
                    "field": {
                        "id" : idField,
                        "childFields": [],
                        "fieldId": attrSelectId,
                        "valueInt" : value
                    },
                    "position": key
                }
            } else if (valueData === 4) {
                var childBox = [];
                var childField = '';
                $('.childBox .checkClass').each(function() {
                    var childFieldId = null;
                    var childFieldVal = $(this).val();
                    var childFieldField = parseInt($(this).attr("data-field"));
                    if (id > 0) {childFieldId = parseInt($(this).attr("data-id"));}
                    childField = {
                        "id" : childFieldId,
                        "childFields" : [],
                        "fieldId" : childFieldField,
                        "valueStr" : childFieldVal
                    }
                    childBox.push(childField);
                });
                field = {
                    "field": {
                        "id" : idField,
                        "childFields": childBox,
                        "fieldId": attrId,
                        "valueInt" : value
                    },
                    "position": key
                }
            }
            dataField.push(field);
        });
        return dataField;
    }

    // Формирование массива блока для JSON - аргумент начало отсчёта
    function createDataBlock (id, key, block) {
        var blockGroup = '.blockGroup';
        var blockGroupId = '#blockGroup';
        var blockName = '.blockName';
        if(block && block === 1) {
            blockGroup = '.blockGroupNew';
            blockGroupId = '#blockGroupNew';
            blockName = '.blockNameNew';
        }
        var id = parseInt(id);
        var idField = null;
        var dataBlock = [];
        $(blockGroup).each(function(item) {
            var i = parseInt(item)+1;
            //console.log(i);
            if($(this).attr("data-field") == i) {
                var elementBlock = blockGroupId;
                if(i !== 0) {elementBlock = elementBlock + i;}
                var elementArray = [];
                $(elementBlock + ' [data-field]').each(function() {
                    if(id > 0) {idField = parseInt($(this).attr("data-id"));}
                    var typeAttr = $(this).attr("type");
                    if (typeAttr && typeAttr !== '') {
                        var elementBlockElem = {
                            "id" : idField,
                            "childFields" : [],
                            "fieldId" : parseInt($(this).attr("data-field")),
                            "valueStr" : $(this).val()
                        }
                    } else {
                        var elementBlockElem = {
                            "id" : idField,
                            "childFields" : [],
                            "fieldId" : parseInt($(this).attr("data-field")),
                            "valueInt" : parseInt($(this).val())
                        }
                    }
                    elementArray.push(elementBlockElem);
                });
                var position = parseInt(key)+i;
                var fieldId = parseInt($(blockName).attr("data-block"));
                if(id > 0) {idField = parseInt($(elementBlock).attr("data-id"));}
                var dataBlockElement = {
                    "field" : {
                        "id" : idField,
                        "childFields": elementArray,
                        "fieldId" : fieldId,
                    },
                    "position" : position
                }
            }
            dataBlock.push(dataBlockElement);
        });
        return dataBlock;
    }

    // Формирование листа согласования
    function createAgreeList (data) {
        var agreeList = [];
        var agreeSum = $(data).length;
        var finalUser = false;
        var currentUser = false;
        for(var i in data) {
            if (i < agreeSum) {
                var ordering = parseInt(i)+1;
                if (ordering === agreeSum) {finalUser = true;} else {finalUser = false;}
                if (ordering === 1) {currentUser = true;} else {currentUser = false;}
                var element = {
                    "id" : null,
                    "ordering" : ordering,
                    "user" : {
                        "id" : data[i]['value']
                    },
                    "finalUser" : finalUser,
                    "currentUser" : currentUser
                }
                agreeList.push(element);
            }
        }
        return agreeList;
    }

    // Формирование объекта JSON для отправки на сервер
    function createJSON (id,dataType,dataField,dataBlock,block) {
        var id = parseInt(id);
        if(id === 0) {id = null;}
        var childFields = [];
        var executorDepartmentsIds = [];
        var whomList = '#whomList';
        var userListBlock = '#userListBlock';
        if(block && block === 1) {
            whomList = '#whomListNew';
            userListBlock = '#userListBlockNew';
        }
        var finalUserId =  $(userListBlock + ' select:last').val();
        var whomList = ChosenOrder.getSelectionOrder($(whomList).get(0));
        for (var i = 0; i < whomList.length; i++) {
            var element = whomList[i];
            if(element !== '') {
                executorDepartmentsIds.push(element);
            }
        }
        if(block && block === 2) {
            finalUserId =  $('#userListBlockDiv .row:last').attr('data-value');
            $('#whomList div').each(function() {
                var element = $(this).attr('data-value');
                if(element !== '') {
                    executorDepartmentsIds.push(element);
                }
            });
        }
        if(dataField !== "") {for (var key in dataField) {childFields.push(dataField[key]);}}
        if(dataBlock !== "") {for (var key in dataBlock) {childFields.push(dataBlock[key]);}}
        if(block && block === 2)  {
            var comment = $('#commentText textarea').val();
            var valueObj = {
                "id" : id,
                "docTypeId" : parseInt(dataType),
                "executorDepartmentsIds" : executorDepartmentsIds,
                "finalUserId" : finalUserId,
                "comment" : comment,
                "childFields" : childFields
            }
        } else if(block && block === 1)  {
            var valueObj = {
                "id" : null,
                "docTypeId" : parseInt(dataType),
                "executorDepartmentsIds" : executorDepartmentsIds,
                "finalUserId" : finalUserId,
                "parentDocId" : id,
                "childFields" : childFields
            }
        } else {
            var valueObj = {
                "id" : id,
                "docTypeId" : parseInt(dataType),
                "executorDepartmentsIds" : executorDepartmentsIds,
                "finalUserId" : finalUserId,
                "childFields" : childFields
            }
        }
        return valueObj;
    }

    // Функция получения записей в таблицу
    function dataTableArray (element, url, typeId) {
        var columns = [];
        columns.push(
            [
                { 'data': 'num' },
                { 'data': 'docStatus' },
                { 'data': 'regNum' },
                { 'data': 'regDateTime' },
                { 'data': 'docType' },
                { 'data': 'currentAgreeFullName' },
                { 'data': 'link' }
            ]
        );
        columns.push(
            [
                { 'data': 'num' },
                { 'data': 'docStatus' },
                { 'data': 'regNum' },
                { 'data': 'regDateTime' },
                { 'data': 'controlDate' },
                { 'data': 'docType' },
                { 'data': 'executorDepartments' },
                { 'data': 'executorUsers' },
                { 'data': 'link' }
            ]
        );
        columns.push(
            [
                { 'data': 'num' },
                { 'data': 'docStatus' },
                { 'data': 'regNum' },
                { 'data': 'regDateTime' },
                { 'data': 'controlDate' },
                { 'data': 'docType' },
                { 'data': 'currentAgreeFullName' },
                { 'data': 'executorDepartments' },
                { 'data': 'executorUsers' },
                { 'data': 'link' }
            ]
        );

        $(element).DataTable({
            "columns": columns[typeId],
            "ajax": {
                "url" : url,
                "dataSrc" : function(data) {
                    $.each(data, function(i, item) {
                        item.num = parseInt(i)+1;

                        switch(item.docStatus) {
                            case 'IN_WORK':
                                item.docStatus = 'На исполнении';
                                break;
                            case 'IN_AGREEMENT':
                                item.docStatus = 'На согласовании';
                                break;
                            case 'AGREEMENT_REJECTED':
                                item.docStatus = 'Согласование отменено';
                                break;
                            case 'COMPLETED':
                                item.docStatus = 'Исполнен';
                                break;
                            case 'DELETED':
                                item.docStatus = 'Удален';
                                break;
                        }

                        if (!item.regNum || item.regNum == '') {
                            item.regNum = item.projectRegNum;
                            item.regDateTime = formatDate(item.projectRegDateTime, 0);
                        } else {
                            item.regDateTime = formatDate(item.regDateTime, 0);
                        }
                        item.regNum = "<a href='agree-document?id=" + item.id + "'>" + item.regNum + "</a>";
                        if (!item.currentAgreeFullName || item.currentAgreeFullName == '') {
                            item.currentAgreeFullName = 'Согласование завершено';
                        }

                        if (item.controlDate && item.controlDate != '') {
                            item.controlDate = formatDate(item.controlDate, 0);
                            if (item.alarmControlDate) {
                                item.controlDate = '<span style="color: #ff0000;">' + item.controlDate + '</span>';
                            }
                        } else {
                            item.controlDate = '';
                        }

                        // Если поле не пустое отображаем поля
                        if(item.executorDepartments && item.executorDepartments != '') {
                            var dataDepartments = JSON.parse(item.executorDepartments);
                            item.executorDepartments = '';
                            for(var i in dataDepartments) {
                                if(dataDepartments[i].id > 0) {
                                    item.executorDepartments = item.executorDepartments + '<div class="d-inline-block amber lighten-4 rounded black-text my-1 mr-2 p-1" data-value="'+dataDepartments[i].id+'"><small>'+dataDepartments[i].name+'</small></div>';
                                }
                            }
                        }
                        // Если поле не пустое отображаем поля
                        if(item.executorUsers && item.executorUsers != '') {
                            var dataUsers = JSON.parse(item.executorUsers);
                            item.executorUsers = '';
                            for(var i in dataUsers) {
                                if(dataUsers[i].id > 0) {
                                    item.executorUsers = item.executorUsers + '<div class="d-inline-block green lighten-4 rounded black-text my-1 mr-2 p-1" data-value="'+dataUsers[i].id+'"><small>'+dataUsers[i].fullName+'</small></div>';
                                }
                            }
                        }
                        item.link = "<a href='agree-document?id=" + item.id + "'><i class='fas fa-edit text-primary'></i></a>";
                        //item.executorDepartments = '<form><select class="chosen-select" id="departmentList"><option value="'+item.executorDepartments+'">'+item.executorDepartments+'</option></select></form>';
                    });
                    return data;
                }
            },
            "iDisplayLength": 25,
            "language": {
                "processing": "Подождите...",
                "search": "Поиск:",
                "lengthMenu": "Показать _MENU_ записей",
                "info": "Страница _PAGE_ из _PAGES_",
                "infoEmpty": "",
                "infoFiltered": "(отфильтровано из _MAX_ записей)",
                "infoPostFix": "",
                "loadingRecords": "Загрузка записей...",
                "zeroRecords": "Записи отсутствуют.",
                "emptyTable": "В таблице отсутствуют данные",
                "paginate": {
                    "first": "",
                    "previous": "",
                    "next": "",
                    "last": ""
                },
                "aria": {
                    "sortAscending": ": активировать для сортировки столбца по возрастанию",
                    "sortDescending": ": активировать для сортировки столбца по убыванию"
                }
            }
        });
        $('.dataTables_length').addClass('bs-select');
        $('#departmentList').chosen({
            width: "100%",
            no_results_text: "Ничего не найдено!"
        });
    }

    // Получение данных об управлении по id
    function getDepartments (url) {
        $.ajax({
            url: url,
            dataType: 'json',
            async: false,
            //data: myData,
            success: function(data) {
                $('#whomList').append('<div class="d-inline-block chip light-blue lighten-2 white-text my-1 mr-2" data-value="' + data.id + '">' + data.name + '</div>');
            }
        });
        /*return $.getJSON(url, function(data) {
            $('#whomList').append('<div class="d-inline-block chip light-blue lighten-2 white-text my-1 mr-2" data-value="' + data.id + '">' + data.name + '</div>');
        });*/
    }

    // Формирование списка управлений без фозможности редактирования
    function createWhomListDisabled (url) {
        for(var i in url) {
            var row = url[i];
            getDepartments('rest/profile/departments/'+row);
        }
    }

    // Формирование списка пользователей без возможности редактирования
    function createUserListDisabled (url, finalVersion) {
        return $.getJSON(url, function(data) {
            for(var i in data) {
                var row = data[i];
                var position = '';
                var comment = '';
                var agreedDateTime = '';
                var undoUser = '';
                if(row.agreedDateTime) {
                    var newDate = formatDate(row.agreedDateTime);
                    var newTime = formatTime(row.agreedDateTime);
                    agreedDateTime = newDate+'/'+newTime;
                }
                if(row.comment) {comment = row.comment;}
                if(row.position) {position = row.position;}
                var currentUser = '';
                if(row.currentUser === true) {
                    currentUser = '<i class="fas fa-user-clock text-warning" title="Текущий согласователь"></i>';
                    if(finalVersion !== 1) {
                        undoUser = '<button class="btn btn-primary btn-sm px-2 py-1 mx-3 btnReturn" type="button" data-undo="'+row.userId+'" title="Перенаправить на согласование"><i class="fas fa-undo-alt text-white"></i></button>';
                    }
                } else {
                    currentUser = '<i class="fas fa-ellipsis-h text-muted" title="Согласователь"></i>';
                }
                if(row.decisionType && row.decisionType === 'ACCEPTED') {
                    currentUser = '<i class="fas fa-check text-success" title="Согласование завершено"></i>';
                    if(row.finalUser === true) {
                        currentUser = '<i class="fas fa-check-circle text-success" title="Финальный согласователь"></i>';
                    }
                }
                if(row.decisionType && row.decisionType === 'REDIRECTED') {
                    currentUser = '<i class="fas fa-undo-alt text-primary" title="Перенаправление"></i>';
                }
                if(row.decisionType && row.decisionType === 'REJECTED') {
                    currentUser = '<i class="fas fa-times text-danger" title="Согалсование отменено"></i>';
                }
                $('#userListBlockDiv').append('<div class="row mb-3 d-flex align-items-center" data-value="'+row.userId+'"><div class="col-1 text-center">'+row.ordering+'</div><div class="col-1 text-center">'+currentUser+'</div><div class="col-4">'+row.fullName+undoUser+'<br><small class="text-muted">'+position+'</small></div><div class="col-3"><small>'+comment+'</small></div><div class="col-3"><small>'+agreedDateTime+'</small></div></div>');
            }
        });
    }