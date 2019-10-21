<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="fragments/headerIndex.jsp"/>
<c:set var = "main" />
<main>
    <div class="container-fluid mb-4 pt-4">
        <div class="card mt-5 white-text rgba-black-light">
            <div class="card-body">
                <div class="container-fluid">
                    <div class="row">
                        <div class="col-12">
                            <section class="mb-5">
                                <div class="row mb-2">
                                    <div class="col-5">
                                        <h3><i class="fas fa-id-card white-text mr-2"></i>Мои документы</h3>
                                    </div>
                                    <div class="col-7">
                                        <h3 class="ml-n5">Тарифное регулирование</h3>
                                    </div>
                                </div>
                                <div class="row my-2 text-center">
                                    <div class="col-lg-4 col-md-12 mb-lg-0 mb-4 wow bounceInDown" >
                                        <h4 class="my-2">На исполнении (<span class="inWorkSum"></span>)</h4>
                                        <div class="blockChart">
                                            <div class="myImg">
                                                <i class="fas fa-briefcase white-text fa-4x" id="iconMenu1"></i>
                                            </div>
                                            <div id="chartDiv1" class="chartDiv"></div>
                                        </div>
                                        <a href="in-work" class="chartLink" data-icon="1"></a>
                                        <%--<canvas id="chart1"></canvas>--%>
                                    </div>
                                    <div class="col-lg-4 col-md-12 mb-lg-0 mb-4 wow bounceInDown" data-wow-delay="0.3s">
                                        <h4 class="my-2">На согласовании (<span class="agreementSum"></span>)</h4>
                                        <div class="blockChart">
                                            <div class="myImg">
                                                <i class="fas fa-edit white-text fa-4x" id="iconMenu2"></i>
                                            </div>
                                            <div id="chartDiv2" class="chartDiv"></div>
                                        </div>
                                        <a href="agreement" class="chartLink" data-icon="2"></a>
                                        <%--<canvas id="chart2"></canvas>--%>
                                    </div>
                                    <div class="col-lg-4 col-md-12 mb-lg-0 mb-4 wow bounceInDown" data-wow-delay="0.6s">
                                        <h4 class="my-2">На распределении (<span class="distributionSum"></span>)</h4>
                                        <div class="blockChart">
                                            <div class="myImg">
                                                <i class="fas fa-user-plus white-text fa-4x" id="iconMenu3"></i>
                                            </div>
                                            <div id="chartDiv3" class="chartDiv"></div>
                                        </div>
                                        <a href="distribution" class="chartLink" data-icon="3"></a>
                                        <%--<canvas id="chart3"></canvas>--%>
                                    </div>
                                </div>
                            </section>
                            <section>
                                <div class="row my-2 text-center">
                                    <div class="col-lg-4 col-md-12 mb-lg-0 mb-4">
                                        <h4 class="my-3">Документы управления</h4>
                                        <h6>На исполнении</h6>
                                        <div class="row d-flex align-items-center white-text">
                                            <div class="col-md-6 text-right">
                                                срок более 3 дней
                                            </div>
                                            <div class="col-md-6 text-left">
                                                <div class="btn btn-sm btn-default font-weight-bold px-1" id="inWorkSuccess"></div>
                                            </div>
                                        </div>
                                        <div class="row d-flex align-items-center white-text">
                                            <div class="col-md-6 text-right">
                                                срок 3 дня
                                            </div>
                                            <div class="col-md-6 text-left">
                                                <div class="btn btn-sm btn-danger font-weight-bold px-1" id="inWorkDanger"></div>
                                            </div>
                                        </div>
                                        <h6>На согласовании</h6>
                                        <div class="row d-flex align-items-center white-text">
                                            <div class="col-md-6 text-right">
                                                срок более 3 дней
                                            </div>
                                            <div class="col-md-6 text-left">
                                                <div class="btn btn-sm btn-default font-weight-bold px-1" id="agreeSuccess"></div>
                                            </div>
                                        </div>
                                        <div class="row d-flex align-items-center white-text">
                                            <div class="col-md-6 text-right">
                                                срок 3 дня
                                            </div>
                                            <div class="col-md-6 text-left">
                                                <div class="btn btn-sm btn-danger font-weight-bold px-1" id="agreeDanger"></div>
                                            </div>
                                        </div>
                                        <h6>На распределении</h6>
                                        <div class="row d-flex align-items-center white-text">
                                            <div class="col-md-6 text-right">
                                                срок более 3 дней
                                            </div>
                                            <div class="col-md-6 text-left">
                                                <div class="btn btn-sm btn-default font-weight-bold px-1" id="distSuccess"></div>
                                            </div>
                                        </div>
                                        <div class="row d-flex align-items-center white-text">
                                            <div class="col-md-6 text-right">
                                                срок 3 дня
                                            </div>
                                            <div class="col-md-6 text-left">
                                                <div class="btn btn-sm btn-danger font-weight-bold px-1" id="distDanger"></div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 col-md-12 mb-lg-0 mb-4">
                                        <h4 class="my-3">Регистрация документа</h4>
                                        <a href="new-document" title="Создать новый документ">
                                            <i class="fas fa-file-alt fa-6x white-text newDoc"></i>
                                        </a>
                                    </div>
                                    <div class="col-lg-4 col-md-12 mb-lg-0 mb-4">
                                        <h4 class="my-3">Моя дисциплина за месяц</h4>
                                        <div class="row d-flex align-items-center justify-content-center white-text">
                                            <div class="col-md-6 text-right">Всего было на контроле</div>
                                            <div class="col-md-6 text-left">
                                                <div class="btn btn-sm btn-primary font-weight-bold px-1" id="mySum" data-value="0"></div>
                                            </div>
                                        </div>
                                        <div class="row d-flex align-items-center justify-content-center white-text">
                                            <div class="col-md-6 text-right">Исполнено в срок</div>
                                            <div class="col-md-6 text-left">
                                                <div class="btn btn-sm btn-default font-weight-bold px-1" id="mySuccess"></div>
                                            </div>
                                        </div>
                                        <div class="row d-flex align-items-center justify-content-center white-text">
                                            <div class="col-md-6 text-right">Исполнено с нарушением срока</div>
                                            <div class="col-md-6 text-left">
                                                <div class="btn btn-sm btn-warning font-weight-bold px-1" id="myWarning"></div>
                                            </div>
                                        </div>
                                        <div class="row d-flex align-items-center justify-content-center white-text">
                                            <div class="col-md-6 text-right">Не исполнено (срок вышел)</div>
                                            <div class="col-md-6 text-left">
                                                <div class="btn btn-sm btn-danger font-weight-bold px-1" id="myDanger"></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </section>
                        </div>
                    </div>
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
        $('header, main, footer').css('padding-left',0);

        $('.chartLink').hover(
            function() {
                var id = $(this).attr('data-icon');
                $('#iconMenu'+id).addClass('animated rotateIn');
            },
            function() {
                var id = $(this).attr('data-icon');
                $('#iconMenu'+id).removeClass('animated rotateIn');
            });

        $('.newDoc').hover(
            function() {
                $(this).addClass('animated heartBeat');
            },
            function() {
                $(this).removeClass('animated heartBeat');
            });

        // Показ цифр дисциплина
        //var mySum = countElemJSON('rest/profile/docs/agreement'); // Общее rest/profile/docs/atThisMounthOnControl
        //console.log(mySum);
        //var mySuccess = countElemJSON('rest/profile/docs/atThisMounthOnControlCompletedInTime'); // Успешные
        //var myWarning = countElemJSON('rest/profile/docs/atThisMounthOnControlCompletedAfterTime'); // С ошибками
        //var myDanger = countElemJSON('rest/profile/docs/atThisMounthOnControlNotCompleted'); // Незавершенные

        var mySum = 39; var mySuccess = 28; var myWarning = 8; var myDanger = 3;
        // Отображение размера плашек от значений показателей
        var myDis = [{'pole':'#mySum','value':mySum},{'pole':'#mySuccess','value':mySuccess},{'pole':'#myWarning','value':myWarning},{'pole':'#myDanger','value':myDanger}];
        statisticBlock(myDis);

        // Данные для показа общей статистики на исполнении
        // inWorkSuccess = countElemJSON('rest/profile/docs/inworkMoreDeadlineByUserName'); // Более 3 дней
        // inWorkDanger = countElemJSON('rest/profile/docs/inworkLessDeadlineByUserName'); // 3 дня
        var inWorkSuccess = 268; var inWorkDanger = 36;
        var allInWork = [{'pole':'#inWorkSuccess','value':inWorkSuccess},{'pole':'#inWorkDanger','value':inWorkDanger}];
        statisticBlock(allInWork);

        // Данные для показа общей статистики на согласовании
        // agreeSuccess = countElemJSON('rest/profile/docs/agreementMoreDeadlineByUserName'); // Более 3 дней
        // agreeDanger = countElemJSON('rest/profile/docs/agreementLessDeadlineByUserName'); // 3 дня
        var agreeSuccess = 120; var agreeDanger = 12;
        var allAgrre = [{'pole':'#agreeSuccess','value':agreeSuccess},{'pole':'#agreeDanger','value':agreeDanger}];
        statisticBlock(allAgrre);

        // Данные для показа общей статистики на респределении
        // distSuccess = countElemJSON('rest/profile/docs/distributionMoreDeadlineByChiefUserName'); // Более 3 дней
        // distDanger = countElemJSON('rest/profile/docs/distributionLessDeadlineByChiefUserName'); // 3 дня
        var distSuccess = 86; var distDanger = 11;
        var allDist = [{'pole':'#distSuccess','value':distSuccess},{'pole':'#distDanger','value':distDanger}];
        statisticBlock(allDist);

        // Данные для графика на исполнении
        var chartDiv1Success = inWorkSuccess;
        var chartDiv1Danger = inWorkDanger;
        // Данные для графика на согласовании
        var chartDiv2Success = agreeSuccess;
        var chartDiv2Danger = agreeDanger;
        // Данные для графика на распределении
        var chartDiv3Success = distSuccess;
        var chartDiv3Danger = distDanger;

        // Графики
        am4core.ready(function() {
            am4core.useTheme(am4themes_dark);
            am4core.useTheme(am4themes_animated);
            var chart = am4core.create("chartDiv1", am4charts.PieChart3D);
            chart.hiddenState.properties.opacity = 0; // this creates initial fade-in
            chart.data = [
                {
                    country: "Срок контроля 3 дня",
                    litres: chartDiv1Danger,
                    color: am4core.color("#ff4444")
                },
                {
                    country: "Срок контроля более 3 дней",
                    litres: chartDiv1Success,
                    color: am4core.color("#2BBBAD")
                }
            ];
            var pieSeries = chart.series.push(new am4charts.PieSeries3D());
            pieSeries.dataFields.value = "litres";
            pieSeries.dataFields.category = "country";
            pieSeries.slices.template.propertyFields.fill = "color";
            chart.innerRadius = am4core.percent(63);
            pieSeries.labels.template.disabled = true;
            pieSeries.ticks.template.disabled = true;
            //chart.legend = new am4charts.Legend();
            //chart.legend.position = "bottom";
        });

        am4core.ready(function() {
            am4core.useTheme(am4themes_dark);
            am4core.useTheme(am4themes_animated);
            var chart = am4core.create("chartDiv2", am4charts.PieChart3D);
            chart.hiddenState.properties.opacity = 0; // this creates initial fade-in
            chart.data = [
                {
                    country: "Срок контроля 3 дня",
                    litres: chartDiv2Danger,
                    color: am4core.color("#ff4444")
                },
                {
                    country: "Срок контроля более 3 дней",
                    litres: chartDiv2Success,
                    color: am4core.color("#2BBBAD")
                }
            ];
            var pieSeries = chart.series.push(new am4charts.PieSeries3D());
            pieSeries.dataFields.value = "litres";
            pieSeries.dataFields.category = "country";
            pieSeries.slices.template.propertyFields.fill = "color";
            chart.innerRadius = am4core.percent(63);
            pieSeries.labels.template.disabled = true;
            pieSeries.ticks.template.disabled = true;
        });

        am4core.ready(function() {
            am4core.useTheme(am4themes_dark);
            am4core.useTheme(am4themes_animated);
            var chart = am4core.create("chartDiv3", am4charts.PieChart3D);
            chart.hiddenState.properties.opacity = 0; // this creates initial fade-in
            chart.data = [
                {
                    country: "Срок контроля 3 дня",
                    litres: chartDiv3Danger,
                    color: am4core.color("#ff4444")
                },
                {
                    country: "Срок контроля более 3 дней",
                    litres: chartDiv3Success,
                    color: am4core.color("#2BBBAD")
                }
            ];
            var pieSeries = chart.series.push(new am4charts.PieSeries3D());
            pieSeries.dataFields.value = "litres";
            pieSeries.dataFields.category = "country";
            pieSeries.slices.template.propertyFields.fill = "color";
            chart.innerRadius = am4core.percent(63);
            pieSeries.labels.template.disabled = true;
            pieSeries.ticks.template.disabled = true;
        });

        // График 1
        /*var ctxD = document.getElementById("chart1").getContext('2d');
        var myLineChart = new Chart(ctxD, {
            type: 'doughnut',
            data: {
                labels: ["Срок контроля более 3 дней", "Срок контроля 3 дня"],
                datasets: [{
                    data: [14, 3],
                    backgroundColor: ["#2BBBAD", "#ff4444"],
                    hoverBackgroundColor: ["#00695c", "#CC0000"]
                }]
            },
            options: {
                responsive: true,
                label: 'Какой-то текст',
                legend: {
                    display: false,
                },
                labels: {
                    fontColor: '#ffffff'
                }
            }
        });*/
        // График 2
        /*var ctxD = document.getElementById("chart2").getContext('2d');
        var myLineChart = new Chart(ctxD, {
            type: 'doughnut',
            data: {
                labels: ["Срок контроля более 3 дней", "Срок контроля 3 дня"],
                datasets: [{
                    data: [14, 3],
                    backgroundColor: ["#2BBBAD", "#ff4444"],
                    hoverBackgroundColor: ["#00695c", "#CC0000"]
                }]
            },
            options: {
                responsive: true,
                label: 'Какой-то текст',
                legend: {
                    display: false,
                },
                labels: {
                    fontColor: '#ffffff'
                }
            }
        });*/
        // График 3
        /*var ctxD = document.getElementById("chart3").getContext('2d');
        var myLineChart = new Chart(ctxD, {
            type: 'doughnut',
            data: {
                labels: ["Срок контроля более 3 дней", "Срок контроля 3 дня"],
                datasets: [{
                    data: [14, 3],
                    backgroundColor: ["#2BBBAD", "#ff4444"],
                    hoverBackgroundColor: ["#00695c", "#CC0000"]
                }]
            },
            options: {
                responsive: true,
                label: 'Какой-то текст',
                legend: {
                    display: false,
                },
                labels: {
                    fontColor: '#ffffff'
                }
            }
        });*/

    });
</script>
<jsp:include page="fragments/footerBasement.jsp"/>