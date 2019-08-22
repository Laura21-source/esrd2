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
                                <div id="blockUp"></div>
                                <div id="blockDown" class="card p-3">
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
                            </div>
                            <div class="col-lg-6 col-12">
                                <div class="container-fluid mx-2">
                                    <div class="sticky-content">
                                        <div class="alert alert-primary mx-auto text-uppercase">Готовый документ</div>
                                        <div class="embed-responsive embed-responsive-1by1 z-depth-1-half mb-3">
                                            <iframe class="embed-responsive-item pdfSRC" src="" height="500"></iframe>
                                        </div>
                                        <div id="btnReformat" class="btn btn-primary btn-md my-3 rounded">Переформировать</div>
                                        <a class="btn btn-light btn-md my-3 pdfHREF" href="" target="_blank">Открыть в новом окне</a>
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
        // Список всех документов
        var docAllURL = "rest/profile/doctypes/";
        // Получаем id документа из строки
        var id = getId();
        // Поля определённого документа
        var docURL = "rest/profile/docs/" + id;
        // Подключение стека полей
        $.getJSON(docURL, function(data) {
            // Дата документа
            var newDate = '';
            if(data.projectRegDateTime) {
                newDate = formatDate(new Date(data.projectRegDateTime), 0);
            }
            $(".documentName").html('Согласование документа №' + data.projectRegNum + ' от ' + newDate);
            // Ссылки на докусмент PDF
            var documentPDF = 'resources/img/pdf_12.pdf';
            $('.pdfSRC').attr('src', documentPDF);
            $('.pdfHREF').attr('href', documentPDF);
            // Получение списка полей вида документа
            createOptions(docAllURL, "#selectType", "name", "id", data.docTypeId);
            // Получение основных полей
            getFieldsDocument("rest/profile/docs/" + id + "/", id, 0);
        });

        // Отправка на сервер
        $('#btnSave').on("click", function(event) {
            event.preventDefault();
            var dataType = $("#selectType").val();
            // Формируем поля JSON
            var dataField = createDataField();
            var sumElem = countElem(dataField)+1;
            var dataBlock = createDataBlock(sumElem);
            var serverStack = JSON.stringify(createJSON(id,dataType,dataField,dataBlock));
            console.log(serverStack);
            /*var serverAjax = $.ajax({
                type: "POST",
                url: 'rest/profile/docs',
                data: serverStack,
                contentType: 'application/json; charset=utf-8'
            });
            serverAjax.done(function() {
                $('#btnSuccess').modal('show');
                //console.log(data);
                $('#btnSuccess').on('hidden.bs.modal', function() {
                    window.location.href="temp-list";
                });
            });*/
        });

        // Отправка на сервер файла PDF
        $('#btnReformat').on("click", function(event) {
            event.preventDefault();
            var dataType = $("#selectType").val();
            // Формируем поля JSON
            var dataField = createDataField();
            var sumElem = countElem(dataField)+1;
            var dataBlock = createDataBlock(sumElem);
            var reformatPDF = JSON.stringify(createJSON(id,dataType,dataField,dataBlock));
            console.log(reformatPDF);
            /*$.ajax({
                type: "POST",
                url: 'rest/profile/docs/pdf',
                data: reformatPDF,
                contentType: 'application/json; charset=utf-8'
            });*/
        });
    });
</script>
<jsp:include page="fragments/footerBasement.jsp"/>