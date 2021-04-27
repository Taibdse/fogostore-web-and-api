<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="onMenu" type="java.lang.Boolean" %>

<c:if test="${onMenu}">
    <nav id="mobile-menu-active">
        <ul class="menu-overflow">
            <li><a href="/trang-chu">Trang chủ</a></li>
            <li><a href="/ve-chung-toi">Giới thiệu</a></li>
            <li><a href="/hang-xe">Hãng xe</a>
                <ul class="fman-sub-menu">
                    <layout:brand-tree brandMap="${brandMap}" onMenu="${onMenu}"/>
                </ul>
            </li>
            <li><a href="/danh-muc">Danh mục</a>
                <ul class="fman-sub-menu">
                    <layout:category-tree categoryMap="${categoryMap}" onMenu="${onMenu}"/>
                </ul>
            </li>
            <li><a href="/bai-viet">Tin tức</a></li>
            <li><a href="/khuyen-mai">Khuyến mãi</a></li>
            <li><a href="/gio-hang">Giỏ hàng</a></li>
        </ul>
    </nav>
</c:if>

<c:if test="${!onMenu}">
    <nav class="text-center">
        <ul class="menu-overflow">
            <li><a href="/trang-chu">Trang chủ</a></li>
            <li><a href="/ve-chung-toi">Giới thiệu</a></li>
            <li><a href="/hang-xe">Hãng xe</a>
                <ul class="fman-sub-menu">
                    <layout:brand-tree brandMap="${brandMap}" onMenu="${onMenu}"/>
                </ul>
            </li>
            <li><a href="/danh-muc">Danh mục</a>
                <ul class="fman-sub-menu">
                    <layout:category-tree categoryMap="${categoryMap}" onMenu="${onMenu}" />
                </ul>
            </li>
            <li><a href="/bai-viet">Tin tức</a></li>
            <li><a href="/khuyen-mai">Khuyến mãi</a></li>
            <li><a href="/gio-hang">Giỏ hàng</a></li>
        </ul>
    </nav>
</c:if>