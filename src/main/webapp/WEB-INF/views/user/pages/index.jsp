<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:wrapper>
    <div id="home-wrap" class="content-section" style="margin-top: 70px">
        <div class="container" style="margin-bottom: 50px; margin-top: 70px">
            <section id="projects" data-isotope="load-simple" class="page padding-top-null padding-onlybottom-lg">
                <div class="projects-items equal three-columns">
                    <c:forEach items="${categoryMap}" var="categoryMapItem">
                        <a href="/danh-muc/${categoryMapItem.value.value.slug}">
                            <div class="single-item shop one-item design branding">
                                <div class="item">
                                    <img src="${categoryMapItem.value.value.image}" alt="${categoryMapItem.value.value.name}">
                                    <div class="content">
                                        <div style="margin-bottom: 10px" class="text-center">
                                            <h5 class="font-weight-bold"
                                                style="font-size: 0.9em">${categoryMapItem.value.value.name}</h5>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </c:forEach>
                </div>
            </section>

            <div class="clearfix"></div>
            <hr style="border: 2px solid #ccc"/>
        </div>

        <h3 class="text-center">Sản phẩm nổi bật</h3>
        <div class="container">
            <section id="projects" data-isotope="load-simple" class="page padding-top-null padding-onlybottom-lg">
                <div class="projects-items equal four-columns">
                    <c:forEach items="${hotProducts}" var="hotProduct">
                        <layout:product product="${hotProduct}"/>
                    </c:forEach>
                </div>
            </section>
            <c:if test="${hotBlogs.size() > 0}">
                <hr style="border: 2px solid #ccc"/>
            </c:if>
        </div>

        <c:if test="${hotBlogs.size() > 0}">
            <h3 class="text-center">Bài viết hay</h3>
            <div class="container">
                <section id="news" class="page">
                    <div class="three-item-gallery">
                        <c:forEach items="${hotBlogs}" var="blog">
                            <div class="item">
                                <div class="single-news one-item">
                                    <a href="/bai-viet/${blog.slug}">
                                        <article>
                                            <img src="${blog.image}" alt="${blog.title}">
                                            <div class="content">
                                                <h3>${blog.title}</h3>
                                                <p>${blog.shortDescription}</p>
                                            </div>
                                        </article>
                                    </a>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </section>
            </div>
        </c:if>
    </div>
</layout:wrapper>