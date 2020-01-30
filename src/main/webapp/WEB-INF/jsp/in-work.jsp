<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="fragments/headerNew.jsp"/>

<main>
    <div class="container-fluid text-center mb-4">
        <div class="card">
            <div class="card-body">
                <div class="alert alert-secondary text-center mb-3">
                    <h4 class="mt-2">Документы на исполнении</h4>
                </div>
                <table id="dataTable" class="table table-striped table-bordered table-sm table-hover" cellspacing="0" width="100%">
                    <thead>
                    <tr>
                        <th class="th-sm font-weight-bold alert-primary" width="10%">№ п/п</th>
                        <th class="th-sm font-weight-bold alert-primary" width="5%">Статус</th>
                        <th class="th-sm font-weight-bold alert-primary" width="20%">Рег. номер</th>
                        <th class="th-sm font-weight-bold alert-primary" width="20%">Дата регистрации</th>
                        <th class="th-sm font-weight-bold alert-primary" width="20%">Дата контроля</th>
                        <th class="th-sm font-weight-bold alert-primary" width="40%">Вид документа</th>
                        <th class="th-sm font-weight-bold alert-primary" width="40%">Подразделения-исполнители</th>
                        <th class="th-sm font-weight-bold alert-primary" width="40%">Исполнители</th>
                        <th class="th-sm font-weight-bold alert-primary" width="10%">Карточка документа</th>
                        <th class="th-sm font-weight-bold alert-primary" width="10%">Выбор</th>
                    </tr>
                    </thead>
                    <tbody id="rowContent"></tbody>
                </table>
                <a href="#" class="btn btn-amber btn-sm rounded mb-3 float-right d-none" id="btnAnswer">Подготовить ответ</a>
            </div>
        </div>
    </div>
</main>

<jsp:include page="fragments/footerNew.jsp"/>
<jsp:include page="fragments/footerScript.jsp"/>
<jsp:include page="fragments/modals/viewDocumentModal.jsp"/>
<script>
    $(function() {
        dataTableArray("#dataTable","rest/profile/docs/inwork", 1);
    });
</script>
<jsp:include page="fragments/footerBasement.jsp"/>