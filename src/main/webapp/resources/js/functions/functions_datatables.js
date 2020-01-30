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
                { 'data': 'link' },
                { 'data': 'edit' }
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
                        item.edit =
                            '<div class="form-check">' +
                            '    <input type="checkbox" class="form-check-input makeAnswer" id="doc'+item.id+'" data-document="'+item.id+'">' +
                            '    <label class="form-check-label" for="doc'+item.id+'"></label>' +
                            '</div>';
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

    // Показываем кнопку для отправки
    $(document).on('click', '.makeAnswer', function() {
        var sumMake = $('.makeAnswer:checked').length;
        if(sumMake > 1) {
            $('#btnAnswer').removeClass('d-none');
        } else {
            $('#btnAnswer').addClass('d-none').attr('href', '#');
        }
    });

    // Получаем ссылку на страницу по нажатию на кнопку отправить
    $(document).on('click', '#btnAnswer', function() {
        var allDocs = '';
        $('.makeAnswer:checked').each(function() {
            var docId = $(this).attr('data-document');
            allDocs = allDocs+docId+',';
        });
        allDocs = allDocs.slice(0, -1);
        var newLink = 'merge-document?id='+allDocs;
        $('#btnAnswer').attr('href', newLink);
    });