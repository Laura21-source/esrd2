<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="index.jsp"/>


<main>
    <div class="container-fluid text-center mb-4">
        <div class="card mx-auto w-100">
            <div class="card-body">
                <div class="container-fluid">
                    <div class="alert alert-secondary text-center mb-3">
                        <h4 class="mt-2">Организации</h4>
                    </div>
                    <table id="dataTable" class="table table-striped table-bordered table-sm table-hover" cellspacing="0" width="100%">
                        <thead>
                        <tr>
                            <th class="th-sm font-weight-bold alert-primary " width="10%">№ п/п</th>
                            <th class="th-sm font-weight-bold alert-primary " width="90%">Название</th>
                        </tr>
                        </thead>
                        <tbody id="rowContent">
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>
</main>

<jsp:include page="index.jsp"/>
<jsp:include page="../fragments/footerScript.jsp"/>
<jsp:include page="../fragments/adminFooterScript.jsp"/>
<jsp:include page="../fragments/modals/viewDocumentModal.jsp"/>

<script>
    $(function() {
        adminDataTableArray("#dataTable","rest/profile/organizations", 3);
    });


    // var i = 0;
    // $('#dataTable').DataTable( {
    //     ajax: {
    //         url: 'rest/profile/organizations',
    //         dataSrc:
    //
    //             function (data) {
    //             i+=1;
    //
    //             if (data.shortNameLf) {
    //                 data.shortNameLf = '<div style="text-align: left" id="' + data[i].id + '" ><a href="#" >' + data.shortNameLf + '</a></div>';
    //             }
    //             return (data, i);
    //             },
    //
    //
    //             function () {
    //                 $('#table td:first-child').each(function (i) {
    //                     $(this).html(i + 1);
    //                 })
    //             }
    //     },
    //     columns: [
    //         { data: 'i' },
    //         { data: 'shortNameLf' }
    //
    //     ],
    //
    //     "iDisplayLength": 25,
    //     "language": {
    //         "processing": "Подождите...",
    //         "search": "Поиск:",
    //         "lengthMenu": "Показать _MENU_ записей",
    //         "info": "Страница _PAGE_ из _PAGES_",
    //         "infoEmpty": "",
    //         "infoFiltered": "(отфильтровано из _MAX_ записей)",
    //         "infoPostFix": "",
    //         "loadingRecords": "Загрузка записей...",
    //         "zeroRecords": "Записи отсутствуют.",
    //         "emptyTable": "В таблице отсутствуют данные",
    //         "paginate": {
    //             "first": "",
    //             "previous": "",
    //             "next": "",
    //             "last": ""
    //         },
    //         "aria": {
    //             "sortAscending": ": активировать для сортировки столбца по возрастанию",
    //             "sortDescending": ": активировать для сортировки столбца по убыванию"
    //         }
    //     }
    // });

</script>

<jsp:include page="../fragments/footerBasement.jsp"/>



