<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="index.jsp"/>
<%--<jsp:include page="../fragments/headerNew.jsp"/>--%>
<%--<c:set var = "main" />--%>
<main>

<%--    Карточка--%>
    <div class="container-fluid text-center mb-4">
        <div class="card mx-auto w-100 pb-5">
            <div class="card-body pb-5">
                <div class="container-fluid">

                    <div class="alert alert-secondary text-center mb-3">
                        <h4 class="mt-2">Подготовка проекта документа</h4>
                    </div>

                    <form class="registrationForm needs-validation" novalidate>
                        <div class="card">
                            <div class="card-body">
<%--                                Наименование--%>
                                <div class="row ml-1 mb-1 mt-2 d-flex align-items-center">
                                    <div class="col-md-2 text-left mt-2">
                                        <span class="text-muted"><i class="fas fa-file-alt mr-2"></i>Наименование</span>
                                    </div>

                                    <div class="col-md-10">
                                        <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Введите наименование документа">
<%--                                        <div id="selectType_invalid" class="d-none">Поле обязательно для заполнения</div>--%>
                                    </div>
                                </div>

<%--                       Шаблон--%>
                                <div class="row ml-1 mb-1 mt-2 d-flex">
                                    <div class="col-md-2 text-left mt-2">
                                        <span class="text-muted"><i class="fas fa-file-alt mr-2"></i>Шаблон</span>
                                    </div>
                                    <div class="col-md-3" style="display: inline;">
                                            <a href="#" class="btn btn-primary btn-md" style="font-size: .60rem; padding: .80rem 2rem;">Загрузить</a>
                                            <a href="#" class="btn btn-primary btn-md" style="font-size: .60rem; padding: .80rem 2rem">Скачать</a>
                                    </div>
                                </div>

<%--                            Роль с правом регистрации--%>
                                <div class="row ml-1 mb-1 mt-2 d-flex align-items-center">
                                    <div class="col-md-2 text-left mt-2">
                                        <span class="text-muted"><i class="fas fa-file-alt mr-2"></i>Роль с правом регистрации</span>
                                    </div>

                                    <div class="col-md-10">
                                        <select data-placeholder="Выберите вид документа" class="chosen-select" name="selectType" id="selectTypeRole" required>
                                            <option value="">Выберите из справочника</option>
                                        </select>
                                    </div>
                                </div>

<%--                            Префикс номера документа--%>
                                <div class="row ml-1 mb-1 mt-2 d-flex align-items-center">
                                    <div class="col-md-2 text-left mt-2">
                                        <span class="text-muted"><i class="fas fa-file-alt mr-2"></i>Префикс номера документа</span>
                                    </div>

                                    <div class="col-md-10">
                                        <select data-placeholder="Выберите вид документа" class="chosen-select" name="selectType" id="selectTypePref" required>
                                            <option value="">Выберите из справочника</option>
                                        </select>
                                    </div>
                                </div>

<%--                            Документ является финальным--%>
                                <div class="row ml-0 mb-3 mt-4 d-flex align-items-center">
                                    <div class="form-check">
                                        <input type="checkbox" class="form-check-input" id="materialUnchecked">
                                        <label class="form-check-label" for="materialUnchecked" style="color: #6c757d;">Документ является финальным</label>
                                    </div>
                                </div>

<%--                            Маска названия документа при публикации--%>
                                <div class="row ml-1 mb-1 mt-2 d-flex align-items-center">
                                    <div class="col-md-2 text-left mt-2">
                                        <span class="text-muted"><i class="fas fa-file-alt mr-2"></i>Маска названия документа при публикации</span>
                                    </div>

                                    <div class="col-md-10">
                                        <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Введите текст">
                                        <%--                                        <div id="selectType_invalid" class="d-none">Поле обязательно для заполнения</div>--%>
                                    </div>
                                </div>

<%--                            Параметры классификаторов при публикации--%>
                                <div class="row ml-1 mb-1 mt-2 d-flex align-items-center">
                                    <div class="col-md-2 text-left mt-2">
                                        <span class="text-muted"><i class="fas fa-file-alt mr-2"></i>Параметры классификаторов при публикации</span>
                                    </div>

                                    <div class="col-md-10">
                                        <input type="text" class="form-control" id="exampleInputEmail1" aria-describedby="emailHelp" placeholder="Введите текст">
                                        <%--                                        <div id="selectType_invalid" class="d-none">Поле обязательно для заполнения</div>--%>
                                    </div>
                                </div>

                                <div class="row ml-1 mb-1 mt-2 d-flex align-items-center">
                                    <div class="col-md-10">
                                        <button type="submit" class="btn btn-success mb-2 my-4 pt-3 rounded">Сохранить изменения</button>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </form>

                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="../fragments/footerNew.jsp"/>
<jsp:include page="../fragments/modals/newDocumentModal.jsp"/>
<jsp:include page="../fragments/modals/viewDocumentModal.jsp"/>
<jsp:include page="../fragments/footerScript.jsp"/>
<jsp:include page="../fragments/footerBasement.jsp"/>