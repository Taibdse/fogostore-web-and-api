<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<style>

</style>
<header id="header" class="border">
    <div class="container">
        <nav class="navbar navbar-default">
            <div id="logo">
                <a class="navbar-brand" href="/">
                    <img src="${website.logoWebsiteImage}" class="normal" alt="logo">
                    <img src="${website.logoWebsiteImage}" class="retina" alt="logo">
                </a>
            </div>
            <div id="sidemenu">
                <div class="menu-holder">
                    <ul>
                        <li>
                            <a href="/trang-chu">Trang chủ</a>
                        </li>
                        <li>
                            <a href="/ve-chung-toi">Giới thiệu</a>
                        </li>
                        <c:forEach items="${categoryMap}" var="categoryNode">
                            <li class="submenu">
                                <a href="/danh-muc/${categoryNode.value.value.slug}">${categoryNode.value.value.name}</a>
                                <c:if test="${categoryNode.value.children.size() > 0}">
                                    <ul class="sub-menu">
                                        <c:forEach items="${categoryNode.value.children}" var="childCategoryNode">
                                            <li>
                                                <a href="/danh-muc/${childCategoryNode.value.slug}">${childCategoryNode.value.name}</a>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                            </li>
                        </c:forEach>
                        <li>
                            <a href="/bai-viet">Bài viết</a>
                        </li>
                    </ul>
                </div>
            </div>
            <div id="menu-responsive-sidemenu">
                <div class="menu-button">
                    <span class="bar bar-1"></span>
                    <span class="bar bar-2"></span>
                    <span class="bar bar-3"></span>
                </div>
            </div>
        </nav>
        <div id="search-product-input-group" class="input-group bg-danger float-right"
             style="max-width: 400px; min-width: 250px">
            <input type="text" class="form-control" placeholder="Nhập tên sản phẩm" aria-describedby="basic-addon2"
                   id="search-product-input">
<%--            <span class="input-group-addon" id="basic-addon2">--%>
<%--                <i class="fa fa-search"></i>--%>
<%--            </span>--%>
        </div>
    </div>
</header>