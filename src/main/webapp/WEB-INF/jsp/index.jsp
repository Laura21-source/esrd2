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
                                <div class="row my-5 text-center">
                                    <div class="col-lg-4 col-md-12 mb-lg-0 mb-4 wow bounceInDown myCard">
                                        <h4 class="my-4">На исполнении</h4>
                                        <canvas id="chart1"></canvas>
                                        <div class="myImg">
                                            <i class="fas fa-briefcase white-text fa-4x"></i>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 col-md-12 mb-lg-0 mb-4 wow bounceInDown" data-wow-delay="0.3s">
                                        <h4 class="my-4">На согласовании</h4>
                                        <canvas id="chart2"></canvas>
                                        <div class="myImg">
                                            <i class="fas fa-edit white-text fa-4x"></i>
                                        </div>
                                    </div>
                                    <div class="col-lg-4 col-md-12 wow bounceInDown" data-wow-delay="0.6s">
                                        <h4 class="my-4">На распределении</h4>
                                        <canvas id="chart3"></canvas>
                                        <div class="myImg">
                                            <i class="fas fa-user-plus white-text fa-4x"></i>
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
        // График 1
        var ctxD = document.getElementById("chart1").getContext('2d');
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
        });
        // График 2
        var ctxD = document.getElementById("chart2").getContext('2d');
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
        });
        // График 3
        var ctxD = document.getElementById("chart3").getContext('2d');
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
        });
    });
</script>
<jsp:include page="fragments/footerBasement.jsp"/>