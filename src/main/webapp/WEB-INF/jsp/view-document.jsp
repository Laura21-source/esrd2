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
                    <div class="alert alert-secondary text-center mb-3">
                        <h6 class="mt-2 documentName"></h6>
                    </div>
                    <form class="registrationForm">
                        <div class="row ml-1 mb-3">
                            <div class="col-md-2 text-left">
                                <span class="text-muted"><i class="fas fa-file-alt mr-2"></i> Вид документа</span>
                            </div>
                            <div class="col-md-10">
                                <select class="browser-default custom-select" name="selectType" id="selectType">
                                    <option value="" class="alert-primary">Выберите вид документа</option>
                                </select>
                            </div>
                        </div>
                        <div id="formFieldEdit">
                            <div class="row ml-1 mb-3">
                                <div class="col-md-6">
                                    <div class="row">
                                        <div class="col-md-4 text-left">
                                            <span for="inputDate" class="text-muted inputDateName"></span>
                                        </div>
                                        <div class="col-md-8">
                                            <input title="Выберите дату" type="date" id="inputDate" name="inputDate" class="white form-control" disabled>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">&nbsp;</div>
                            </div>
                            <div class="row ml-1 mb-3">
                                <div class="col-md-6">
                                    <div class="row">
                                        <div class="col-md-4 text-left">
                                            <span for="inputTime" class="text-muted inputTimeName"></span>
                                        </div>
                                        <div class="col-md-8">
                                            <input title="Выберите время" type="time" id="inputTime" name="inputTime" class="white form-control" disabled>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">&nbsp;</div>
                            </div>
                            <div id="templateBlock" class="card p-3 mb-3">
                                <h5 class="templateBlockName"></h5>
                                <div class="card-body">
                                    <div class="row card mb-3" id="blockQuestion" req="true">
                                        <div class="col-12">
                                            <div class="card-body">
                                                <div class="row">
                                                    <div class="col-md-9 text-left">
                                                        <h6 id="nameQuestion">Вопрос 1</h6>
                                                    </div>
                                                    <div class="col-md-3 text-right">
                                                        <div id="delQuestion" class="btn btn-danger btn-sm pointer delQuestion d-none rounded" title="Удалить вопрос"><i class="fas fa-trash"></i></div>
                                                    </div>
                                                </div>
                                                <hr>
                                                <div class="row templateBlockSelect"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="marginBlock my-3"></div>
                                    <div class="row">
                                        <div class="col-12 text-right">
                                            <button type="button" class="btn btn-primary btn-sm pointer addQuestion mr-3 submitBtn rounded" title="Добавить вопрос" disabled><i class="fas fa-plus"></i> Добавить</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!--<button id="closeDocument" type="submit" class="btn btn-danger mb-5 pt-3 submitBtn rounded" data-toggle="modal" data-target="#registrationCansel">Отмена</button>-->
                            <a href="agree-document" id="closeDocument" type="button" class="btn btn-danger mb-5 pt-3 rounded">Отмена</a>
                            <button id="editDocument" type="submit" class="btn btn-primary mb-5 pt-3 submitBtn rounded">Правка</button>
                            <div href="#" onclick="window.location.reload();" id="cancelDocument" class="btn btn-danger mb-5 pt-3 rounded d-none">Отменить</div>
                            <button id="saveDocument" type="submit" class="btn btn-success mb-5 pt-3 submitBtn rounded d-none" data-toggle="modal" data-target="#registrationSuccess">Сохранить</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="fragments/footerNew.jsp"/>
<jsp:include page="fragments/modals/viewDocumentModal.jsp"/>
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
            var newDate = new Date(data.regDate);
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
            $(".documentName").html('Карточка документа №' + data.regNum + ' от ' + newDate);
            // Получение списка полей вида документа
            $.getJSON("rest/profile/doctypes/", function(dataType) {
                for(var k in dataType) {
                    var rowType = dataType[k];
                    if(data.docTypeId === rowType.id) {
                        selectedField = 'selected="selected"';
                    } else {
                        selectedField = '';
                    }
                    $("#selectType").append('<option value="' + rowType.id + '"' + selectedField + ' disabled>'+ rowType.name +'</option>');
                }
            });
            // Получение основных полей
            for(var i in data.docValuedFields) {
                var rowFields = data.docValuedFields[i];
                if(rowFields.field.fieldType === "DATE") {
                    var newDateRevers = new Date(rowFields.field.value);
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
                    var newTime = new Date(rowFields.field.value);
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
                if(rowFields.field.fieldType === "GROUP_FIELDS") {
                    $(".templateBlockName").html(rowFields.field.name);
                    // Количество селектов в базе
                    var sumSelectBase = rowFields.field.childFields.length;
                    // Количество селектов на странице
                    var sumSelectPage = $("[seq='true']").length;
                    for(var y in rowFields.field.childFields) {
                        var rowSelectField = rowFields.field.childFields[y];
                        var selectFieldName = "selectField" + rowSelectField.catalogId;
                        // Количество селектов на базе должно быть больше чем на странице
                        if(sumSelectBase > sumSelectPage) {
                            $(".templateBlockSelect").append('<div class="col-md-3 text-left mt-3"><span class="text-muted">' + rowSelectField.name + '</span></div><div class="col-md-9 mt-3"><select class="browser-default custom-select" id="' + selectFieldName + '" name="' + selectFieldName + '" seq="true"><option value="" class="alert-primary">Выберите значение справочника</option></select></div>');
                            $.getJSON("rest/profile/catalogs/" + rowSelectField.catalogId + "/elems", function(dataOption) {
                                for(var y in dataOption) {
                                    var rowOption = dataOption[y];
                                    console.log(rowSelectField.value + " - " + rowOption.id);
                                    if(rowSelectField.value === rowOption.id) {
                                        selectedField = 'selected="selected"';
                                    } else {
                                        selectedField = '';
                                    }
                                    $('#selectField' + rowOption.catalogId).append('<option value="' + rowOption.id + '" ' + selectedField + ' disabled>' + rowOption.valueStr + '</option>');
                                }
                            });
                        }
                    }
                }
            }
        });
    });
</script>
<jsp:include page="fragments/footerBasement.jsp"/>