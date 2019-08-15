<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="modal fade" id="registrationSuccess" tabindex="-1" role="dialog" aria-labelledby="registrationSuccess"
     aria-hidden="true">
    <div class="modal-dialog modal-notify modal-success" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <p class="heading lead">Документ успешно зарегистрирован!</p>
                <button type="button" class="close reloadButton" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" class="white-text">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <div class="text-center">
                    <i class="fas fa-check fa-4x mb-3 animated rotateIn"></i>
                    <h6>Регистрационный номер:</h6>
                    <h3>согл-1/19</h3>
                </div>
            </div>
            <div class="modal-footer justify-content-center">
                <a type="button" class="btn btn-success rounded reloadButton" data-dismiss="modal">Продолжить работу</a>
            </div>
        </div>
    </div>
</div>