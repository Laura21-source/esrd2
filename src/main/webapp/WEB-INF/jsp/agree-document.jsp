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
                                <h6 class="mt-2">Согласование документа №согл-1/19 от 08.08.2019</h6>
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
                                                <select class="browser-default custom-select" name="selectTypeAgree" id="selectTypeAgree">
                                                    <option value="" class="alert-primary">Выберите вид документа</option>
                                                    <option value="1" selected>Повестка заседания Правления</option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row ml-1 mb-3">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-4 text-left mt-2">
                                                <span for="inputDate" class="text-muted"><i class="fas fa-calendar-alt mr-2"></i> Дата заседания</span>
                                            </div>
                                            <div class="col-md-8">
                                                <input title="Выберите дату" type="date" id="inputDate" name="inputDate" class="form-control" value="2019-08-08">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row ml-1 mb-3">
                                    <div class="col-md-12">
                                        <div class="row">
                                            <div class="col-md-4 text-left mt-2">
                                                <span for="inputTime" class="text-muted"><i class="fas fa-clock mr-2"></i> Время  заседания</span>
                                            </div>
                                            <div class="col-md-8">
                                                <input title="Выберите время" type="time" id="inputTime" name="inputTime" class="form-control" value="11:45">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div>&nbsp;</div>
                                <div class="row card mb-3" id="blockQuestion" req="true">
                                    <div class="col-12">
                                        <div class="card-body">
                                            <div class="row">
                                                <div class="col-md-9 text-left">
                                                    <h5 id="nameQuestion">Вопрос 1</h5>
                                                </div>
                                                <div class="col-md-3 text-right">
                                                    <div id="delQuestion" class="btn btn-danger btn-sm pointer delQuestion d-none rounded" title="Удалить вопрос"><i class="fas fa-trash"></i></div>
                                                </div>
                                            </div>
                                            <hr>
                                            <div class="row">
                                                <div class="col-md-4 text-left mt-3">
                                                    <span class="text-muted"><i class="fas fa-globe mr-2"></i> Тематика</span>
                                                </div>
                                                <div class="col-md-8 mt-3">
                                                    <select class="browser-default custom-select" name="selectTheme[]" id="selectTheme">
                                                        <option value="" class="alert-primary">Выберите тематику документа</option>
                                                        <option value="1" selected>Электроэнергия</option>
                                                        <option value="2">Водоотведение</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-4 text-left mt-3">
                                                    <span class="text-muted"><i class="fas fa-briefcase mr-2"></i> Организация</span>
                                                </div>
                                                <div class="col-md-8 mt-3">
                                                    <select class="browser-default custom-select" name="selectOrganisation[]" id="selectOrganisation">
                                                        <option value="" class="alert-primary">Выберите организацию</option>
                                                        <option value="1">ООО Гарант Инвест</option>
                                                        <option value="2">ОАО Бутовский химический завод</option>
                                                        <option value="3" selected>ОАО Квант-Н</option>
                                                    </select>
                                                </div>
                                                <div class="col-md-4 text-left mt-3">
                                                    <span class="text-muted"><i class="fas fa-user-tie mr-2"></i> Ответственный</span>
                                                </div>
                                                <div class="col-md-8 mt-3">
                                                    <select class="browser-default custom-select" name="selectCrucial[]" id="selectCrucial">
                                                        <option value="" class="alert-primary">Выберите ответственное лицо</option>
                                                        <option value="1" selected>В.Н. Минин, тел. 8 (495) 620 20 00, доб. 14832.</option>
                                                        <option value="2">И.И. Власкина, тел. 8 (495) 957 72 16 </option>
                                                    </select>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="marginBlock my-3"></div>
                                <div class="row">
                                    <div class="col-12 text-left">
                                        <div class="btn btn-primary btn-sm pointer addQuestion rounded" title="Добавить вопрос"><i class="fas fa-plus"></i> Добавить</div>
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
                        <button type="submit" class="btn btn-success mb-5 pt-3 submitBtn rounded">Согласовать</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="fragments/footerNew.jsp"/>
<jsp:include page="fragments/modals/agreeDocumentModal.jsp"/>
<jsp:include page="fragments/footerScript.jsp"/>
<jsp:include page="fragments/footerBasement.jsp"/>