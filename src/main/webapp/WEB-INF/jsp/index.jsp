<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="fragments/headerNew.jsp"/>
<c:set var = "main" />
<main>
    <div class="container-fluid w-75 mb-4 pt-5">
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
                                        <h3>Тарифное регулирование</h3>
                                    </div>
                                </div>
                                <div class="row my-2 text-center">
                                    <div class="col-lg-4 col-md-12 mb-lg-0 mb-4 wow bounceInDown">
                                        <h4 class="my-2">На исполнении</h4>
                                        <div class="blockChart">
                                            <div class="myImg">
                                                <i class="fas fa-briefcase white-text fa-4x"></i>
                                            </div>
                                            <div id="chartDiv1" class="chartDiv"></div>
                                        </div>
                                        <a href="in-work" class=""></a>
                                        <%--<canvas id="chart1"></canvas>--%>
                                    </div>
                                    <div class="col-lg-4 col-md-12 mb-lg-0 mb-4 wow bounceInDown" data-wow-delay="0.3s">
                                        <h4 class="my-2">На согласовании</h4>
                                        <div class="blockChart">
                                            <div class="myImg">
                                                <a href="agreement" title="Документы на согласовании"><i class="fas fa-edit white-text fa-4x"></i></a>
                                            </div>
                                            <div id="chartDiv2" class="chartDiv"></div>
                                        </div>
                                        <%--<canvas id="chart2"></canvas>--%>
                                    </div>
                                    <div class="col-lg-4 col-md-12 mb-lg-0 mb-4 wow bounceInDown" data-wow-delay="0.6s">
                                        <h4 class="my-2">На распределении</h4>
                                        <div class="blockChart">
                                            <div class="myImg">
                                                <a href="distribution" title="Документы на распределении"><i class="fas fa-user-plus white-text fa-4x"></i></a>
                                            </div>
                                            <div id="chartDiv3" class="chartDiv"></div>
                                        </div>
                                        <%--<canvas id="chart3"></canvas>--%>
                                    </div>
                                </div>
                            </section>
                            <section class="mb-5">
                                <div class="row my-2 text-center">
                                    <div class="col-lg-4 col-md-12 mb-lg-0 mb-4">
                                        <h4 class="my-5">Документы управления</h4>
                                        <h6 class="my-2">На исполнении</h6>
                                        <div class="row d-flex align-items-center white-text">
                                            <div class="col-md-6">
                                                <div class="text-right">
                                                    <p>срок более 3 дней</p>
                                                    <p>срок 3 дня</p>
                                                </div>
                                            </div>
                                            <div class="col-md-6 text-left">
                                                <button class="btn btn-sm btn-default w-100 font-weight-bold">268</button>
                                                <button class="btn btn-sm btn-danger w-75 font-weight-bold">120</button>
                                            </div>
                                        </div>
                                        <h6 class="my-2">На согласовании</h6>
                                        <div class="row d-flex align-items-center white-text">
                                            <div class="col-md-6">
                                                <div class="text-right">
                                                    <p>срок более 3 дней</p>
                                                    <p>срок 3 дня</p>
                                                </div>
                                            </div>
                                            <div class="col-md-6 text-left">
                                                <button class="btn btn-sm btn-default w-100 font-weight-bold">120</button>
                                                <button class="btn btn-sm btn-danger w-50 font-weight-bold">12</button>
                                            </div>
                                        </div>
                                        <h6 class="my-2">На распределении</h6>
                                        <div class="row d-flex align-items-center white-text">
                                            <div class="col-md-6">
                                                <div class="text-right">
                                                    <p>срок более 3 дней</p>
                                                    <p>срок 3 дня</p>
                                                </div>
                                            </div>
                                            <div class="col-md-6 text-left">
                                                <button class="btn btn-sm btn-default w-100 font-weight-bold">120</button>
                                                <button class="btn btn-sm btn-danger w-50 font-weight-bold">12</button>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 col-md-12 mb-lg-0 mb-4">
                                        <h4 class="my-5">Регистрация документа</h4>
                                        <a href="new-document" title="Создать новый документ">
                                            <i class="fas fa-file-alt fa-6x white-text newDoc"></i>
                                        </a>
                                    </div>
                                    <div class="col-lg-4 col-md-12 mb-lg-0 mb-4">
                                        <h4 class="my-5">Моя дисциплина за месяц</h4>
                                        <div class="row d-flex align-items-center justify-content-center white-text">
                                            <div class="col-md-6 text-right">
                                                Всего было на контроле
                                            </div>
                                            <div class="col-md-6 text-left">
                                                <button class="btn btn-sm btn-primary w-100 font-weight-bold">268</button>
                                            </div>
                                        </div>
                                        <div class="row d-flex align-items-center justify-content-center white-text">
                                            <div class="col-md-6 text-right">
                                                Исполнено в срок
                                            </div>
                                            <div class="col-md-6 text-left">
                                                <button class="btn btn-sm btn-default w-75 font-weight-bold">250</button>
                                            </div>
                                        </div>
                                        <div class="row d-flex align-items-center justify-content-center white-text">
                                            <div class="col-md-6 text-right">
                                                Исполнено с нарушением срока
                                            </div>
                                            <div class="col-md-6 text-left">
                                                <button class="btn btn-sm btn-warning w-50 font-weight-bold">14</button>
                                            </div>
                                        </div>
                                        <div class="row d-flex align-items-center justify-content-center white-text">
                                            <div class="col-md-6 text-right">
                                                Не исполнено (срок вышел)
                                            </div>
                                            <div class="col-md-6 text-left">
                                                <button class="btn btn-sm btn-danger w-25 font-weight-bold">4</button>
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
        $('#customSkin').removeClass('fixed-sn');
        $('#slide-out').hide();
        $('header, main, footer').css('padding-left',0);
        $('.float-left').addClass('d-none');

        am4core.ready(function() {
            am4core.useTheme(am4themes_dark);
            am4core.useTheme(am4themes_animated);
            var chart = am4core.create("chartDiv1", am4charts.PieChart3D);
            chart.hiddenState.properties.opacity = 0; // this creates initial fade-in
            chart.data = [
                {
                    country: "Срок контроля 3 дня",
                    litres: 10,
                    color: am4core.color("#ff4444")
                },
                {
                    country: "Срок контроля более 3 дней",
                    litres: 60,
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
                    litres: 36,
                    color: am4core.color("#ff4444")
                },
                {
                    country: "Срок контроля более 3 дней",
                    litres: 18,
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
                    litres: 3,
                    color: am4core.color("#ff4444")
                },
                {
                    country: "Срок контроля более 3 дней",
                    litres: 8,
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