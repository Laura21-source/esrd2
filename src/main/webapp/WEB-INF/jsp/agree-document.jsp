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
                                <button type="button" class="btn btn-primary btn-sm float-right rounded" data-toggle="modal" data-target="#listAgree"><i class="fas fa-list-ul mr-2"></i>Список согласования</button>
                            </div>
                        </div>
                    </div>
                    <form class="registrationForm needs-validation" novalidate>
                        <div class="row">
                            <div class="col-lg-6 col-12">
                                <div class="row ml-1 mb-3">
                                    <div class="col-12">
                                        <div class="row">
                                            <div class="col-md-3 text-left mt-2">
                                                <div class="text-muted"><i class="fas fa-file-alt mr-2"></i> Вид документа<%--<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup>--%></div>
                                            </div>
                                            <div class="col-md-9">
                                                <select class="browser-default custom-select" name="selectType" id="selectType" required disabled>
                                                    <option value="" class="alert-primary">Выберите вид документа</option>
                                                </select>
                                                <div class="invalid-feedback">Поле обязательно для заполнения</div>
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
                                                <div class="btn btn-primary btn-sm pointer addGroup mr-3 rounded" title="Добавить вопрос"><i class="fas fa-plus mr-2"></i> Добавить</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-lg-6 col-12">
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
                                            <iframe class="embed-responsive-item pdfSRC" src=""></iframe>
                                        </div>
                                        <a href="" id="btnLoad" class="btn btn-default btn-md my-3 rounded pdfHREF" target="_blank" title="Скачать файл"><i class="fas fa-download mr-2"></i>Скачать</a>
                                        <div id="btnReformat" class="btn btn-mdb-color btn-md my-3 rounded pointer"><i class="fas fa-sync mr-2"></i>Переформировать</div>
                                        <a class="btn btn-light-blue btn-md my-3 pdfHREF" href="" target="_blank">Открыть в новом окне</a>
                                        <%--<div id="blockLoadPDF" class="d-none my-3">
                                            <div class="alert alert-secondary mx-auto text-uppercase mb-3">Загрузить подписанный документ</div>
                                            <div class="form-row mb-4 d-flex align-items-center justify-content-center">
                                                <div class="md-form file-field col">
                                                    <div class="btn btn-mdb-color btn-md float-left">
                                                        <span>Обзор</span>
                                                        <input title="Загрузить подписанный документ" type="file" id="inputPDF" name="inputPDF">
                                                    </div>
                                                    <div class="file-path-wrapper">
                                                        <input class="file-path validate" type="text" placeholder="Выберите файл">
                                                    </div>
                                                </div>
                                                <div class="col">
                                                    <a href="" id="btnLoadPDF" class="btn btn-mdb-color btn-md my-3 rounded" target="_blank" title="Загрузить файл"><i class="fas fa-upload mr-2"></i>Загрузить</a>
                                                </div>
                                            </div>
                                        </div>--%>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="row mt-3" id="commentText">
                            <div class="col-md-3">&nbsp;</div>
                            <div class="col-md-6 form-group text-left">
                                <label class="text-muted">Комментарий</label>
                                <textarea class="form-control" rows="3"></textarea>
                            </div>
                            <div class="col-md-3">&nbsp;</div>
                        </div>
                    </form>
                    <button type="submit" id="btnSave" class="btn btn-success mb-2 my-4 pt-3 rounded btnSave">Согласовать</button>
                    <button type="submit" id="btnReset" class="btn btn-danger mb-2 my-4 pt-3 rounded btnReset">Отменить согласование</button>
                    <button type="button" id="btnWordFile" class="btn btn-warning mb-2 my-4 pt-3 rounded btnSave">Сгенерировать служебную записку</button>
                    <a href="" type="button" id="btnLoadS" class="btn btn-primary mb-2 my-4 pt-3 rounded d-none btnSave"><i class="fas fa-download mr-2"></i>Скачать файл</a>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="fragments/footerNew.jsp"/>
