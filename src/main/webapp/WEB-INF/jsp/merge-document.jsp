<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="fragments/headerNew.jsp"/>

<script>
    $(window).on('load', function() {
        $('#mdb-preloader').addClass('loaded');
    });
</script>
<main>
    <div id="mdb-preloader" class="flex-center">
        <h2 class="text-dark mr-5">Подождите, идёт загрузка данных</h2>
        <div class="preloader-wrapper active">
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
    </div>
    <div class="container-fluid text-center mb-4">
        <div class="card mx-auto w-100">
            <div class="card-body">
                <div class="container-fluid">
                    <div class="mergeBlock"></div>
                    <div class="alert alert-secondary text-center mb-3 blockDocumentNew d-none">
                        <div class="btn btn-primary btn-sm rounded px-3 btnCloseNew"><i class="fas fa-minus mr-2"></i>Свернуть</div>
                        <h5 class="mt-2">Формирование нового документа</h5>
                    </div>
                    <form class="newDocumentForm needs-validation blockDocumentNew d-none" novalidate>
                        <div class="card pb-5">
                            <div class="card-body pb-5">
                                <div class="row ml-1 mb-1 d-flex align-items-center">
                                    <div class="col-md-2 text-left mt-2">
                                        <div class="text-muted"><i class="fas fa-file-alt mr-2"></i> Вид документа<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup></div>
                                    </div>
                                    <div class="col-md-10">
                                        <select data-placeholder="Выберите вид документа" class="chosen-select" name="selectTypeNew" id="selectTypeNew" required>
                                            <option value="">Выберите из справочника</option>
                                        </select>
                                        <div class="invalid-tooltip">Выберите тип документа</div>
                                    </div>
                                </div>
                                <div id="blockFieldsNew" class="d-none">
                                    <div class="whomListNew">
                                        <div class="row ml-1 mb-2 d-flex align-items-center">
                                            <div class="col-2 text-left mt-2">
                                                <span class="text-muted"><i class="fas fa-sitemap mr-2"></i>Адресат<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup></span>
                                            </div>
                                            <div class="col-10">
                                                <select data-placeholder="Выберите из справочника" multiple class="chosen-select" id="whomListNew" required>
                                                    <option value="">Выберите из справочника</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-md-6">
                                            <div class="alert alert-primary mx-auto text-uppercase">Список согласования<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup></div>
                                            <div class="card">
                                                <div class="card-body">
                                                    <div class="row text-center font-weight-bold blue-grey lighten-5 d-flex align-items-center justify-content-center py-2 fontSmall">
                                                        <div class="col-md-1"><small>№</small></div>
                                                        <div class="col-md-1"></div>
                                                        <div class="col-md-8"><small>Согласователь</small></div>
                                                        <div class="col-md-2"><%--Удалить--%></div>
                                                    </div>
                                                    <div class="row" id="userListBlockNew">
                                                        <div class="col-12 mt-2 blockUserNew" id="blockUserNew1">
                                                            <div class="row d-flex align-items-center justify-content-center fontSmall" data-user="1">
                                                                <div class="col-md-1">1</div>
                                                                <div class="col-md-1"><i class="fas fa-user"></i></div>
                                                                <div class="col-md-8 selectUser">
                                                                    <select data-placeholder="Выберите из справочника" class="chosen-select userListNew"  data-spisok="1" id="userListNew1" name="userListNew[]" required>
                                                                        <option value="" selected>Выбрать</option>
                                                                    </select>
                                                                    <div class="fontSmall text-left" id="userListPostNew1"></div>
                                                                </div>
                                                                <div class="col-md-2"></div>
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <hr class="my-1">
                                                    <div class="row">
                                                        <div class="col-12 text-right">
                                                            <div class="btn btn-primary btn-sm addUserNew rounded px-3" title="Добавить согласователя"><i class="fas fa-plus mr-2"></i> Добавить</div>
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
                                                        <div class="preloader-wrapper active bigLoaderNew d-none">
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
                                                        <div class="pdfSignNew"><i class="far fa-file-pdf text-danger fa-10x"></i></div>
                                                        <iframe class="embed-responsive-item pdfSRCNew" src=""></iframe>
                                                    </div>
                                                    <a href="" id="btnSavePdfNew" class="btn btn-default btn-sm my-3 rounded pdfHREFNew px-3" target="_blank" data-toggle="tooltip" title="Скачать файл"><i class="fas fa-download mr-2"></i>Скачать</a>
                                                    <div id="btnReformatNew" class="btn btn-mdb-color btn-sm my-3 rounded px-3"><i class="fas fa-sync mr-2"></i>Переформировать</div>
                                                    <a class="btn btn-light-blue btn-sm my-3 pdfHREFNew px-3" href="" target="_blank">Открыть в новом окне</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="alert alert-secondary text-center mt-5 mb-3">
                                        <h5 class="mt-2">Поля формирования документа</h5>
                                        <select id="arrayBlockNew" class="d-none"></select>
                                    </div>
                                </div>
                                <div id="blockBlockNew"></div>
                                <button type="submit" id="btnSaveNew" class="btn btn-success mb-2 my-4 pt-3 rounded d-none btnSave">Отправить на согласование</button>
                                <%--<button type="button" id="btnWordFileNew" class="btn btn-warning mb-2 my-4 pt-3 rounded d-none btnSave">Сгенерировать служебную записку</button>--%>
                                <a href="" type="button" id="btnLoadNew" class="btn btn-primary mb-2 my-4 pt-3 rounded d-none btnSave"><i class="fas fa-download mr-2"></i>Скачать файл</a>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="fragments/footerNew.jsp"/>
