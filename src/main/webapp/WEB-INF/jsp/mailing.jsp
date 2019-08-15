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
                        <h6 class="mt-2">Список рассылки документа</h6>
                    </div>
                    <form>
                        <div class="row ml-1 mb-3">
                            <div class="col-md-2 text-left mt-3">
                                <label for="inputText" class="text-muted"><i class="fas fa-comments mr-2"></i> Текст письма</label>
                            </div>
                            <div class="col-md-10 mt-3">
                                <textarea class="form-control rounded-0" id="inputText" name="inputText" rows="3"></textarea>
                            </div>
                        </div>
                        <div class="row ml-1 mb-1">
                            <div class="col-md-2 text-left mt-1">
                                <label for="inputFile" class="text-muted"><i class="fas fa-file-download mr-2"></i> Прикреплённый файл</label>
                            </div>
                            <div class="col-md-10 text-left">
                                <button type="button" id="inputFile" name="inputFile" class="btn btn-primary btn-sm rounded">Получить файл</button>
                            </div>
                        </div>
                        <div>&nbsp;</div>
                        <div class="row card mb-3" id="blockRecipient" req="true">
                            <div class="col-12">
                                <div class="card-body">
                                    <div class="row">
                                        <div class="col-md-9 text-left">
                                            <h5 id="nameRecipient">Получатель 1</h5>
                                        </div>
                                        <div class="col-md-3 text-right">
                                            <div id="delRecipient" class="btn btn-danger btn-sm pointer delRecipient d-none rounded" title="Удалить получателя"><i class="fas fa-trash"></i></div>
                                        </div>
                                    </div>
                                    <hr>
                                    <div class="row">
                                        <div class="col-md-2 text-left mt-3">
                                            <span class="text-muted"><i class="fas fa-briefcase mr-2"></i> Организация</span>
                                        </div>
                                        <div class="col-md-10 mt-3">
                                            <select class="browser-default custom-select" name="selectOrganisation[]" id="selectOrganisation">
                                                <option value="" class="alert-primary">Выберите организацию</option>
                                                <option value="1">ООО Гарант Инвест</option>
                                                <option value="2" selected>ОАО Бутовский химический завод</option>
                                                <option value="3">ОАО Квант-Н</option>
                                            </select>
                                        </div>
                                        <div class="col-md-2 text-left mt-3">
                                            <span class="text-muted"><i class="fas fa-user-tie mr-2"></i> Ответственный</span>
                                        </div>
                                        <div class="col-md-10 mt-3">
                                            <select class="browser-default custom-select" name="selectCrucial[]" id="selectCrucial">
                                                <option value="" class="alert-primary">Выберите ответственное лицо</option>
                                                <option value="1" selected>В.Н. Минин, тел. 8 (495) 620 20 00, доб. 14832.</option>
                                                <option value="2">И.И. Власкина, тел. 8 (495) 957 72 16 </option>
                                            </select>
                                        </div>
                                        <div class="col-md-2 text-left mt-3">
                                            <span class="text-muted"><i class="fas fa-envelope mr-2"></i> Email</span>
                                        </div>
                                        <div class="col-md-10 mt-3">
                                            <select class="browser-default custom-select" name="selectEmail[]" id="selectEmail">
                                                <option value="" class="alert-primary">Выберите электронную почту</option>
                                                <option value="1" selected>minin@email.ru</option>
                                                <option value="2">minin1@email.ru</option>
                                            </select>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="marginBlock my-3"></div>
                        <div class="row">
                            <div class="col-12 text-right">
                                <div class="btn btn-primary btn-sm pointer addRecipient rounded" title="Добавить получателя"><i class="fas fa-plus mr-2"></i> Добавить</div>
                            </div>
                        </div>
                        <button type="submit" class="btn btn-success mb-5 pt-3 submitBtn rounded">Отправить</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="fragments/footerNew.jsp"/>
<jsp:include page="fragments/footerScript.jsp"/>
<jsp:include page="fragments/footerBasement.jsp"/>