<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:wrapper>
<%--    <layout:page-header title="Về chúng tôi" />--%>
    <div id="home-wrap" class="content-section">
        <div class="container" style="margin-top: 30px; margin-bottom: 30px">
            <div class="row">
                <div class="col-md-8 mx-auto float-none">
                    <div>${shop.aboutUsContent}</div>
                </div>
            </div>
        </div>
    </div>
</layout:wrapper>