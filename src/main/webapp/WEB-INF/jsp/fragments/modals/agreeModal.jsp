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