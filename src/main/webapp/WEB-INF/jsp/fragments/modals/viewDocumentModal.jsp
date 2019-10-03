<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal fade" id="addElement" tabindex="-1" role="dialog" aria-labelledby="addElement"
     aria-hidden="true">
    <div class="modal-dialog modal-primary modal-dialog-centered modal-size-md modal-notify" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <p class="heading lead">Добавление организации</p>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="white-text">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="progress md-progress primary-color-dark bigFormLoader d-none">
                    <div class="indeterminate"></div>
                </div>
                <form class="addElementForm">
                    <div class="row mb-2">
                        <div class="col-3">
                            <span class="text-muted">ИНН</span>
                        </div>
                        <div class="col-9">
                            <div class="input-group">
                                <input type="text" id="inn" class="form-control">
                                <div class="input-group-append">
                                    <button class="btn btn-md btn-primary m-0 px-3" type="button" id="btnEgrul">Заполнить из ЕГРЮЛ</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-3">
                            <span class="text-muted">Сокращённое название</span>
                        </div>
                        <div class="col-9">
                            <input type="text" id="shortName" class="form-control" <%--disabled--%>>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-3">
                            <span class="text-muted">Полное название</span>
                        </div>
                        <div class="col-9">
                            <input type="text" id="fullName" class="form-control" <%--disabled--%>>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-6">
                            <div class="row">
                                <div class="col-3 d-flex align-items-center justify-content-center">
                                    <span class="text-muted">ОГРН</span>
                                </div>
                                <div class="col-9">
                                    <input type="text" id="ogrn" class="form-control" <%--disabled--%>>
                                </div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="row">
                                <div class="col-3 d-flex align-items-center justify-content-center">
                                    <span class="text-muted">КПП</span>
                                </div>
                                <div class="col-9">
                                    <input type="text" id="kpp" class="form-control" <%--disabled--%>>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-3">
                            <span class="text-muted">Адрес</span>
                        </div>
                        <div class="col-9">
                            <input type="text" id="address" class="form-control" <%--disabled--%>>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-3">
                            <span class="text-muted">Должность</span>
                        </div>
                        <div class="col-9">
                            <input type="text" id="positionManager" class="form-control" <%--disabled--%>>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-3">
                            <span class="text-muted">ФИО</span>
                        </div>
                        <div class="col-9">
                            <input type="text" id="fioManager" class="form-control" <%--disabled--%>>
                        </div>
                    </div>
                </form>
            </div>
            <div class="d-flex align-items-center justify-content-center pb-2">
                <a href=#" type="button" class="btn btn-primary rounded btnAddElement">Добавить</a>
                <a href="#" type="button" class="btn btn-danger rounded" data-dismiss="modal">Отмена</a>
            </div>
        </div>
    </div>
</div>