<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<!doctype html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title><spring:message code="app.title"/></title>
    <base href="${pageContext.request.contextPath}/"/>
    <link href="resources/img/favicon.ico" rel="icon" type="image/x-icon">
    <link href="resources/img/favicon.ico" rel="shortcut icon" type="image/x-icon">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
    <link rel="stylesheet" href="resources/css/index.css">
</head>
<body id="customSkin" class="fixed-sn dark-skin">

<header>
    <div id="slide-out" class="side-nav fixed">
        <ul class="custom-scrollbar">
            <li>
                <form class="search-form" role="search">
                    <div class="md-form mt-0 pt-1 waves-light">
                        <input type="text" class="form-control" placeholder="Поиск по документам">
                    </div>
                </form>
            </li>
            <li>
                <ul class="collapsible collapsible-accordion">
                    <!--<li><a href="#" class="collapsible-header waves-effect text-white">Повестки <span class="badge badge-danger ml-2">42</span></a></li>-->
                    <%--<li>
                        <a href="mailing" class="collapsible-header waves-effect text-white">
                            <i class="fas fa-clipboard-list mr-2"></i>Список рассылки
                        </a>
                    </li>--%>
                    <li>
                        <a href="new-document" class="collapsible-header waves-effect text-white">
                            <i class="fas fa-file-import mr-2"></i>Регистрация документа
                        </a>
                    </li>
                    <%--<li>
                        <a href="view-document?id=28" class="collapsible-header waves-effect text-white">
                            <i class="fas fa-eye mr-2"></i>Просмотр документа
                        </a>
                    </li>
                    <li>
                        <a href="agree-document?id=28" class="collapsible-header waves-effect text-white">
                            <i class="fas fa-file-signature mr-2"></i>Согласование документа
                        </a>
                    </li>--%>
                </ul>
            </li>
        </ul>
        <div class="sidenav-bg mask-strong"></div>
    </div>
    <!--Навигация-->
    <nav class="navbar fixed-top navbar-toggleable-md navbar-expand-lg scrolling-navbar double-nav black white-text">
        <div class="float-left">
            <a href="#" data-activates="slide-out" class="button-collapse"><i class="fas fa-bars"></i></a>
        </div>
        <a class="navbar-brand ml-3" href="#">
            <img src="resources/img/Logo.png" height="30" alt="Логотип">
        </a>
        <span class="breadcrumb-dn mr-auto">
        <h6 class="mt-2">Единая система регистрации документов (ЕСРД)</h6>
      </span>
        <ul class="nav navbar-nav nav-flex-icons ml-auto">
            <%--<li class="nav-item">
                <a class="nav-link"><i class="far fa-question-circle"></i> <span class="clearfix d-none d-sm-inline-block">Поддержка</span></a>
            </li>--%>
            <li class="nav-item dropdown">
                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown"
                   aria-haspopup="true" aria-expanded="false"><i class="fas fa-user"></i> Администратор
                </a>
                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">
                    <a class="dropdown-item" href="#"><i class="fas fa-cog"></i> Настройки</a>
                    <a class="dropdown-item" href="#"><i class="fas fa-sign-out-alt"></i> Выйти</a>
                </div>
            </li>
        </ul>
    </nav>
</header>