<jsp:include page="fragments/modals/agreeDocumentModal.jsp"/>
<jsp:include page="fragments/modals/viewDocumentModal.jsp"/>
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
            // Меняем кнопку согласования на подписания
            if (data.finalStage === true) {
                $('#btnWordFile').addClass('d-none');
                // Меняем название документа
                if(data.regNum && data.regNum !== '') {
                    // Если документ подписан
                    newDate = formatDate(new Date(data.regDateTime), 0);
                    $(".documentName").html('Документ №' + data.regNum + ' от ' + newDate);
                    $('#btnSave, #addGroup, #btnReformat, #btnReset, #commentText').addClass('d-none');
                } else {
                    $(".documentName").html('Подписание документа №' + data.projectRegNum + ' от ' + newDate);
                    // Меняем кнопку согласования на подписания
                    $('#btnSave').html('Подписать');
                    $('#blockLoadPDF').removeClass('d-none');
                }
                // Меняем названия в модальном окне
                $('.heading').html('Подписание документа');
                $('.bodySuccess h6').html('Документ успешно подписан!');
            }
            // Имеет ли право пользователь подписывать документ
            if(data.canAgree === false) {
                $('#btnSave, #addGroup, #btnReformat, #btnReset, #commentText').addClass('d-none');
            }
            // Ссылки на документ PDF
            var documentPDF = data.UrlPDF;
            $('.pdfSRC').attr('src', documentPDF);
            $('.pdfHREF').attr('href', documentPDF);
            // Получение списка полей вида документа
            createOptions(docAllURL, "#selectType", "name", "id", data.docTypeId,"","","","","","","","","","");
            // Получение основных полей
            // Верхний блок полей
            getUpFields(docURL, id);
            // Нижний блок полей
            getDownFields(docURL, id, 0);
        });

        // Список согласования документа
        $.getJSON('rest/profile/docs/' + id + '/agreement/list/', function(data) {
            /*$('#listAgree .modal-body').append('<div class="row mb-3 d-flex align-items-center text-center font-weight-bold"><div class="col-1">Статус</div><div class="col-4">Пользователь</div><div class="col-4">Комментарий</div><div class="col-3">Редактирование</div></div>');*/
            for(var i in data) {
                var row = data[i];
                var returnButton = '';
                var comment = '';
                if(row.comment) {comment = row.comment;}
                if(row.currentUser === true) {
                    var currentUser = '<i class="fas fa-user-clock text-warning"></i>';
                } else {
                    currentUser = '<i class="fas fa-ellipsis-h text-muted"></i>';
                }
                if(row.decisionType && row.decisionType === 'ACCEPTED') {
                    currentUser = '<i class="fas fa-check text-success"></i>';
                    returnButton = '<button class="btn btn-danger btn-sm rounded btnReturn" data-user="'+row.name+'"><i class="fas fa-undo-alt mr-2"></i>Вернуть</button>';
                }
                var firstName = row.firstName.substr(0,1)+'.';
                var patronym = row.patronym.substr(0,1)+'.';
                $('#listAgree .modal-body').append('<div class="row mb-3 d-flex align-items-center"><div class="col-1 text-center">'+currentUser+'</div><div class="col-4">'+row.lastName+' '+firstName+' '+patronym+'<br><small class="text-muted">'+row.position+'</small></div><div class="col-4"><small>'+comment+'</small></div><div class="col-3">'+returnButton+'</div></div>');
            }
        });

        // Отправка согласования на сервер
        $('#btnSave').on("click", function(event) {
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
                $('#btnSuccess').modal('show');
                var trueName = $(this).html();
                $(this).attr('disabled', true).html('Отправка запроса');
                var dataType = $("#selectType").val();
                // Формируем поля JSON
                var dataField = createDataField(id);
                var sumElem = countElem(dataField) + 1;
                var dataBlock = createDataBlock(id, sumElem);
                //console.log(JSON.stringify(dataBlock));
                var serverStack = JSON.stringify(createJSON(id, dataType, dataField, dataBlock));
                //console.log(serverStack);
                var serverAjax = $.ajax({
                    type: "POST",
                    url: 'rest/profile/docs',
                    data: serverStack,
                    contentType: 'application/json; charset=utf-8'
                });
                serverAjax.done(function (data) {
                    $('.loaderSuccess').addClass('d-none');
                    $('.bodySuccess, .headerSuccess, .footerSuccess').removeClass('d-none').fadeIn(500);
                    var regNum = data.regNum;
                    if (regNum) {
                        $('#btnSuccess #regName').html('Регистрационный номер:');
                        $('#btnSuccess #regNum').html(regNum);
                        $('#btnSuccess').on('hidden.bs.modal', function () {
                            $("#btnSave").attr('disabled', false).html(trueName);
                            window.location.href = "agree-document?id=" + data.id;
                        });
                    } else {
                        $('#btnSuccess').on('hidden.bs.modal', function () {
                            $("#btnSave").attr('disabled', false).html(trueName);
                            window.location.href = "agreement";
                        });
                    }
                });
                serverAjax.fail(function () {
                    toastr["error"]("Ошибка приложения!");
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
                    $('#btnLoadS').removeClass('d-none').attr("href", data.fileUrl);
                    $('#btnLoadS').click(function () {
                        $("#btnWordFile").removeClass('d-none').removeClass('waves-effect');
                        $('#btnLoadS').addClass('d-none');
                    });
                });
                serverAjax.fail(function () {
                    toastr["error"]("Ошибка сохранения!");
                    $("#btnWordFile").attr('disabled', false).removeClass('btn-warning').addClass('btn-danger').html('Ошибка! Отправить еще раз');
                });
            }
        });

        // Отправка отмены согласования на сервер
        $('#btnReset').on("click", function(event) {
            event.preventDefault();
            $('#btnCancel').modal('show');
            var trueName =  $(this).html();
            $(this).attr('disabled', true).html('Отправка запроса');
            var dataType = $("#selectType").val();
            // Формируем поля JSON
            var dataField = createDataField(id);
            var sumElem = countElem(dataField)+1;
            var dataBlock = createDataBlock(id, sumElem);
            //console.log(JSON.stringify(dataBlock));
            var serverStack = JSON.stringify(createJSON(id,dataType,dataField,dataBlock));
            //console.log(serverStack);
            var comment = $('#commentText textarea').val();
            var serverAjax = $.ajax({
                type: "POST",
                url: 'rest/profile/docs/rejectDocAgreement/'+id+'?comment='+comment,
                data: serverStack,
                contentType: 'application/json; charset=utf-8'
            });
            serverAjax.done(function() {
                $('.loaderCancel').addClass('d-none');
                $('.bodyCancel, .headerCancel, .footerCancel').removeClass('d-none').fadeIn(500);
                $('#btnCancel').on('hidden.bs.modal', function() {
                    $("#btnReset").attr('disabled', false).html(trueName);
                    window.location.href="agreement";
                });
            });
            serverAjax.fail(function () {
                toastr["error"]("Ошибка приложения!");
            });
        });

        // Отправка на сервер файла PDF
        $('#btnReformat').on("click", function(event) {
            event.preventDefault();
            var trueName =  $(this).html();
            $(this).html('Отправка запроса').attr('disabled', true);
            $(".pdfSRC").addClass("d-none");
            $(".bigLoader").removeClass("d-none").fadeIn(500);
            var dataType = $("#selectType").val();
            // Формируем поля JSON
            var dataField = createDataField(id);
            var sumElem = countElem(dataField)+1;
            var dataBlock = createDataBlock(id, sumElem);
            var tempPDF = createJSON(id,dataType,dataField,dataBlock);
            //console.log(tempPDF);
            var reformatPDF = JSON.stringify(createJSON(id,dataType,dataField,dataBlock));
            //console.log(reformatPDF);
            var serverAjax = $.ajax({
                type: "POST",
                url: 'rest/profile/docs/pdf',
                data: reformatPDF,
                contentType: 'application/json; charset=utf-8'
            });
            serverAjax.done(function(data) {
                $(".bigLoader").addClass("d-none").fadeOut(1000);
                $("#btnReformat").html(trueName).attr('disabled', false).removeClass('waves-effect');
                $(".pdfSRC").removeClass("d-none").attr("src", data.fileUrl);
                $(".pdfHREF").attr("href", data.fileUrl);
            });
            serverAjax.fail(function () {
                toastr["error"]("Ошибка отправки файла!");
            });
        });
    });
</script>
<jsp:include page="fragments/footerBasement.jsp"/>