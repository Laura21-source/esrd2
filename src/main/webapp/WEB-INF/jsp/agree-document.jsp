<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="fragments/headerNew.jsp"/>

<main>
    <div class="container-fluid text-center mb-4">
        <div class="card mx-auto w-100">
            <div class="card-body">
                <div class="container-fluid">
                    <div class="alert alert-secondary mb-3">
                        <div class="row d-flex align-items-center">
                            <div class="col-sm-6">
                                <h6 class="mt-2 documentName"></h6>
                            </div>
                            <div class="col-sm-6">
                                <button type="button" class="btn btn-primary btn-sm float-right rounded" data-toggle="modal" data-target="#listAgree">Список согласования</button>
                            </div>
                        </div>
                    </div>
                    <form class="registrationForm">
                        <div class="row">
                            <div class="col-lg-6 col-12">
                                <div class="row ml-1 mb-3">
                                    <div class="col-12">
                                        <div class="row">
                                            <div class="col-md-4 text-left mt-2">
                                                <span class="text-muted"><i class="fas fa-file-alt mr-2"></i> Вид документа</span>
                                            </div>
                                            <div class="col-md-8">
                                                <select class="browser-default custom-select" name="selectType" id="selectType">
                                                    <option value="" class="alert-primary">Выберите вид документа</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row ml-1 mb-3">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-4 text-left mt-2">
                                                <span for="inputDate" class="text-muted inputDateName"></span>
                                            </div>
                                            <div class="col-md-8">
                                                <input title="Выберите дату" type="date" id="inputDate" name="inputDate" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row ml-1 mb-3">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-4 text-left mt-2">
                                                <span for="inputTime" class="text-muted inputTimeName"></span>
                                            </div>
                                            <div class="col-md-8">
                                                <input title="Выберите время" type="time" id="inputTime" name="inputTime" class="form-control">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div id="templateBlock" class="card p-3 mb-3">
                                    <h5 class="templateBlockName"></h5>
                                    <div class="card-body">
                                        <div id="newBlockQuestion"></div>
                                        <%--<div class="row card mb-3" id="blockQuestion" req="true">
                                            <div class="col-12">
                                                <div class="card-body">
                                                    <div class="row">
                                                        <div class="col-md-9 text-left">
                                                            <h6 id="nameQuestion"></h6>
                                                        </div>
                                                        <div class="col-md-3 text-right">
                                                            <div id="delQuestion" class="btn btn-danger btn-sm pointer delQuestion d-none rounded" title="Удалить вопрос"><i class="fas fa-trash"></i></div>
                                                        </div>
                                                    </div>
                                                    <hr>
                                                    <div class="row templateBlockSelect"></div>
                                                </div>
                                            </div>
                                        </div>--%>
                                        <div class="marginBlock my-3"></div>
                                        <div class="row">
                                            <div class="col-12 text-right">
                                                <button type="button" class="btn btn-primary btn-sm pointer addQuestion mr-3 submitBtn rounded" title="Добавить вопрос"><i class="fas fa-plus"></i> Добавить</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-12">
                                <div class="container-fluid mx-2">
                                    <div class="sticky-content">
                                        <div class="alert alert-primary mx-auto text-uppercase">Готовый документ</div>
                                        <div class="embed-responsive embed-responsive-1by1 z-depth-1-half mb-3">
                                            <iframe class="embed-responsive-item" src="resources/img/pdf_12.pdf" height="500"></iframe>
                                        </div>
                                        <div class="btn btn-primary btn-md my-3 rounded">Переформировать</div>
                                        <a class="btn btn-light btn-md my-3" href="resources/img/pdf_12.pdf" target="_blank">Открыть в новом окне</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </form>
                    <button type="submit" id="btnSave" class="btn btn-success mb-2 my-4 pt-3 rounded btnSave">Согласовать</button>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="fragments/footerNew.jsp"/>
