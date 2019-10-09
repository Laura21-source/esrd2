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
            <div class="modal-body fontSmall">
                <div class="progress md-progress primary-color-dark bigFormLoader d-none">
                    <div class="indeterminate"></div>
                </div>
                <form class="addElementForm needs-validation" novalidate>
                    <div class="row mb-2">
                        <div class="col-4">
                            <div class="text-muted">ИНН<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup></div>
                        </div>
                        <div class="col-8">
                            <div class="input-group">
                                <input type="text" id="inn" class="form-control form-control-sm" required>
                                <div class="input-group-append">
                                    <button class="btn btn-sm btn-primary m-0 px-3" type="button" id="btnEgrul">Заполнить из ЕГРЮЛ</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-4">
                            <div class="text-muted">Короткое название<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup></div>
                        </div>
                        <div class="col-8">
                            <input type="text" id="shortNameLf" class="form-control form-control-sm" required disabled>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-4">
                            <div class="text-muted">Полное название<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup></div>
                        </div>
                        <div class="col-8">
                            <input type="text" id="fullNameLf" class="form-control form-control-sm" required disabled>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-6">
                            <div class="row">
                                <div class="col-4 d-flex align-items-center justify-content-center">
                                    <div class="text-muted">ОГРН<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup></div>
                                </div>
                                <div class="col-8">
                                    <input type="text" id="ogrn" class="form-control form-control-sm" required disabled>
                                </div>
                            </div>
                        </div>
                        <div class="col-6">
                            <div class="row">
                                <div class="col-4 d-flex align-items-center justify-content-center">
                                    <div class="text-muted">КПП<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup></div>
                                </div>
                                <div class="col-8">
                                    <input type="text" id="kpp" class="form-control form-control-sm" required disabled>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-4">
                            <div class="text-muted">Адрес<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup></div>
                        </div>
                        <div class="col-8">
                            <input type="text" id="address" class="form-control form-control-sm" required disabled>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-4">
                            <span class="text-muted">Должность<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup></span>
                        </div>
                        <div class="col-8">
                            <input type="text" id="positionManager" class="form-control form-control-sm" required disabled>
                        </div>
                    </div>
                    <div class="row mb-2">
                        <div class="col-4">
                            <span class="text-muted">ФИО<sup><i class="fas fa-star-of-life ml-1 text-danger"></i></sup></span>
                        </div>
                        <div class="col-8">
                            <input type="text" id="fioManager" class="form-control form-control-sm" required disabled>
                        </div>
                    </div>
                    <input type="hidden" id="shortName">
                    <input type="hidden" id="fullName">
                    <input type="hidden" id="shortLegalForm">
                    <input type="hidden" id="fullLegalForm">
                    <input type="hidden" id="normalizedName">
                </form>
            </div>
            <div class="btnBlock">
                <div class="d-flex align-items-center justify-content-center pb-2">
                    <a href=#" type="button" class="btn btn-md btn-primary rounded btnAddElement">Добавить</a>
                    <a href="#" type="button" class="btn btn-md btn-danger rounded" data-dismiss="modal">Отмена</a>
                </div>
            </div>
        </div>
    </div>
</div>