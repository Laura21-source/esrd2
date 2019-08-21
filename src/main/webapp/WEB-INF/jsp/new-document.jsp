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
                        <div id="blockUp" class="d-none"></div>
                        <div id="blockDown" class="d-none card p-3">
                            <h5 class="blockName"></h5>
                            <div class="card-body">
                                <div id="newBlockGroup"></div>
                                <div class="marginBlock my-3"></div>
                                <div class="row">
                                    <div class="col-12 text-right">
                                        <div class="btn btn-primary btn-sm pointer addGroup mr-3 rounded" title="Добавить вопрос"><i class="fas fa-plus"></i> Добавить</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <button type="submit" id="btnSave" class="btn btn-success mb-2 my-4 pt-3 rounded d-none btnSave">Зарегистрировать</button>
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
        createOptions("rest/profile/doctypes/", "#selectType", "name", "id", "");

        // Выбор типа документа
        $("#selectType").change(function() {
            $('#blockUp, #newBlockGroup').empty();
            var asd = $("#selectType").val();
            if(asd == 0) {
                $("#bBlockUp, #blockDown, #btnSave").addClass("d-none");
            } else {
                $("#blockUp, #blockDown, #btnSave").removeClass("d-none");
                // Список полей по виду документа
                getFieldsDocument("rest/profile/doctypes/" + asd + "/fields", 0, 1);
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
                            //"id" : null,
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
                            //"id" : null,
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
                            //"id" : null,
                            "childFields" : [],
                            "fieldId" : $(this).attr("data-field"),
                            "valueInt" : $(this).val()
                        }
                        fieldOptionArray.push(fieldOption);
                    });
                    var position = 3+i;
                    var dataField = {
                        "field" : {
                            //"id" : null,
                            "childFields": fieldOptionArray,
                            "fieldId" : 6,
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
            serverAjax.done(function(data) {
                var projectRegNum = data.projectRegNum;
                $('#createSave').modal('show');
                $('#createSave #regNumTemplate').html(projectRegNum);
                $('#createSave').on('hidden.bs.modal', function() {
                    $("#blockUp, #blockDown, #btnSave").addClass("d-none");
                });
            });
        });
    });
</script>
<jsp:include page="fragments/footerBasement.jsp"/>