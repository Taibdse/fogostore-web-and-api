<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:wrapper>
    <div>
        <div class="row no-margin wrap-slider padding-leftright-null">
            <div class="col-md-6 padding-leftright-null">
                <div class="bg-img home error" style="background-image:url(/assets/img/404.jpg)"></div>
            </div>
            <div class="col-md-6 padding-leftright-null">
                <div id="home-slider" class="error secondary-background">
                    <div class="text">
                        <h1 class="white margin-bottom-extrasmall">404 Not Found<span>.</span></h1>
                        <p class="heading left grey-light margin-bottom">Trang này không tồn tại</p>
                        <a href="/trang-chu" class="btn-alt small white margin-null">Trang chủ</a>

                    </div>
                </div>
            </div>
        </div>
    </div>
</layout:wrapper>