<jsp:include page="fragments/modals/agreeDocumentModal.jsp"/>
<jsp:include page="fragments/modals/viewDocumentModal.jsp"/>
<jsp:include page="fragments/modals/newDocumentModal.jsp"/>
<jsp:include page="fragments/footerScript.jsp"/>
<script>

    $(function() {
        // Список всех документов
        var docAllURL = "rest/profile/doctypes/";
        // Получаем информацию из id строки
        var id = getId();

        // Подключение стека полей
        function getValuesDocument (docURL, docPole) {
            $.getJSON(docURL, function(data) {
                var finalVersion = '';
                // Скрываем поле Адресат если finalDoc = true
                if(data.finalDoc === true) {$(docPole+' .whomList').addClass('d-none');}
                // Дата документа
                var newDate = '';
                if(data.projectRegDateTime) {newDate = formatDate(new Date(data.projectRegDateTime), 0);}
                $(docPole+" .documentName").html('Согласование документа №' + data.projectRegNum + ' от ' + newDate);
                // Меняем кнопку согласования на подписания
                if (data.finalStage === true) {
                    $(docPole+" .documentName").html('Подписание документа №' + data.projectRegNum + ' от ' + newDate);
                    // Меняем кнопку согласования на подписания
                    $('#btnSave').html('Подписать');
                    $('#blockLoadPDF').removeClass('d-none');
                    // Меняем названия в модальном окне
                    $('.headerAddTemplate .heading').html('Внимание! Подписание документа!');
                    $('.headerSuccess .heading').html('Подписание документа');
                    $('.bodySuccess h6').html('Документ успешно подписан!');
                }
                if(data.regNum && data.regNum !== '') {
                    // Если документ подписан
                    newDate = formatDate(new Date(data.regDateTime), 0);
                    $(docPole+" .documentName").html('Документ №' + data.regNum + ' от ' + newDate);
                    $('#btnSave, #addGroup, #btnReformat, #btnReset, #commentText').addClass('d-none');
                }
                // Имеет ли право пользователь подписывать документ
                if(data.canAgree === false) {
                    finalVersion = 1;
                    $('#btnSave, #addGroup, #btnReformat, #btnReset, #commentText').addClass('d-none');
                }
                // Если документ уже подписан
                if(data.docStatus === 'IN_WORK') {
                    if(data.canWork === true) {
                        $('.blockDocumentNew').removeClass('d-none');
                        $('.blockDocument, #btnWordFile').addClass('d-none');
                        $('.docName').html('Сведения о документе');
                    }
                    /*if(data.canDistribute == true) {
                        $(docPole+ '.performerBlock').removeClass('d-none');
                        $(docPole+ '#performerList').prop('required',true);
                        // Добавим опций
                        if (data.executorUsersIds.length == 0) {
                            createOptions ('rest/profile/users/', docPole+' #performerList', '', 'id', '', 'usersList');
                        }
                        for(var i in data.executorUsersIds) {
                            createOptions ('rest/profile/users/', docPole+' #performerList', '', 'id', data.executorUsersIds[i], 'usersList');
                        }
                        // Добавление исполнителя
                        $(document).on("change", docPole+" #performerList", function(evt, params) {
                            if (params.selected) {
                                var userId = parseInt(params.selected);
                                var serverAjax = $.ajax({
                                    type: "POST",
                                    url: 'rest/profile/docs/'+id+'/addExecutorUser/'+userId,
                                    contentType: 'application/json; charset=utf-8'
                                });
                                serverAjax.done(function() {
                                    toastr["success"]("Исполнителю назначен на документ!");
                                });
                                serverAjax.fail(function() {
                                    //toastr["error"]("Не удалось назначить исполнителя!");
                                    var errorInfo = JSON.parse(serverAjax.responseText);
                                    toastr["error"]("Не удалось назначить исполнителя! " + errorInfo.details);
                                    //$("#performerList").empty();
                                    $(docPole+" #performerList option[value='"+userId+"']").prop("selected", false);
                                    $(docPole+" #performerList").trigger("chosen:updated");
                                });
                            }
                            if (params.deselected) {
                                var userId = parseInt(params.deselected);
                                var serverAjax = $.ajax({
                                    type: "DELETE",
                                    url: 'rest/profile/docs/'+id+'/removeExecutorUser/'+userId,
                                    contentType: 'application/json; charset=utf-8'
                                });
                                serverAjax.done(function() {
                                    toastr["success"]("Исполнитель снят с документа!");
                                });
                                serverAjax.fail(function () {
                                    toastr["error"]("Исполнитель не снят с документа!");
                                    //$("#performerList").empty();
                                    $(docPole+" #performerList option[value='"+userId+"']").prop("selected", false);
                                    $(docPole+" #performerList").trigger("chosen:updated");
                                });
                            }
                        });
                    }*/
                }
                // Список согласования
                $(docPole+' #userListBlock,'+docPole+' .userList').remove();
                $(docPole+' .disableUserList').removeClass('d-none');
                // Инициатор согласования
                if(data.initialUser) {
                    $(docPole+' #initialUser').append('' +
                        '<div class="mb-3 d-flex align-items-center">' +
                        '   <div class="text-muted mr-2">' +
                        '       <i class="fas fa-user text-success mr-2" title="Инициатор согласования"></i>Инициатор согласования:' +
                        '   </div>' +
                        '   <div>'+data.initialUser.fullName+'</div>' +
                        '</div>');
                }
                createUserListDisabled('rest/profile/docs/'+data.id+'/agreement/list', finalVersion, docPole);

                // Ссылки на документ PDF
                var documentPDF = data.UrlPDF;
                $(docPole+' .pdfSRC').attr('src', documentPDF);
                $(docPole+' .pdfHREF').attr('href', documentPDF);
                // Получение списка полей вида документа
                $(docPole+' #selectType').chosen({
                    width: "100%",
                    no_results_text: "Ничего не найдено!"
                });
                createOptions(docAllURL, docPole+' #selectType', 'name', 'id', data.docTypeId,'');
                // Список кому
                createWhomListDisabled (data.executorDepartmentsIds);
                // Вывод блоков полей
                getNewFields(docURL, data.id, '', '', '', '', '', docPole);
                setTimeout(function() {
                    $('#mdb-preloader').removeClass('loaded').hide();
                }, 500);
            });
        }

        // Проверяем - один документ или несколько
        if(id.indexOf(',') > 0) {
            $('.mergeBlock').append(
                '<div class="accordion md-accordion card mb-3" id="accordionEx" role="tablist" aria-multiselectable="true"></div>'
            );
            // Поля документов по
            var documentsId = id.split( "," );
            $.each(documentsId, function (index, value) {
                $('.accordion').append(
                    '<div class="card" id="document'+index+'">' +
                    '   <div class="card-header" role="tab" id="heading'+index+'">' +
                    '       <a class="collapsed" data-toggle="collapse" data-parent="#accordionEx" href="#collapse'+index+'" aria-expanded="false" aria-controls="collapse'+index+'">' +
                    '           <div class="alert alert-primary text-center mb-3">' +
                    '               <h4 class="mt-2"><span class="documentName"></span><i class="fas fa-angle-down rotate-icon"></i></h4>' +
                    '           </div>' +
                    '       </a>' +
                    '   </div>' +
                    '   <div id="collapse'+index+'" class="collapse" role="tabpanel" aria-labelledby="heading'+index+'" data-parent="#accordionEx">' +
                    '       <div class="card-body">' +
                    '           <form class="registrationForm needs-validation" novalidate>' +
                    '               <div class="row">' +
                    '                   <div class="col-md-6">' +
                    '                       <div class="alert alert-primary mx-auto text-uppercase">Список согласования</div>' +
                    '                       <div class="card z-depth-1-half rounded">' +
                    '                           <div class="card-body">' +
                    '                               <div id="initialUser"></div>' +
                    '                               <div class="row text-center font-weight-bold blue-grey lighten-5 d-flex align-items-center justify-content-center py-2 userList">' +
                    '                                   <div class="col-md-1"><small>№</small></div>' +
                    '                                   <div class="col-md-1"></div>' +
                    '                                   <div class="col-md-8"><small>Согласователь</small></div>' +
                    '                                   <div class="col-md-2"><%--Удалить--%></div>' +
                    '                               </div>' +
                    '                               <div class="row text-center mb-3 font-weight-bold blue-grey lighten-5 d-flex align-items-center justify-content-center py-2 d-none disableUserList">' +
                    '                                   <div class="col-md-1">№</div>' +
                    '                                   <div class="col-md-1"></div>' +
                    '                                   <div class="col-md-4"><small>Согласователь</small></div>' +
                    '                                   <div class="col-md-3"><small>Комментарий</small></div>' +
                    '                                   <div class="col-md-3"><small>Дата/Время</small></div>' +
                    '                               </div>' +
                    '                               <div id="userListBlockDiv"></div>' +
                    '                               <div class="row" id="userListBlock">' +
                    '                                   <div class="col-12 mt-2 blockUser" id="blockUser1">' +
                    '                                       <div class="row d-flex align-items-center justify-content-center fontSmall" data-user="1">' +
                    '                                           <div class="col-md-1">1</div>' +
                    '                                           <div class="col-md-1"><i class="fas fa-user"></i></div>' +
                    '                                           <div class="col-md-8 selectUser">' +
                    '                                               <select data-placeholder="Выберите из справочника" class="chosen-select userList" data-spisok="1" id="userList1" name="userList[]" required>' +
                    '                                                   <option value="" selected>Выбрать </option>' +
                    '                                               </select>' +
                    '                                               <div class="fontSmall text-left" id="userListPost1"></div>' +
                    '                                           </div>' +
                    '                                           <div class="col-md-2"></div>' +
                    '                                      </div>' +
                    '                                   </div>' +
                    '                                   <hr class="my-1">' +
                    '                                   <div class="col-12 text-right">' +
                    '                                       <div class="btn btn-primary btn-sm addUser rounded px-3" title="Добавить согласователя"><i class="fas fa-plus mr-2"></i> Добавить</div>' +
                    '                                   </div>' +
                    '                               </div>' +
                    '                               <div class="invalid-tooltip">Выберите согласователя</div>' +
                    '                           </div>' +
                    '                       </div>' +
                    '                   </div>' +
                    '                   <div class="col-md-6">' +
                    '                       <div class="container-fluid mx-2">' +
                    '                           <div class="sticky-content">' +
                    '                               <div class="alert alert-primary mx-auto text-uppercase">Готовый документ</div>' +
                    '                               <div class="embed-responsive embed-responsive-1by1 z-depth-1-half rounded mb-3 d-flex align-items-center justify-content-center">' +
                    '                                   <div class="preloader-wrapper active bigLoader d-none">' +
                    '                                       <div class="spinner-layer spinner-blue-only">' +
                    '                                           <div class="circle-clipper left">' +
                    '                                               <div class="circle"></div>' +
                    '                                           </div>' +
                    '                                           <div class="gap-patch">' +
                    '                                               <div class="circle"></div>' +
                    '                                           </div>' +
                    '                                           <div class="circle-clipper right">' +
                    '                                               <div class="circle"></div>' +
                    '                                           </div>' +
                    '                                       </div>' +
                    '                                   </div>' +
                    '                                   <div class="pdfSign"><i class="far fa-file-pdf text-danger fa-10x"></i></div>' +
                    '                                   <iframe class="embed-responsive-item pdfSRC" src=""></iframe>' +
                    '                               </div>' +
                    '                               <a href="" id="btnSavePdf" class="btn btn-default btn-sm my-3 rounded pdfHREF px-3" target="_blank" data-toggle="tooltip" title="Скачать файл"><i class="fas fa-download mr-2"></i>Скачать</a>' +
                    '                               <div id="btnReformat" class="btn btn-mdb-color btn-sm my-3 rounded px-3"><i class="fas fa-sync mr-2"></i>Переформировать</div>' +
                    '                               <a class="btn btn-light-blue btn-sm my-3 pdfHREF px-3" href="" target="_blank">Открыть в новом окне</a>' +
                    '                           </div>' +
                    '                       </div>' +
                    '                   </div>' +
                    '               </div>' +
                    '               <div class="alert alert-secondary text-center mt-5 mb-3">' +
                    '                   <div class="btn btn-primary btn-sm rounded px-3 btnView"><i class="fas fa-plus mr-2"></i>Развернуть</div>' +
                    '                   <h5 class="mt-2 docName">Поля формирования документа</h5>' +
                    '                   <select id="arrayBlock" class="d-none" multiple>' +
                    '                       <option value="256" selected></option>' +
                    '                   </select>' +
                    '               </div>' +
                    '               <div class="card blockDocument">' +
                    '                   <div class="card-body">' +
                    '                       <div class="row mb-3 d-flex align-items-center">' +
                    '                           <div class="col-md-3 text-left mt-2">' +
                    '                               <div class="text-muted"><i class="fas fa-file-alt mr-2"></i> Вид документа<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup></div>' +
                    '                           </div>' +
                    '                           <div class="col-md-9">' +
                    '                               <select data-placeholder="Выберите вид документа" class="chosen-select" name="selectType" id="selectType" required>' +
                    '                                   <option value="">Выберите из справочника</option>' +
                    '                               </select>' +
                    '                               <div class="invalid-tooltip">Выберите тип документа</div>' +
                    '                           </div>' +
                    '                       </div>' +
                    '                       <div id="blockUp"></div>' +
                    '                       <div id="blockBlock"></div>' +
                '                           <div class="row mt-3" id="commentText">' +
                    '                           <div class="col-md-3">&nbsp;</div>' +
                    '                           <div class="col-md-6 form-group text-left">' +
                    '                               <label class="text-muted">Комментарий</label>' +
                    '                               <textarea class="form-control" rows="3"></textarea>' +
                    '                           </div>' +
                    '                           <div class="col-md-3">&nbsp;</div>' +
                    '                       </div>' +
                    '                       <button type="submit" id="btnSave" class="btn btn-success mb-2 my-4 pt-3 rounded btnSave">Согласовать</button>' +
                    '                       <button type="submit" id="btnReset" class="btn btn-danger mb-2 my-4 pt-3 rounded btnReset">Отменить согласование</button>' +
                    '                       <a href="" type="button" id="btnLoad" class="btn btn-primary mb-2 my-4 pt-3 rounded d-none btnSave"><i class="fas fa-download mr-2"></i>Скачать файл</a>' +
                    '                   </div>' +
                    '               </div>' +
                    '           </form>' +
                    '       </div>' +
                    '   </div>' +
                    '</div>'
                );
                var docURL = "rest/profile/docs/" + value;
                getValuesDocument (docURL, '#document'+index);
            });
        } else {
            window.location.href = "agree-document?id=" + id;
        }

        // ФОРМИРОВАНИЕ НОВОГО ДОКУМЕНТА
        // Список полей вида документов
        createOptions('rest/profile/doctypes/', '#selectTypeNew', 'name', 'id', '', '');

        // Выбор типа документа
        $("#selectTypeNew").change(function() {
            $('#mdb-preloader').show();
            // Убрать с экрана все предыдущие поля
            $('#blockBlockNew, #newBlockGroupNew').empty();
            $('.blockGroupNew').remove();
            var asd = $("#selectTypeNew").val();
            if(asd && asd !== '') {
                // Показать или скрыть поле Адресат по параметру finalDoc
                getFinalStage('rest/profile/doctypes/'+ asd, '.whomListNew');
                // Добавить блоки отсюда в файл функций -getFieldsDocument
                $("#blockFieldsNew, #blockBlockNew, #btnSaveNew, #btnWordFileNew").removeClass("d-none");
                // Показываем поля документа
                var chiefDoc = documentsId[0];
                var optionDocs = '';
                $.each(documentsId, function (index, value) {
                    if(value !== chiefDoc) {
                        optionDocs = optionDocs+'&optionalDocIds='+value;
                    }
                });
                var mergeUrl = "rest/profile/docs/"+chiefDoc+"/fields/merged?targetDocTypeId="+asd+optionDocs;
                console.log(mergeUrl);
                getNewFields(mergeUrl, chiefDoc, '', '', 1, '' , '');
            } else {
                $("#blockFieldsNew, #blockBlockNew, #btnSaveNew, #btnWordFileNew").addClass("d-none");
            }
            setTimeout(function() {
                $('#mdb-preloader').removeClass('loaded').hide();
            }, 500);
        });

        // Список кому
        createOptions ('rest/profile/departments/getAllTopLevel', '#whomListNew', 'name', 'id', '', '');

        // Список согласования
        createOptions ('rest/profile/users/', '#userListNew1', '', 'id', '', 'usersList');
        // Добавление должности при изменении пользователя
        $(document).on("change", ".userListNew", function() {
            var userId = $(this).val();
            var link = $(this).attr('data-spisok');
            createUserList('rest/profile/users/'+userId, '#userListPostNew'+link);
        });

        // Сохранение - Отправка на сервер
        $('#btnSaveNew').on("click", function(event) {
            event.preventDefault();
            var formsValue = $('.newDocumentForm input,.newDocumentForm textarea,.newDocumentForm select').filter('[required]');
            var agreeFormsValue = $('.newDocumentForm #userListBlockNew select');
            event.preventDefault();
            var checkField = checkValidation(formsValue);
            if(checkField === false) {
                toastr["error"]("Заполните обязательные поля!");
                event.stopPropagation();
            } else {
                $('#createSave').modal('show');
                var dataType = $("#selectTypeNew").val();
                // Формируем поля JSON
                var dataField = createDataField(0, 1);
                var sumElem = countElem(dataField)+1;
                var dataBlock = createDataBlock(0, sumElem, 1);
                var agreeListStack = JSON.stringify(createAgreeList(agreeFormsValue));
                var serverStack = JSON.stringify(createJSON(id,dataType,dataField,dataBlock,1));
                //console.log(serverStack);
                var serverAjax = $.ajax({
                    type: "POST",
                    url: 'rest/profile/docs',
                    data: serverStack,
                    contentType: 'application/json; charset=utf-8'
                });
                // Успешное сохранение документа
                serverAjax.done(function(data) {
                    $("#btnWordFileNew").attr('disabled', false).removeClass('btn-danger').addClass('btn-warning').addClass('d-none').html('Сгенерировать служебную записку');
                    $('.loaderSuccess, .headerCreateTemplate').addClass('d-none');
                    $('.bodySuccess, .headerSuccess, .footerSuccess').removeClass('d-none').fadeIn(500);
                    var projectRegNum = data.projectRegNum;
                    $('#createSave #regNumTemplate').html(projectRegNum);
                    $('#createSave').on('hidden.bs.modal', function() {
                        $('select').val('');
                        $('#userListBlockNew .blockUserNew:not(:first)').remove();
                        $("#blockUpNew, #blockDownNew, #btnSaveNew, .pdfSRCNew").addClass("d-none");
                        //$("#btnSaveNew").attr('disabled', false).html(trueName);
                        window.location.href="all";
                    });
                    // Сохранение списка согласования
                    var serverAgreeList = $.ajax({
                        type: "POST",
                        url: 'rest/profile/docs/'+data.id+'/agreement/list',
                        data: agreeListStack,
                        contentType: 'application/json; charset=utf-8',
                    });
                    serverAgreeList.fail(function () {
                        toastr["error"]("Ошибка сохранения списка согласования!");
                    });
                });
                // Ошибка сохранения документа
                serverAjax.fail(function () {
                    toastr["error"]("Ошибка сохранения списка согласования!<br>Заполните обязательное поле - Адресат!");
                });
            }
        });

        $('#btnReformatNew').on("click", function(event) {
            event.preventDefault();
            var forms = $('.newDocumentForm');
            var formsValue = $('.newDocumentForm input,.newDocumentForm textarea,.newDocumentForm select').filter('[required]');
            event.preventDefault();
            var checkField = checkValidation(formsValue);
            if(checkField === false) {
                toastr["error"]("Заполните обязательные поля!");
                event.stopPropagation();
            } else {
                var trueName =  $(this).html();
                $(this).html('Отправка запроса').attr('disabled', true);
                $(".pdfSRCNew, .pdfSignNew").addClass("d-none");
                $(".bigLoaderNew").removeClass("d-none").fadeIn(500);
                var dataType = $("#selectTypeNew").val();
                // Формируем поля JSON
                var dataField = createDataField(0, 1);
                var sumElem = countElem(dataField)+1;
                var dataBlock = createDataBlock(0, sumElem, 1);
                var reformatPDF = JSON.stringify(createJSON(0,dataType,dataField,dataBlock,1));
                console.log(reformatPDF);
                var serverAjax = $.ajax({
                    type: "POST",
                    url: 'rest/profile/docs/pdf',
                    data: reformatPDF,
                    contentType: 'application/json; charset=utf-8'
                });
                serverAjax.done(function(data) {
                    $(".bigLoaderNew").addClass("d-none").fadeOut(1000);
                    $("#btnReformatNew").html(trueName).attr('disabled', false).removeClass('waves-effect');
                    $(".pdfSRCNew").removeClass("d-none").attr("src", data.fileUrl);
                    $(".pdfSignNew").removeClass("d-none");
                    $(".pdfHREFNew").attr("href", data.fileUrl);
                });
                serverAjax.fail(function () {
                    toastr["error"]("Ошибка формировки файла PDF!");
                });
            }
        });

    });
</script>
<jsp:include page="fragments/footerBasement.jsp"/>