<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!doctype html>
<html lang="ru">

<head>
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <title><spring:message code="app.title"/></title>
    <base href="${pageContext.request.contextPath}/"/>
    <link href="resources/img/favicon.ico" rel="icon" type="image/x-icon">
    <link href="resources/img/favicon.ico" rel="shortcut icon" type="image/x-icon">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.6.3/css/all.css">
<%--    <link rel="stylesheet" href="resources/css/index.css">--%>
    <link rel="stylesheet" href="resources/css/admIndex.css">
</head>

<body id="customSkin" class="fixed-sn dark-skin">

<header>
    <div id="slide-out" class="side-nav fixed">

        <ul class="custom-scrollbar">
            <li>
                <form class="search-form" role="search">
                    <div class="md-form mt-8 pr-1">
                        <input type="text" class="form-control search"  placeholder="Поиск">
<%--                        <input type="text" class="form-control" disabled>--%>

                    </div>
                </form>
            </li>
            <li>
                <ul class="collapsible collapsible-accordion">
                    <li><a class="collapsible-header waves-effect arrow-r" href="admin/doctype"><i class="fa fa-file-alt mr-2"></i>Виды документа</a></li>
                    <li><a class="collapsible-header waves-effect arrow-r" href="admin/departments"><i class="fas fa-sitemap mr-2"></i>Подразделения</a></li>
                    <li><a class="collapsible-header waves-effect arrow-r" href="admin/users"><i class="fas fa-user mr-2"></i>Пользователи</a></li>
                    <li><a class="collapsible-header waves-effect arrow-r" href="roles"><i class="fa fa-list mr-2"></i>Роли</a></li>
                    <li><a class="collapsible-header waves-effect arrow-r" href="dictionary"><i class="fas fa-book-reader mr-2"></i>Справочники</a></li>
                    <li><a class="collapsible-header waves-effect arrow-r" href="admin/organizations"><i class="fas fa-user-friends mr-2"></i>Организации</a></li>
                    <li><a class="collapsible-header waves-effect arrow-r" href="tables"><i class="fas fa-table mr-2"></i>Таблицы</a></li>
<%--                    <li><a class="collapsible-header waves-effect arrow-r" href="admin/admNew-document"><i class="fas fa-table mr-2"></i>Новый документ</a></li>--%>
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
        <a class="navbar-brand ml-3" href="admin/index">
            <img src="resources/img/Logo.png" height="30" alt="Логотип">
        </a>
        <span class="breadcrumb-dn mr-auto">
            <h6 class="mt-2">Административная панель</h6>
        </span>
<%--        <ul class="nav navbar-nav nav-flex-icons ml-auto">--%>
<%--            <li class="nav-item">--%>
<%--                <a class="nav-link" data-toggle="modal" data-target="#helpBlock"><i class="far fa-question-circle mr-2"></i><span class="clearfix d-none d-sm-inline-block mr-3">Поддержка</span></a>--%>
<%--            </li>--%>
<%--            <li class="nav-item dropdown">--%>
<%--                <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown"--%>
<%--                   aria-haspopup="true" aria-expanded="false"><i class="fas fa-user mr-2"></i><span id="templateUser"><sec:authentication property="principal.username"/></span>--%>
<%--                </a>--%>
<%--                <div id="currentUser" class="fontSmall"></div>--%>
<%--                <div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">--%>
<%--                    &lt;%&ndash;<a class="dropdown-item" href="#"><i class="fas fa-cog"></i> Настройки</a>&ndash;%&gt;--%>
<%--                    <form:form id="form-logout" class="dropdown-item" action="logout" method="post">--%>
<%--                        <span type="submit" onclick="document.forms['form-logout'].submit();">--%>
<%--                            <i class="fas fa-sign-out-alt"></i> <spring:message code="app.quit"/>--%>
<%--                        </span>--%>
<%--                    </form:form>--%>
<%--                </div>--%>
<%--            </li>--%>
<%--        </ul>--%>
    </nav>
</header>