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
                        <div class="row">
                            <div class="col-md-6">
                                <div class="alert alert-primary mx-auto text-uppercase">Список согласования</div>
                                <div class="card">
                                    <div class="card-body">
                                        <div class="row text-center font-weight-bold blue-grey lighten-5 d-flex align-items-center justify-content-center py-2 fontSmall">
                                            <div class="col-md-1">№</div>
                                            <div class="col-md-1"></div>
                                            <div class="col-md-8">Согласователь</div>
                                            <div class="col-md-2">Удалить</div>
                                        </div>
                                        <div class="row" id="userListBlock">
                                            <div class="col-12 mt-2" id="blockUser1">
                                                <div class="row d-flex align-items-center justify-content-center fontSmall" data-user="1">
                                                    <div class="col-md-1">1.</div>
                                                    <div class="col-md-1"><i class="fas fa-user"></i></div>
                                                    <div class="col-md-8 selectUser select-outline">
                                                        <select class="mdb-select md-form md-outline colorful-select dropdown-primary userList" data-spisok="1" id="userList1" searchable=' Поиск' name="userList[]" required>
                                                            <option value="" selected>Выбрать</option>
                                                        </select>
                                                        <div class="fontSmall text-left" id="userListPost1"></div>
                                                    </div>
                                                    <div class="col-md-2"></div>
                                                </div>
                                            </div>
                                        </div>
                                        <hr class="my-1">
                                        <div class="row">
                                            <div class="col-12 text-right">
                                                <div class="btn btn-primary btn-sm addUser rounded px-3" data-toggle="tooltip" title="Добавить согласователя"><i class="fas fa-plus mr-2"></i> Добавить</div>
                                            </div>
                                        </div>
                                        <div class="invalid-tooltip">Выберите согласователя</div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="container-fluid mx-2">
                                    <div class="sticky-content">
                                        <div class="alert alert-primary mx-auto text-uppercase">Готовый документ</div>
                                        <div class="embed-responsive embed-responsive-1by1 z-depth-1-half mb-3 d-flex align-items-center justify-content-center">
                                            <!--Big blue Loader-->
                                            <div class="preloader-wrapper active bigLoader d-none">
                                                <div class="spinner-layer spinner-blue-only">
                                                    <div class="circle-clipper left">
                                                        <div class="circle"></div>
                                                    </div>
                                                    <div class="gap-patch">
                                                        <div class="circle"></div>
                                                    </div>
                                                    <div class="circle-clipper right">
                                                        <div class="circle"></div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="pdfSign"><i class="far fa-file-pdf text-danger fa-10x"></i></div>
                                            <iframe class="embed-responsive-item pdfSRC" src=""></iframe>
                                        </div>
                                        <!--btnLoad--><a href="" id="btnSavePdf" class="btn btn-default btn-sm my-3 rounded pdfHREF px-3" target="_blank" data-toggle="tooltip" title="Скачать файл"><i class="fas fa-download mr-2"></i>Скачать</a>
                                        <div id="btnReformat" class="btn btn-mdb-color btn-sm my-3 rounded px-3"><i class="fas fa-sync mr-2"></i>Переформировать</div>
                                        <a class="btn btn-light-blue btn-sm my-3 pdfHREF px-3" href="" target="_blank">Открыть в новом окне</a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="alert alert-secondary text-center mt-5 mb-3">
                            <h5 class="mt-2">Поля формирования документа</h5>
                        </div>
                        <div class="card">
                            <div class="card-body">
                                <div class="row ml-1 mb-3 d-flex align-items-center">
                                    <div class="col-md-3 text-left mt-2">
                                        <span class="text-muted"><i class="fas fa-file-alt mr-2"></i> Вид документа</span>
                                    </div>
                                    <div class="col-md-9 select-outline">
                                        <select class="mdb-select md-form md-outline validate colorful-select dropdown-primary" name="selectType" id="selectType" required>
                                            <option value="" selected>Выберите вид документа</option>
                                        </select>
                                        <div class="invalid-tooltip">Выберите тип документа</div>
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
                                                <div class="btn btn-primary btn-sm pointer addGroup mr-3 rounded" data-toggle="tooltip" title="Добавить блок"><i class="fas fa-plus mr-2"></i> Добавить</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <button type="submit" id="btnSave" class="btn btn-success mb-2 my-4 pt-3 rounded d-none btnSave">Отправить на согласование</button>
                                <button type="button" id="btnWordFile" class="btn btn-warning mb-2 my-4 pt-3 rounded d-none btnSave">Сгенерировать служебную записку</button>
                                <a href="" type="button" id="btnLoad" class="btn btn-primary mb-2 my-4 pt-3 rounded d-none btnSave"><i class="fas fa-download mr-2"></i>Скачать файл</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="fragments/footerNew.jsp"/>
