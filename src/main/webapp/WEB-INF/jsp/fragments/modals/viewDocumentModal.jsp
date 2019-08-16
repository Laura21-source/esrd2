<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal fade" id="btnCansel" tabindex="-1" role="dialog" aria-labelledby="btnCansel"
     aria-hidden="true">
    <div class="modal-dialog modal-notify modal-danger" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <p class="heading lead">Отмена исполения!</p>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="white-text">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="text-center">
                    <i class="fas fa-times fa-4x mb-3 animated rotateIn"></i>
                    <h6>Исполнение документа прекращено!</h6>
                </div>
            </div>
            <div class="modal-footer justify-content-center">
                <a href="agree-document" type="button" class="btn btn-danger rounded">Продолжить работу</a>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="btnSuccess" tabindex="-1" role="dialog" aria-labelledby="btnSuccess"
     aria-hidden="true">
    <div class="modal-dialog modal-notify modal-success" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <p class="heading lead">Документ успешно отредактирован!</p>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="white-text">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="text-center">
                    <i class="fas fa-check fa-4x mb-3 animated rotateIn"></i>
                    <h6>Сохранение успешно завершено!</h6>
                </div>
            </div>
            <div class="modal-footer justify-content-center">
                <a type="button" class="btn btn-success rounded" data-dismiss="modal">Продолжить работу</a>
            </div>
        </div>
    </div>
</div>