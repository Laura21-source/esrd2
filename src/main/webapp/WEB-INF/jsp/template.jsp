<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<jsp:include page="fragments/headerNew.jsp"/>
<c:set var = "main" />
<main>
    <div class="container-fluid text-center mb-4 pt-5">
        <div class="row">
            <div class="col-md-6">
                <section class="my-5">
                    <h2 class="alert alert-primary">Мои документы</h2>
                    <div class="row">
                        <div class="col-lg-4 col-md-12 mb-lg-0 mb-4 wow bounceInDown">
                            <div class="card-wrapper myCard">
                                <div id="card-1" class="card card-rotating text-center">
                                    <!-- Front Side -->
                                    <div class="face front">
                                        <div class="card-up">
                                            <img class="card-img-top" src="resources/img/in-work.jpg" alt="На исполнении">
                                        </div>
                                        <div class="avatar mx-auto white rounded-circle">
                                            <img class="p-2" src="resources/img/in-work.png" alt="Иконка">
                                        </div>
                                        <div class="card-body">
                                            <h4 class="font-weight-bold mt-1 mb-3">На исполнении</h4>
                                            <div class="text-center">
                                                <a href="in-work" class="btn btn-primary rounded">Перейти</a>
                                            </div>
                                        </div>
                                        <div>
                                            <a class="rotate-btn grey-text" data-card="card-1"><i class="fas fa-redo-alt grey-text mr-2"></i> Подробная информация</a>
                                        </div>
                                    </div>
                                    <!-- Front Side -->
                                    <!-- Back Side -->
                                    <div class="face back h-100">
                                        <div class="card-title mb-5">
                                            <h4 class="font-weight-bold mt-4 mb-2">
                                                <strong>Подробная информация</strong>
                                            </h4>
                                        </div>
                                        <div class="card-body text-left py-5 my-5">
                                            <h6 class="my-3">Всего документов <span class="badge badge-primary">17486</span></h6>
                                            <h6 class="my-3">Срок контроля более 3-х дней <span class="badge badge-success">13486</span></h6>
                                            <h6 class="my-3">Срок контроля 3 дня <span class="badge badge-danger">4000</span></h6>
                                        </div>
                                        <div>
                                            <a class="rotate-btn grey-text" data-card="card-1"><i class="fas fa-redo-alt grey-text mr-2"></i> Вернуться</a>
                                        </div>
                                    </div>
                                    <!-- Back Side -->
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-12 mb-lg-0 mb-4 wow bounceInDown" data-wow-delay="0.3s">
                            <div class="card-wrapper myCard">
                                <div id="card-2" class="card card-rotating text-center">
                                    <!-- Front Side -->
                                    <div class="face front">
                                        <div class="card-up">
                                            <img class="card-img-top" src="resources/img/agreement.jpg" alt="Согласование">
                                        </div>
                                        <div class="avatar mx-auto white rounded-circle">
                                            <img class="p-2" src="resources/img/agreement.png" alt="Иконка">
                                        </div>
                                        <div class="card-body">
                                            <h4 class="font-weight-bold mt-1 mb-3">Согласование</h4>
                                            <div class="text-center">
                                                <button class="btn btn-primary btn-floating" title="Всего документов">24517</button>
                                                <button class="btn btn-success btn-floating" title="Всего документов">24469</button>
                                                <button class="btn btn-danger btn-floating" title="Всего документов">48</button>
                                            </div>
                                        </div>
                                        <div class="card-footer white">
                                            <a class="rotate-btn grey-text" data-card="card-2"><i class="fas fa-redo-alt grey-text mr-2"></i> Подробная информация</a>
                                        </div>
                                    </div>
                                    <!-- Front Side -->
                                    <!-- Back Side -->
                                    <div class="face back h-100">
                                        <div class="card-title">
                                            <h4 class="font-weight-bold mt-4 mb-2">
                                                <strong>Подробная информация</strong>
                                            </h4>
                                        </div>
                                        <div class="card-body">
                                            <canvas id="lineChart"></canvas>
                                            <a class="rotate-btn grey-text" data-card="card-2"><i class="fas fa-redo-alt grey-text mr-2"></i> Вернуться</a>
                                        </div>
                                    </div>
                                    <!-- Back Side -->
                                </div>
                            </div>
                        </div>
                        <div class="col-lg-4 col-md-12 wow bounceInDown" data-wow-delay="0.6s">
                            <div class="card-wrapper myCard">
                                <div id="card-3" class="card card-rotating text-center">
                                    <!-- Front Side -->
                                    <div class="face front">
                                        <div class="card-up">
                                            <img class="card-img-top" src="resources/img/distribution.jpg"
                                                 alt="Распределение">
                                        </div>
                                        <div class="avatar mx-auto white rounded-circle">
                                            <img class="p-2" src="resources/img/distribution.png" alt="Иконка">
                                        </div>
                                        <div class="card-body">
                                            <h4 class="font-weight-bold mt-1 mb-3">Распределение</h4>
                                            <div class="text-center">
                                                <button class="btn btn-primary btn-floating" title="Всего документов">5849</button>
                                                <button class="btn btn-success btn-floating" title="Всего документов">4286</button>
                                                <button class="btn btn-danger btn-floating" title="Всего документов">1563</button>
                                            </div>
                                        </div>
                                        <div class="card-footer white">
                                            <a class="rotate-btn grey-text" data-card="card-3"><i class="fas fa-redo-alt grey-text mr-2"></i> Подробная информация</a>
                                        </div>
                                    </div>
                                    <!-- Front Side -->
                                    <!-- Back Side -->
                                    <div class="face back h-100">
                                        <div class="card-title">
                                            <h4 class="font-weight-bold mt-4 mb-2">
                                                <strong>Подробная информация</strong>
                                            </h4>
                                        </div>
                                        <div class="card-body">
                                            <canvas id="horizontalBar"></canvas>
                                            <a class="rotate-btn grey-text" data-card="card-3"><i class="fas fa-redo-alt grey-text mr-2"></i> Вернуться</a>
                                        </div>
                                    </div>
                                    <!-- Back Side -->
                                </div>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
            <div class="col-md-6"></div>
        </div>
    </div>
</main>

<jsp:include page="fragments/footerNew.jsp"/>
<jsp:include page="fragments/modals/newDocumentModal.jsp"/>
<jsp:include page="fragments/modals/viewDocumentModal.jsp"/>
<jsp:include page="fragments/footerScript.jsp"/>
<script>
    $(function() {
        $('body').css('background','none');
        $('#customSkin').removeClass('fixed-sn');
        $('#slide-out').hide();
        $('header, main, footer').css('padding-left',0);
        $('.float-left').addClass('d-none');
    });
</script>
<jsp:include page="fragments/footerBasement.jsp"/>