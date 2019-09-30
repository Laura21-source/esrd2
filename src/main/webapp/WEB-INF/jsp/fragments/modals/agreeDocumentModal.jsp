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
        <div class="modal-content btnSuccess">
            <div class="modal-header headerSuccess d-none">
                <p class="heading lead">Согласование документа</p>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="white-text">&times;</span>
                </button>
            </div>
            <div class="modal-body d-flex align-items-center justify-content-center">
                <div class="preloader-wrapper active big active crazy loaderSuccess">
                    <div class="spinner-layer spinner-blue">
                        <div class="circle-clipper left">
                            <div class="circle"></div>
                        </div>
                        <div class="gap-patch">
                            <div class="circle"></div>
                        </div>
                        <div class="circle-clipper right">
                            <div class="circle"></div>
                        </div>
                    </div>
                    <div class="spinner-layer spinner-red">
                        <div class="circle-clipper left">
                            <div class="circle"></div>
                        </div>
                        <div class="gap-patch">
                            <div class="circle"></div>
                        </div>
                        <div class="circle-clipper right">
                            <div class="circle"></div>
                        </div>
                    </div>
                    <div class="spinner-layer spinner-yellow">
                        <div class="circle-clipper left">
                            <div class="circle"></div>
                        </div>
                        <div class="gap-patch">
                            <div class="circle"></div>
                        </div>
                        <div class="circle-clipper right">
                            <div class="circle"></div>
                        </div>
                    </div>
                    <div class="spinner-layer spinner-green">
                        <div class="circle-clipper left">
                            <div class="circle"></div>
                        </div>
                        <div class="gap-patch">
                            <div class="circle"></div>
                        </div>
                        <div class="circle-clipper right">
                            <div class="circle"></div>
                        </div>
                    </div>
                </div>
                <div class="text-center bodySuccess d-none">
                    <i class="fas fa-check fa-4x mb-3 animated rotateIn"></i>
                    <h6>Документ успешно согласован!</h6>
                </div>
            </div>
            <div class="modal-footer justify-content-center footerSuccess d-none">
                <a type="button" class="btn btn-success rounded" data-dismiss="modal">Продолжить работу</a>
            </div>
        </div>
    </div>
</div>