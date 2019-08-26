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
                        <h6 class="mt-2">Документы на исполнении</h6>
                    </div>
                    <table id="dataTable" class="table table-striped table-bordered table-sm table-hover" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th class="th-sm font-weight-bold alert-primary" width="10%">№ п/п</th>
                            <th class="th-sm font-weight-bold alert-primary" width="20%">Номер</th>
                            <th class="th-sm font-weight-bold alert-primary" width="20%">Дата регистрации</th>
                            <th class="th-sm font-weight-bold alert-primary" width="40%">Название</th>
                            <th class="th-sm font-weight-bold alert-primary" width="10%">Согласовать</th>
                        </tr>
                        </thead>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="fragments/footerNew.jsp"/>
<jsp:include page="fragments/footerScript.jsp"/>
<script>
    $(function() {
        var tableArray = getListArray("rest/profile/docs/agreement");
        console.log(tableArray);
        // Сортируемые таблицы
        $('#dataTable').DataTable({
            "ajax" : tableArray
            /*"ajax": {
                url : "rest/profile/docs/agreement",
                dataSrc: ''
            }*/,
            "columns": [
                { data: 'id' },
                { data: 'number' },
                { data: 'date' },
                { data: 'name' },
                { data: 'link' }
            ],
            "language": {
                "processing": "Подождите...",
                "search": "Поиск:",
                "lengthMenu": "Показать _MENU_ записей",
                "info": "Страница _PAGE_ из _PAGES_",
                "infoEmpty": "",
                "infoFiltered": "(отфильтровано из _MAX_ записей)",
                "infoPostFix": "",
                "loadingRecords": "Загрузка записей...",
                "zeroRecords": "Записи отсутствуют.",
                "emptyTable": "В таблице отсутствуют данные",
                "paginate": {
                    "first": "",
                    "previous": "",
                    "next": "",
                    "last": ""
                },
                "aria": {
                    "sortAscending": ": активировать для сортировки столбца по возрастанию",
                    "sortDescending": ": активировать для сортировки столбца по убыванию"
                }
            }
        });
        $('.dataTables_length').addClass('bs-select');
    });
</script>
<jsp:include page="fragments/footerBasement.jsp"/>