<jsp:include page="fragments/modals/agreeDocumentModal.jsp"/>
<jsp:include page="fragments/footerScript.jsp"/>
<script>
    $(function() {
        // Получаем id документа из строки
        var urlString = window.location.href;
        var url = new URL(urlString);
        var id = url.searchParams.get("id");
        // Переменная выделения полей
        var selectedField = '';
        // Подключение стека полей
        $.getJSON("rest/profile/docs/" + id, function(data) {
            var newDate = new Date(data.projectRegDateTime);
            function formatDate(date) {
                var day = date.getDate();
                var month = date.getMonth()+1;
                if(month < 10) {
                    month = '0' + month;
                }
                var year = date.getFullYear();
                return day + '-' + month + '-' + year;
            }
            var newDate = formatDate(newDate);
            $(".documentName").html('Согласование документа №' + data.projectRegNum + ' от ' + newDate);
            // Получение списка полей вида документа
            $.getJSON("rest/profile/doctypes/", function(dataType) {
                for(var k in dataType) {
                    var rowType = dataType[k];
                    if(data.docTypeId === rowType.id) {
                        selectedField = 'selected="selected"';
                    } else {
                        selectedField = '';
                    }
                    $("#selectType").append('<option value="' + rowType.id + '"' + selectedField + '>'+ rowType.name +'</option>');
                }
            });
            // Получение основных полей
            var newBlockArray = [];
            for(var i in data.childFields) {
                var rowFields = data.childFields[i];
                if(rowFields.field.fieldType === "DATE") {
                    var newDateRevers = new Date(rowFields.field.valueDate);
                    function formatDateRevers(date) {
                        var day = date.getDate();
                        var month = date.getMonth()+1;
                        if(month < 10) {
                            month = '0' + month;
                        }
                        var year = date.getFullYear();
                        return year + '-' + month + '-' + day;
                    }
                    var newDateRevers = formatDateRevers(newDateRevers);
                    $(".inputDateName").html('<i class="fas fa-calendar-alt mr-2"></i>' + rowFields.field.name);
                    $("#inputDate").attr("value", newDateRevers);
                }
                if(rowFields.field.fieldType === "TIME") {
                    var newTime = new Date(rowFields.field.valueDate);
                    function formatTime(date) {
                        var hours = date.getHours();
                        var minutes = date.getMinutes();
                        if(minutes < 10) {
                            minutes = '0' + minutes;
                        }
                        return hours + ':' + minutes;
                    }
                    var newTime = formatTime(newTime);
                    $("#inputTimeBlock").removeClass("d-none");
                    $(".inputTimeName").html('<i class="fas fa-clock mr-2"></i>' + rowFields.field.name);
                    $("#inputTime").attr("value", newTime);
                }
                if(rowFields.field.fieldType === "GROUP_FIELDS" && rowFields.field.fieldId === 22) {
                    var newBlockArrayElem = {
                        "field" : rowFields.field
                    }
                    newBlockArray.push(newBlockArrayElem);
                }
            }
            //console.log(newBlockArray);
            for(var key in newBlockArray) {
                key = parseInt(key);
                var newRowFields = newBlockArray[key];
                var dubbleKey = parseInt(key+1);
                var questionKey = '';
                var delButton = ' d-none';
                if(key != 0) {
                    questionKey = key;
                    delButton = '';
                }
                $("#newBlockQuestion").append('<div class="row card mb-3 blockQuestion" id="blockQuestion' + questionKey + '" data-field="' + key + '" req="true"><div class="col-12"><div class="card-body"><div class="row"><div class="col-md-9 text-left"><h6 id="nameQuestion' + questionKey + '">Вопрос ' + dubbleKey + '</h6></div><div class="col-md-3 text-right"><div id="delQuestion' + questionKey + '" class="btn btn-danger btn-sm pointer delQuestion rounded' + delButton + '" title="Удалить вопрос"><i class="fas fa-trash"></i></div></div></div><hr><div class="row templateBlockSelect"></div></div></div></div>');
                $(".templateBlockName").html(newRowFields.field.name);
                for (var y in newRowFields.field.childFields) {
                    var rowSelectField = newRowFields.field.childFields[y];
                    var selectFieldName = "selectField" + rowSelectField.catalogId;
                    $("#blockQuestion" + questionKey + " .templateBlockSelect").append('<div class="col-md-3 text-left mt-3"><span class="text-muted">' + rowSelectField.name + '</span></div><div class="col-md-9 mt-3"><select class="browser-default custom-select white" id="' + selectFieldName + '" name="' + selectFieldName + '" data-field="' + rowSelectField.fieldId + '" seq="true"><option value="" class="alert-primary">Выберите значение справочника</option></select></div>');
                    // Номер каталога
                    var numberCatalog = rowSelectField.catalogId;
                    var newnumberCatalog = ('#blockQuestion' + questionKey + ' #selectField' + numberCatalog);
                    // Номер поля для отметки в селектах
                    var numberField = rowSelectField.valueInt;
                    function createSelectedId(url, numberCatalog, numberField) {
                        $.getJSON(url, function (dataOption) {
                            for (var m in dataOption) {
                                var rowOption = dataOption[m];
                                if (numberField === rowOption.id) {
                                    selectedField = 'selected="selected"';
                                } else {
                                    selectedField = '';
                                }
                                //console.log('#blockQuestion' + questionKey + ' #selectField' + numberCatalog);
                                $(numberCatalog).append('<option value="' + rowOption.id + '" ' + selectedField + '>' + rowOption.valueStr + '</option>');
                            }
                        });
                    }
                    createSelectedId("rest/profile/catalogs/" + numberCatalog + "/elems", newnumberCatalog, numberField);
                }
            }
        });
        // Формирование объекта JSON
        function createJSON(id,dataType,dataDate,dataTime,dataField) {
            var newId = parseInt(id);
            if(newId === 0) {newId = null;}
            var newdataType = parseInt(dataType);
            var childFields = [];
            if(dataDate || dataTime) {
                var field1 = '';
                var field2 = '';
                if(dataDate !== "") {
                    var newData = dataDate + "T00:00:00";
                    field1 = {
                        "field" : {
                            "id" : null,
                            "childFields" : [],
                            "fieldId" : 4,
                            "valueDate" : newData
                        },
                        "position" : 1,
                    }
                    childFields.push(field1);
                }
                if(dataTime !== "") {
                    var newTime = dataDate + "T" + dataTime + ":00";
                    field2 = {
                        "field" : {
                            "id" : null,
                            "childFields" : [],
                            "fieldId" : 5,
                            "valueDate" : newTime
                        },
                        "position" : 2,
                    }
                    childFields.push(field2);
                }
            }
            if(dataField !== "") {
                for(var key in dataField){
                    childFields.push(dataField[key]);
                }
            }
            var valueObj = {
                "id" : newId,
                "docTypeId" : newdataType,
                "childFields" : childFields
            }
            return valueObj;
        }

        // Кнопка отправки
        $('#btnSave').on("click", function(event){
            event.preventDefault();
            var dataType = $("#selectType").val();
            var dataDate = $("#inputDate").val();
            var dataTime = $("#inputTime").val();
            var dataFieldArray = [];
            $('.blockQuestion').each(function(i) {
                if($(this).attr("data-field") == i) {
                    var fieldOptionBlock = "#blockQuestion";
                    if(i !== 0) {
                        fieldOptionBlock = fieldOptionBlock + i;
                    }
                    var fieldOptionArray = [];
                    $(fieldOptionBlock + ' select[data-field]').each(function(){
                        var fieldOption = {
                            "id" : null,
                            "childFields" : [],
                            "fieldId" : $(this).attr("data-field"),
                            "valueInt" : $(this).val()
                        }
                        fieldOptionArray.push(fieldOption);
                    });
                    var position = 3+i;
                    var dataField = {
                        "field" : {
                            "id" : null,
                            "childFields": fieldOptionArray,
                            "fieldId" : 22,
                        },
                        "position" : position
                    }
                }
                dataFieldArray.push(dataField);
            });
            var serverStack = createJSON(0,dataType,dataDate,dataTime,dataFieldArray);
            console.log(serverStack);
            var serverAjax = $.ajax({
                type: "POST",
                url: 'rest/profile/docs',
                data: JSON.stringify(serverStack),
                contentType: 'application/json; charset=utf-8'
            });
            /*serverAjax.done(function(data) {
                var projectRegNum = data.projectRegNum;
                $('#btnSuccess').modal('show');
                $('#btnSuccess #regNumTemplate').html(projectRegNum);
                $('#btnSuccess').on('hidden.bs.modal', function (e) {
                    window.location.href="temp-list";
                });
            });*/
        });
    });
</script>
<jsp:include page="fragments/footerBasement.jsp"/>