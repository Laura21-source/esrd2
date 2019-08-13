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
                        <h6 class="mt-2">Карточка документа №согл-1/19 от 08.08.2019</h6>
                    </div>
                    <form class="registrationForm">
                        <div class="row ml-1 mb-3">
                            <div class="col-md-2 text-left">
                                <span class="text-muted"><i class="fas fa-file-alt mr-2"></i> Вид документа</span>
                            </div>
                            <div class="col-md-10">
                                <select class="browser-default custom-select" name="selectType" id="selectType">
                                    <option value="" class="alert-primary">Выберите вид документа</option>
                                    <option value="1" selected disabled>Повестка заседания Правления</option>
                                </select>
                            </div>
                        </div>
                        <div id="formFieldEdit">
                            <div class="row ml-1 mb-3">
                                <div class="col-md-6">
                                    <div class="row">
                                        <div class="col-md-4 text-left">
                                            <span for="inputDate" class="text-muted"><i class="fas fa-calendar-alt mr-2"></i> Дата заседания</span>
                                        </div>
                                        <div class="col-md-8">
                                            <input title="Выберите дату" type="date" id="inputDate" name="inputDate" class="white form-control" value="2019-08-08" disabled>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6"></div>
                            </div>
                            <div class="row ml-1 mb-3">
                                <div class="col-md-6">
                                    <div class="row">
                                        <div class="col-md-4 text-left">
                                            <span for="inputTime" class="text-muted"><i class="fas fa-clock mr-2"></i> Время  заседания</span>
                                        </div>
                                        <div class="col-md-8">
                                            <input title="Выберите время" type="time" id="inputTime" name="inputTime" class="white form-control" value="11:45" disabled>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-md-6"></div>
                            </div>
                            <div>&nbsp;</div>
                            <div class="card my-3" id="blockQuestion" req="true">
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
                                            <div class="col-md-2 text-left mt-3">
                                                <span class="text-muted"><i class="fas fa-globe mr-2"></i> Тематика</span>
                                            </div>
                                            <div class="col-md-10 mt-3">
                                                <select class="browser-default custom-select" name="selectTheme" id="selectTheme">
                                                    <option value="" class="alert-primary my-2">Выберите тематику документа</option>
                                                    <option value="1" class="my-2" selected disabled>Электроэнергия</option>
                                                    <option value="2" class="my-2" disabled>Водоотведение</option>
                                                </select>
                                            </div>
                                            <div class="col-md-2 text-left mt-3">
                                                <span class="text-muted"><i class="fas fa-briefcase mr-2"></i> Организация</span>
                                            </div>
                                            <div class="col-md-10 mt-3">
                                                <select class="browser-default custom-select" name="selectOrganisation" id="selectOrganisation">
                                                    <option value="" class="alert-primary">Выберите организацию</option>
                                                    <option value="1" selected disabled>ООО Гарант Инвест</option>
                                                    <option value="2" disabled>ОАО Бутовский химический завод</option>
                                                    <option value="3" disabled>ОАО Квант-Н</option>
                                                </select>
                                            </div>
                                            <div class="col-md-2 text-left mt-3">
                                                <span class="text-muted"><i class="fas fa-user-tie mr-2"></i> Ответственный</span>
                                            </div>
                                            <div class="col-md-10 mt-3">
                                                <select class="browser-default custom-select" name="selectCrucial" id="selectCrucial">
                                                    <option value="" class="alert-primary">Выберите ответственное лицо</option>
                                                    <option value="1" selected disabled>В.Н. Минин, тел. 8 (495) 620 20 00, доб. 14832.</option>
                                                    <option value="2" disabled>И.И. Власкина, тел. 8 (495) 957 72 16 </option>
                                                </select>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="marginBlock my-3"></div>
                            <div class="row">
                                <div class="col-12 text-right">
                                    <button type="button" class="btn btn-primary btn-sm addQuestion mr-3 rounded" title="Добавить вопрос" disabled><i class="fas fa-plus"></i> Добавить</button>
                                </div>
                            </div>
                            <!--<button id="closeDocument" type="submit" class="btn btn-danger mb-5 pt-3 submitBtn rounded" data-toggle="modal" data-target="#registrationCansel">Отмена</button>-->
                            <a href="agree-document" id="closeDocument" type="button" class="btn btn-danger mb-5 pt-3 rounded">Отмена</a>
                            <button id="editDocument" type="submit" class="btn btn-primary mb-5 pt-3 submitBtn rounded">Правка</button>
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
<jsp:include page="fragments/footerBasement.jsp"/>