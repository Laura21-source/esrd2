    // Формирование массива элементов для JSON поля вне GROUP_FIELDS
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
            var valueInt = null;
            var valueStr = null;
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
            if (attrElem === "tableHtml") {
                valueData = 5;
                valueInt = $('#catalogTables').attr('data-value');
                //$('.editTable table td').trim();
                $('.editTable .deleteElem').remove();
                $('.editTable h6').remove();
                valueStr = $('.editTable').html();
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
                if(id > 0) {idField = parseInt($('.chosen-select', this).attr("data-id"));}
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
                if(id > 0) {idField = parseInt($(this).attr("data-id"));}
                var childBox = [];
                var childField = '';
                $('.childBox .checkClass').each(function() {
                    var childFieldId = null;
                    if (id > 0) {childFieldId = parseInt($(this).attr("data-id"));}
                    var childFieldVal = $(this).val();
                    var childFieldField = parseInt($(this).attr("data-field"));
                    var typeAttr = $(this).attr('type');
                    if(typeAttr === "select") {
                        childField = {
                            "id": childFieldId,
                            "childFields": [],
                            "fieldId": childFieldField,
                            "valueInt": childFieldVal
                        }
                    } else {
                        childField = {
                            "id" : childFieldId,
                            "childFields" : [],
                            "fieldId" : childFieldField,
                            "valueStr" : childFieldVal
                        }
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
            } else if (valueData === 5) {
                field = {
                    "field": {
                        "id" : idField,
                        "childFields": [],
                        "fieldId": attrId,
                        "valueInt" : valueInt,
                        "valueStr" : valueStr
                    },
                    "position": key
                }
            }
            //console.log(field);
            dataField.push(field);
        });
        return dataField;
    }

    function createDataBlock (id, key, block) {
        //alert(key);
        var blockGroup = '.blockGroup';
        var blockGroupId = '#blockGroup';
        if(block && block === 1) {
            blockGroup = '.blockGroupNew';
            blockGroupId = '#blockGroupNew';
        }
        var id = parseInt(id);
        var idField = null;
        var dataBlock = [];
        $('.BlockDiv').each(function() {
            var name = $(this).attr('id');
            $('#'+name+' .blockField '+blockGroup).each(function(item) {
                var i = parseInt(item)+1;
                if($(this).attr("data-field") == i) {
                    var elementBlock = '#'+name+' '+blockGroup+' .blockGroupFields .blockRow';
                    var blockName = $(this).attr('id');
                    //if(i !== 0) {elementBlock = elementBlock+i;}
                    var elementArray = [];
                    $('#'+blockName+' [data-field]').each(function() {
                        //alert(elementBlock);
                        var typeAttr = $(this).attr("type");
                        if(id > 0) {idField = parseInt($(this).attr("data-id"));}
                        if (typeAttr && typeAttr != '') {
                            if (typeAttr === "tableHtml") {
                                var valueInt = $(this).attr('data-value');
                                var tabId = '#'+$(this).attr('id');
                                $(tabId+' .editTable .deleteElem, '+tabId+' .editTable h6').remove();
                                var valueStr = $(tabId+' .editTable').html();
                                var elementBlockElem = {
                                    "id" : idField,
                                    "childFields" : [],
                                    "fieldId" : parseInt($(this).attr("data-field")),
                                    "valueInt": valueInt,
                                    "valueStr" : valueStr
                                }
                            } else if(typeAttr === "select") {
                                var elementBlockElem = {
                                    "id" : idField,
                                    "childFields" : [],
                                    "fieldId" : parseInt($(this).attr("data-field")),
                                    "valueInt" : parseInt($(this).val())
                                }
                            } else if(typeAttr === "text") {
                                var elementBlockElem = {
                                    "id" : idField,
                                    "childFields" : [],
                                    "fieldId" : parseInt($(this).attr("data-field")),
                                    "valueStr" : $(this).val()
                                }
                            }
                        } else {
                            var elementBlockElem = {
                                "id" : idField,
                                "childFields" : [],
                                "fieldId" : parseInt($(this).attr("data-field")),
                                "valueStr" : $(this).val()
                            }
                        }
                        elementArray.push(elementBlockElem);
                    });
                    var position = parseInt(key)+i;
                    var fieldId = parseInt($('#'+blockName).attr("data-div"));
                    if(id > 0) {idField = parseInt($('#'+blockName).attr("data-id"));}
                    var dataBlockElement = {
                        "field" : {
                            "id" : idField,
                            "childFields": elementArray,
                            "fieldId" : fieldId,
                        },
                        "position" : position
                    }
                } else {
                    alert($(this).attr("data-field")+' - '+i);
                }
                dataBlock.push(dataBlockElement);
            });
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