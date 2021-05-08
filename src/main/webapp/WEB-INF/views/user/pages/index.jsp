<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:wrapper>
    <div class="text small padding-topbottom-null">
        <div class="project-gallery">
            <c:forEach items="${website.homePageImages}" var="homePageImage">
                <div class="item">
                    <img class="img-responsive" src="${homePageImage}" alt="">
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="clearfix"></div>
    <div id="home-wrap" class="content-section" style="margin-top: 70px">
        <h3 class="text-center">Sản phẩm nổi bật</h3>
        <div class="container">
            <section id="projects" data-isotope="load-simple" class="page padding-top-null padding-onlybottom-lg">
                <div class="projects-items equal four-columns">
                    <c:forEach items="${hotProducts}" var="hotProduct">
                        <layout:product product="${hotProduct}"/>
                    </c:forEach>
                </div>
            </section>
        </div>
    </div>
</layout:wrapper>