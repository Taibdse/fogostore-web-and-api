<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<!-- Header & Menu -->
<header id="header" class="border">
    <div class="container">
        <nav class="navbar navbar-default">
            <!--  Header Logo  -->
            <div id="logo">
                <a class="navbar-brand" href="index.jsp">
                    <img src="assets/img/logo.png" class="normal" alt="logo">
                    <img src="assets/img/logo@2x.png" class="retina" alt="logo">
                </a>
            </div>
            <!--  END Header Logo  -->
            <!--  Menu  -->
            <div id="sidemenu">
                <div class="menu-holder">
                    <ul>
                        <li>
                            <a href="/trang-chu">Trang chủ</a>
                        </li>
                        <li class="submenu">
                            <a href="javascript:void(0)" class="active-item">Sản phẩm</a>
                            <ul class="sub-menu">
                                <li>
                                    <a href="javascript:void(0)">Accessories</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0)">Accessories</a>
                                </li>
                                <li>
                                    <a href="javascript:void(0)">Accessories</a>
                                </li>
                            </ul>
                        </li>
                        <li>
                            <a href="/ve-chung-toi">Về chúng tôi</a>
                        </li>
                        <li>
                            <a href="/lien-he">Liên hệ</a>
                        </li>
                        <%--                        <li class="submenu">--%>
                        <%--                            <a href="javascript:void(0)" class="active-item">Home</a>--%>
                        <%--                            <ul class="sub-menu">--%>
                        <%--                                <li><a href="index.jsp">Home Minimal</a></li>--%>
                        <%--                                <li><a href="index-creative.jsp">Home Creative</a></li>--%>
                        <%--                                <li><a href="index-agency.jsp">Home Agency</a></li>--%>
                        <%--                                <li><a href="index-shop.jsp">Home Shop</a></li>--%>
                        <%--                            </ul>--%>
                        <%--                        </li>--%>
                        <%--                        <li class="submenu">--%>
                        <%--                            <a href="javascript:void(0)">Pages</a>--%>
                        <%--                            <ul class="sub-menu">--%>
                        <%--                                <li><a href="about.jsp">About</a></li>--%>
                        <%--                                <li><a href="page-image-header.jsp">Page Image Header</a></li>--%>
                        <%--                                <li><a href="services.jsp">Services</a></li>--%>
                        <%--                                <li><a href="404.html">404</a></li>--%>
                        <%--                            </ul>--%>
                        <%--                        </li>--%>
                        <%--                        <li class="submenu">--%>
                        <%--                            <a href="javascript:void(0)">Portfolio</a>--%>
                        <%--                            <ul class="sub-menu">--%>
                        <%--                                <li><a href="portfolio-two-columns.jsp">Two Columns</a></li>--%>
                        <%--                                <li><a href="portfolio-three-columns.jsp">Three Columns</a></li>--%>
                        <%--                                <li><a href="portfolio-four-columns.jsp">Four Columns</a></li>--%>
                        <%--                                <li><a href="project-1.jsp">Project 1</a></li>--%>
                        <%--                                <li><a href="project-2.jsp">Project 2</a></li>--%>
                        <%--                            </ul>--%>
                        <%--                        </li>--%>
                        <%--                        <li class="submenu">--%>
                        <%--                            <a href="javascript:void(0)">Journal</a>--%>
                        <%--                            <ul class="sub-menu">--%>
                        <%--                                <li><a href="blog.jsp">Journal Classic</a></li>--%>
                        <%--                                <li><a href="blog-minimal.jsp">Journal Minimal</a></li>--%>
                        <%--                                <li><a href="single-post.jsp">Post</a></li>--%>
                        <%--                                <li><a href="single-post-slider.jsp">Post Slider</a></li>--%>
                        <%--                                <li><a href="single-post-video.jsp">Post Video</a></li>--%>
                        <%--                            </ul>--%>
                        <%--                        </li>--%>
                        <%--                        <li class="submenu">--%>
                        <%--                            <a href="javascript:void(0)">Shop</a>--%>
                        <%--                            <ul class="sub-menu">--%>
                        <%--                                <li><a href="index-shop.jsp">Index Shop</a></li>--%>
                        <%--                                <li><a href="single-product.jsp">Product</a></li>--%>
                        <%--                            </ul>--%>
                        <%--                        </li>--%>
                        <%--                        <li>--%>
                        <%--                            <a href="elements.jsp">Elements</a>--%>
                        <%--                        </li>--%>
                        <%--                        <li>--%>
                        <%--                            <a href="contacts.jsp">Contacts</a>--%>
                        <%--                        </li>--%>
                        <!-- Lang -->
                        <%--                        <li class="lang">--%>
                        <%--                            <span class="current"><a href="#">en</a></span>--%>
                        <%--                            <ul>--%>
                        <%--                                <li><a href="#">it</a></li>--%>
                        <%--                            </ul>--%>
                        <%--                        </li>--%>
                    </ul>
                </div>
            </div>
            <!--  END Menu  -->
            <!--  Button for Responsive Menu  -->
            <div id="menu-responsive-sidemenu">
                <div class="menu-button">
                    <span class="bar bar-1"></span>
                    <span class="bar bar-2"></span>
                    <span class="bar bar-3"></span>
                </div>
            </div>
            <!--  END Button for Responsive Menu  -->
        </nav>
    </div>
</header>
<!-- END Header & Menu -->