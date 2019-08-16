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
                        <h4 class="mt-2">Подготовка проекта документа</h4>
                        <h4 id="cartSuccess" class="d-none mt-2">Карточка документа №согл-1/19 от 08.08.2019</h4>
                    </div>
                    <form class="registrationForm">
                        <div class="row ml-1 mb-3">
                            <div class="col-md-2 text-left mt-2">
                                <span class="text-muted"><i class="fas fa-file-alt mr-2"></i> Вид документа</span>
                            </div>
                            <div class="col-md-10">
                                <select class="browser-default custom-select" name="selectType" id="selectType">
                                    <option value="" class="alert-primary" selected>Выберите вид документа</option>
                                </select>
                            </div>
                        </div>
                        <div id="templateForm" class="d-none">
                            <div class="row ml-1 mb-3 d-none" id="inputDateBlock">
                                <div class="col-md-6">
                                    <div class="row">
                                        <div class="col-md-4 text-left">
                                            <span for="inputDate" class="text-muted inputDateName"></span>
                                        </div>
                                        <div class="col-md-8">
                                            <input title="Выберите дату" type="date" id="inputDate" name="inputDate" class="white form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">&nbsp;</div>
                            </div>
                            <div class="row ml-1 mb-3 d-none" id="inputTimeBlock">
                                <div class="col-md-6">
                                    <div class="row">
                                        <div class="col-md-4 text-left">
                                            <span for="inputTime" class="text-muted inputTimeName"></span>
                                        </div>
                                        <div class="col-md-8">
                                            <input title="Выберите время" type="time" id="inputTime" name="inputTime" class="white form-control">
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6">&nbsp;</div>
                            </div>
                        </div>
                        <div id="templateBlock" class="d-none card p-3">
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
                                        <div class="btn btn-primary btn-sm pointer addQuestion mr-3 rounded" title="Добавить вопрос"><i class="fas fa-plus"></i> Добавить</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button type="submit" id="btnSave" class="btn btn-success mb-2 pt-3 submitBtn rounded d-none" data-toggle="modal" data-target="#btnSuccess">Зарегистрировать</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="fragments/footerNew.jsp"/>
<jsp:include page="fragments/modals/newDocumentModal.jsp"/>
<jsp:include page="fragments/footerScript.jsp"/>
<script>
    $(function() {
        // Список полей вида документов
        $.getJSON("rest/profile/doctypes/", function(data) {
            for(var i in data) {
                var row = data[i];
                $("#selectType").append('<option value="' + row.id + '">'+ row.name +'</option>');
            }
        });

        $("#selectType").change(function(){
            var asd = $("#selectType").val();
            if(asd == 0) {
                $("#templateForm, #templateBlock, #btnSave").addClass("d-none");
            } else {
                $("#templateForm, #templateBlock, #btnSave").removeClass("d-none");
                // Список полей по виду документа
                var typeDocument = "rest/profile/doctypes/" + asd + "/fields";
                $.getJSON(typeDocument, function(dataType) {
                    for(var i in dataType) {
                        var rowType = dataType[i];
                        if(rowType.field.fieldType === "TIME") {
                            $("#inputTimeBlock").removeClass("d-none");
                            $(".inputTimeName").html('<i class="fas fa-clock mr-2"></i>' + rowType.field.name);
                        }
                        if(rowType.field.fieldType === "DATE") {
                            $("#inputDateBlock").removeClass("d-none");
                            $(".inputDateName").html('<i class="fas fa-calendar-alt mr-2"></i>' + rowType.field.name);
                        }
                        if(rowType.field.fieldType === "GROUP_FIELDS") {
                            $(".templateBlockName").html(rowType.field.name);
                            // Количество селектов в базе
                            var sumSelectBase = rowType.field.childFields.length;
                            // Количество селектов на странице
                            var sumSelectPage = $("[seq='true']").length;
                            for(var k in rowType.field.childFields) {
                                var rowSelectField = rowType.field.childFields[k];
                                var selectFieldName = "selectField" + rowSelectField.catalogId;
                                //console.log(rowSelectField.catalogId);
                                // Количество селектов на базе должно быть больше чем на странице
                                if(sumSelectBase > sumSelectPage) {
                                    $(".templateBlockSelect").append('<div class="col-md-3 text-left mt-3"><span class="text-muted">' + rowSelectField.name + '</span></div><div class="col-md-9 mt-3"><select class="browser-default custom-select" id="' + selectFieldName + '" name="' + selectFieldName + '" seq="true"><option value="" class="alert-primary" selected>Выберите значение справочника</option></select></div>');
                                    // Номер каталога
                                    var numberCatalog = rowSelectField.catalogId;
                                    function createOptionFields(url, numberCatalog) {
                                        $.getJSON(url, function(dataOption) {
                                            for(var y in dataOption) {
                                                var rowOption = dataOption[y];
                                                $('#selectField' + numberCatalog).append('<option value="' + rowOption.id + '">' + rowOption.valueStr + '</option>');
                                            }
                                        });
                                    }
                                    createOptionFields("rest/profile/catalogs/" + rowSelectField.catalogId + "/elems", numberCatalog);
                                }
                            }
                        }
                    }
                })
                .done(function(response){
                    if(response.length == 0){
                        $("#templateForm, #templateBlock, #btnSave").addClass("d-none");
                    }
                });
            }
        });
        // Кнопка отправки
        $('.submitBtn').click(function(event){
            event.preventDefault();
            var serverStack = $(".registrationForm").serializeArray();
            /*$(".registrationForm").find('input, textarea, select').each(function(){
                var valueObj = {
                    'name' : $(this).attr('id'),
                    'value' : $(this).val()
                }
                // Преобразуем в JSON
                var serverStack = JSON.stringify(valueObj);
                console.log(serverStack);
            });*/
            console.log(serverStack);
            //$('#templateForm, #templateBlock').addClass("d-none");
        });
    });
</script>
<jsp:include page="fragments/footerBasement.jsp"/>