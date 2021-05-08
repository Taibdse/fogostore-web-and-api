<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

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
                        <li class="submenu">
                            <a href="javascript:void(0)">Sản phẩm</a>
                            <ul class="sub-menu">
                                <c:forEach items="${categoryMap}" var="categoryNode">
                                    <li>
                                        <a href="/danh-muc/${categoryNode.value.value.slug}">${categoryNode.value.value.name}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0)">Chính sách</a>
                            <ul class="sub-menu">
                                <c:forEach items="${policies}" var="policy">
                                    <li>
                                        <a href="/chinh-sach/${policy.slug}">${policy.name}</a>
                                    </li>
                                </c:forEach>
                            </ul>
                        </li>
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
    </div>
</header>
