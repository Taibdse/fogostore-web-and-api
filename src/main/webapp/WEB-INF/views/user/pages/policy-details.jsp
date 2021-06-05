<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:wrapper>
<%--    <layout:page-header title="${policy.name}" />--%>
    <div id="home-wrap" class="content-section">
        <c:if test="${policy != null}">
            <div class="container" style="margin-top: 30px; margin-bottom: 30px">
                <h3 class="text-center">${policy.name}</h3>
                <div class="row">
                    <div class="col-md-8 mx-auto float-none">
                        <div>${policy.content}</div>
                    </div>
                </div>
            </div>
        </c:if>
        <c:if test="${policy == null}">
            <h3 class="text-center">NOT FOUND</h3>
        </c:if>
    </div>
</layout:wrapper>