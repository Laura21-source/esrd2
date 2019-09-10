<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="fragments/headerNew.jsp"/>
<c:set var = "main" />
<main>
    <div class="container-fluid text-center mb-4">
        <div class="card mx-auto w-100">
            <div class="card-body">
                <div class="container-fluid">
                    <div class="alert alert-secondary text-center mb-3">
                        <h4 class="mt-2">Подготовка проекта документа</h4>
                    </div>
                    <form class="registrationForm needs-validation" novalidate>
                        <div class="row ml-1 mb-3">
                            <div class="col-md-4 text-left mt-2">
                                <span class="text-muted"><i class="fas fa-file-alt mr-2"></i> Вид документа</span>
                            </div>
                            <div class="col-md-4">
                                <select class="browser-default custom-select" name="selectType" id="selectType" required>
                                    <option value="" class="alert-primary" selected>Выберите вид документа</option>
                                </select>
                                <div class="invalid-tooltip">Выберите тип документа</div>
                            </div>
                            <div class="col-md-2">&nbsp;</div>
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
                        <button type="submit" id="btnSave" class="btn btn-success mb-2 my-4 pt-3 rounded d-none btnSave">Отправить на согласование</button>
                        <button type="button" id="btnSluzh" class="btn btn-warning mb-2 my-4 pt-3 rounded d-none btnSave">Сгенерировать служебную записку</button>
                        <a href="" type="button" id="btnLoad" class="btn btn-primary mb-2 my-4 pt-3 rounded d-none btnSave">Скачать файл</a>
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
        createOptions("rest/profile/doctypes/", "#selectType", "name", "id", "", "", "", "", "", "", "", "","","","");

        // Выбор типа документа
        $("#selectType").change(function() {
            // Убрать с экрана все предыдущие поля
            $('#blockUp, #newBlockGroup').empty();
            $('.blockGroup').remove();
            var asd = $("#selectType").val();
            if(asd == 0) {
                $("#bBlockUp, #blockDown, #btnSave, #btnSluzh").addClass("d-none");
            } else {
                $("#blockUp, #blockDown, #btnSave, #btnSluzh").removeClass("d-none");
                // Список полей по виду документа
                getFieldsDocument("rest/profile/doctypes/" + asd + "/fields", 0, 1);
            }
        });

        // Отправка на сервер
        $('#btnSave').on("click", function(event) {
            event.preventDefault();
            $('#createSave').modal('show');
            var trueName =  $(this).html();
            $(this).attr('disabled', true).html('Отправка запроса');
            var dataType = $("#selectType").val();
            // Формируем поля JSON
            var dataField = createDataField(0);
            var sumElem = countElem(dataField)+1;
            var dataBlock = createDataBlock(0, sumElem);
            var serverStack = JSON.stringify(createJSON(0,dataType,dataField,dataBlock));
            //console.log(serverStack);
            var serverAjax = $.ajax({
                type: "POST",
                url: 'rest/profile/docs',
                data: serverStack,
                contentType: 'application/json; charset=utf-8'
            });
            serverAjax.done(function(data) {
                $("#btnSluzh").attr('disabled', false).removeClass('btn-danger').addClass('btn-warning').addClass('d-none').html('Сгенерировать служебную записку');
                $('.loaderSuccess').addClass('d-none');
                $('.bodySuccess, .headerSuccess, .footerSuccess').removeClass('d-none').fadeIn(500);
                var projectRegNum = data.projectRegNum;
                $('#createSave #regNumTemplate').html(projectRegNum);
                $('#createSave').on('hidden.bs.modal', function() {
                    $('#selectType').val("");
                    $("#blockUp, #blockDown, #btnSave").addClass("d-none");
                    $("#btnSave").attr('disabled', false).html(trueName);
                });
            });
        });

        // Отправка на сервер файла служебки
        $('#btnSluzh').on("click", function(event) {
            event.preventDefault();
            var trueName =  $(this).html();
            $(this).attr('disabled', true).html('Отправка запроса');
            var dataType = $("#selectType").val();
            // Формируем поля JSON
            var dataField = createDataField(0);
            var sumElem = countElem(dataField)+1;
            var dataBlock = createDataBlock(0, sumElem);
            var templSluzh = createJSON(0,dataType,dataField,dataBlock);
            console.log(templSluzh);
            var repostSluzh = JSON.stringify(createJSON(0,dataType,dataField,dataBlock));
            console.log(repostSluzh);
            var serverAjax = $.ajax({
                type: "POST",
                url: 'rest/profile/docs/docx',
                data: repostSluzh,
                contentType: 'application/json; charset=utf-8'
            });
            serverAjax.done(function(data) {
                $("#btnSluzh").attr('disabled', false).removeClass('btn-danger').addClass('btn-warning').addClass('d-none').html(trueName);
                $('#btnLoad').removeClass('d-none').attr("href", data.fileUrl);
                $('#btnLoad').click(function() {
                    $("#btnSluzh").removeClass('d-none').removeClass('waves-effect');
                    $('#btnLoad').addClass('d-none');
                });
            });
            serverAjax.fail(function(data) {
                $("#btnSluzh").attr('disabled', false).removeClass('btn-warning').addClass('btn-danger').html('Ошибка! Отправить еще раз');
            });
        });
    });
</script>
<jsp:include page="fragments/footerBasement.jsp"/>