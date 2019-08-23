<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal fade right" id="listAgree" tabindex="-1" role="dialog" aria-labelledby="listAgree"
     aria-hidden="true" data-backdrop="false">
    <div class="modal-dialog modal-side modal-top-right modal-right" role="document">
        <div class="modal-content">
            <div class="modal-header alert alert-secondary">
                <h6>Список согласования</h6>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="t" rue="">×</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="row mb-3">
                    <div class="col-8">Иванов И.А.</div>
                    <div class="col-4"><i class="fas fa-check text-success"></i></div>
                </div>
                <div class="row mb-3">
                    <div class="col-8">Сидоров А.В.</div>
                    <div class="col-4"><i class="fas fa-check text-success"></i></div>
                </div>
                <div class="row mb-3">
                    <div class="col-8">Петров И.Г.</div>
                    <div class="col-4"><i class="fas fa-user-clock text-warning"></i></div>
                </div>
                <div class="row mb-3">
                    <div class="col-8">Минин В.В.</div>
                    <div class="col-4"><i class="fas fa-ellipsis-h text-muted"></i></div>
                </div>
            </div>
            <div class="modal-footer text-center">
                <button type="button" class="btn btn-primary btn-sm rounded" data-dismiss="modal">Закрыть</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="btnSuccess" tabindex="-1" role="dialog" aria-labelledby="btnSuccess"
     aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered modal-notify modal-success" role="document">
        <div class="modal-content blockSuccess d-none">
            <div class="modal-header">
                <p class="heading lead">Документ успешно согласован!</p>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="white-text">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="text-center">
                    <i class="fas fa-check fa-4x mb-3 animated rotateIn"></i>
                    <h6>Согласование успешно завершено!</h6>
                </div>
            </div>
            <div class="modal-footer justify-content-center">
                <a type="button" class="btn btn-success rounded" data-dismiss="modal">Продолжить работу</a>
            </div>
        </div>
        <div class="modal-content">
            <div class="modal-body d-flex align-items-center justify-content-center">
                <div class="container-fluid h-100vh">
                    <div class="row h-100">
                        <div class="w-100 h-100 m-auto flex-center">
                            <div class="spinner-border circle-radius-205 p-absolute" role="status">
                                <span class="sr-only">Loading...</span>
                            </div>
                            <div class="card circle circle-radius-200">
                                <div class="card-body flex-center">
                                    <img id="logo" src="https://mdbootstrap.com/img/Marketing/other/logo/logo-mdb-vue-small.png" alt="MDB Logo">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>