<jsp:include page="fragments/modals/newDocumentModal.jsp"/>
<jsp:include page="fragments/modals/viewDocumentModal.jsp"/>
<jsp:include page="fragments/footerScript.jsp"/>
<script>
    $(function() {
        // Список юзеров
        createOptions ('rest/profile/users/', '#userList1', '', 'id', '', 'usersList');
        // Добавление должности при изменении пользователя
        $(document).on("change", ".userList", function() {
            var userId = $(this).val();
            var link = $(this).attr('data-spisok');
            createUserList('rest/profile/users/'+userId, '#userListPost'+link);
        });

        // Список полей вида документов
        createOptions('rest/profile/doctypes/', '#selectType', 'name', 'id', '', '');
        $('#selectType.mdb-select').materialSelect();

        // Выбор типа документа
        $("#selectType").change(function() {
            // Убрать с экрана все предыдущие поля
            $('#blockUp, #newBlockGroup').empty();
            $('.blockGroup').remove();
            var asd = $("#selectType").val();
            if(asd && asd !== '') {
                // Добавить блоки отсюда в файл функций -getFieldsDocument
                $("#blockUp, #blockDown, #btnSave, #btnWordFile").removeClass("d-none");
                // Верхний блок полей
                getUpFields("rest/profile/doctypes/" + asd + "/fields", 0);
                // Нижний блок полей
                getDownFields("rest/profile/doctypes/" + asd + "/fields", 0, '');
            } else {
                $("#blockUp, #blockDown, #btnSave, #btnWordFile").addClass("d-none");
            }
        });

        // Сохранение - Отправка на сервер
        $('#btnSave').on("click", function(event) {
            event.preventDefault();
            var forms = $('.registrationForm');
            var formsValue = $('.registrationForm input,.registrationForm textarea,.registrationForm select').filter('[required]');
            var agreeFormsValue = $('.registrationForm #userListBlock select');
            $(forms).addClass('was-validated');
            event.preventDefault();
            var checkField = checkValidation(formsValue);
            if(checkField === false) {
                toastr["error"]("Заполните обязательные поля!");
                $(forms).addClass('was-validated');
                event.stopPropagation();
            } else {
                $('#createSave').modal('show');
                var trueName =  $(this).html();
                $(this).attr('disabled', true).html('Отправка запроса');
                var dataType = $("#selectType").val();
                // Формируем поля JSON
                var dataField = createDataField(0);
                var sumElem = countElem(dataField)+1;
                var dataBlock = createDataBlock(0, sumElem);
                var serverStack = JSON.stringify(createJSON(0,dataType,dataField,dataBlock));
                var agreeListStack = JSON.stringify(createAgreeList(agreeFormsValue));
                //console.log(serverStack);
                var serverAjax = $.ajax({
                    type: "POST",
                    url: 'rest/profile/docs',
                    data: serverStack,
                    contentType: 'application/json; charset=utf-8'
                });
                // Успешное сохранение документа
                serverAjax.done(function(data) {
                    $("#btnWordFile").attr('disabled', false).removeClass('btn-danger').addClass('btn-warning').addClass('d-none').html('Сгенерировать служебную записку');
                    $('.loaderSuccess').addClass('d-none');
                    $('.bodySuccess, .headerSuccess, .footerSuccess').removeClass('d-none').fadeIn(500);
                    $('.registrationForm').removeClass('was-validated');
                    var projectRegNum = data.projectRegNum;
                    $('#createSave #regNumTemplate').html(projectRegNum);
                    $('#createSave').on('hidden.bs.modal', function() {
                        $('#selectType').val("");
                        $("#blockUp, #blockDown, #btnSave").addClass("d-none");
                        $("#btnSave").attr('disabled', false).html(trueName);
                    });
                    // Сохранение списка согласования
                    var serverAgreeList = $.ajax({
                        type: "POST",
                        url: 'rest/profile/docs/'+data.id+'/agreement/list',
                        data: agreeListStack,
                        contentType: 'application/json; charset=utf-8',
                    });
                    serverAgreeList.done(function() {
                        toastr["success"]("Успешно сохранён список согласования!");
                    });
                    serverAgreeList.fail(function () {
                        toastr["error"]("Ошибка сохранения списка согласования!");
                    });
                    // Сохранение файла служебки
                    var serverWord = $.ajax({
                        type: "POST",
                        url: 'rest/profile/docs/docx',
                        data: serverStack,
                        contentType: 'application/json; charset=utf-8',
                    });
                    serverWord.done(function(data) {
                        $('#modalLoad').attr("href", data.fileUrl);
                    });
                    serverWord.fail(function () {
                        toastr["error"]("Ошибка сохранения файла служебки!");
                    });
                });
                // Ошибка сохранения документа
                serverAjax.fail(function () {
                    toastr["error"]("Ошибка сохранения документа!");
                });
            }
        });

        // Отправка на сервер файла служебки
        $('#btnWordFile').on("click", function(event) {
            event.preventDefault();
            var forms = $('.registrationForm');
            var formsValue = $('.registrationForm input,.registrationForm textarea,.registrationForm select').filter('[required]');
            event.preventDefault();
            var checkField = checkValidation(formsValue);
            if(checkField === false) {
                toastr["error"]("Заполните обязательные поля!");
                $(forms).addClass('was-validated');
                event.stopPropagation();
            } else {
                var trueName = $(this).html();
                $(this).attr('disabled', true).html('Отправка запроса');
                var dataType = $("#selectType").val();
                // Формируем поля JSON
                var dataField = createDataField(0);
                var sumElem = countElem(dataField) + 1;
                var dataBlock = createDataBlock(0, sumElem);
                var repostWordFile = JSON.stringify(createJSON(0, dataType, dataField, dataBlock));
                //console.log(repostWordFile);
                var serverAjax = $.ajax({
                    type: "POST",
                    url: 'rest/profile/docs/docx',
                    data: repostWordFile,
                    contentType: 'application/json; charset=utf-8'
                });
                serverAjax.done(function (data) {
                    $("#btnWordFile").attr('disabled', false).removeClass('btn-danger').addClass('btn-warning').addClass('d-none').html(trueName);
                    $('#btnLoad').removeClass('d-none').attr("href", data.fileUrl);
                    $('#btnLoad').click(function () {
                        $("#btnWordFile").removeClass('d-none').removeClass('waves-effect');
                        $('#btnLoad').addClass('d-none');
                    });
                });
                serverAjax.fail(function () {
                    toastr["error"]("Ошибка сохранения!");
                    $("#btnWordFile").attr('disabled', false).removeClass('btn-warning').addClass('btn-danger').html('Ошибка! Отправить еще раз');
                });
            }
        });
    });
</script>
<jsp:include page="fragments/footerBasement.jsp"/>