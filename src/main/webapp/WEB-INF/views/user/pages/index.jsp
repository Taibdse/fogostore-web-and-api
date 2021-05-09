<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:wrapper>
    <div class="text small padding-topbottom-null">
        <div class="project-gallery">
            <c:forEach items="${website.homePageImages}" var="homePageImage">
                <div class="item">
                    <img class="img-responsive" src="${homePageImage}" alt="homePageImage">
                </div>
            </c:forEach>
        </div>
    </div>
    <div class="clearfix"></div>

    <div id="home-wrap" class="content-section" style="margin-top: 70px">
        <h3 class="text-center">Danh mục</h3>
        <div class="container" style="margin-bottom: 30px">
            <c:forEach items="${categoryMap}" var="categoryMapItem">
                <div class="col-lg-2 col-md-3 col-sm-4 col-xs-6 px-0">
                    <h4>
                        <a href="/danh-muc/${categoryMapItem.value.value.slug}">${categoryMapItem.value.value.name}</a>
                    </h4>
                    <ul class="categories-list">
                        <c:forEach items="${categoryMapItem.value.children}" var="childCate">
                            <li>
                                <a href="/danh-muc/${childCate.value.slug}">${childCate.value.name}</a>
                            </li>
                        </c:forEach>
                    </ul>
                </div>
            </c:forEach>
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
            <hr style="border: 2px solid #ccc"/>
        </div>

        <c:if test="${hotBlogs.size() > 0}">
            <h3 class="text-center">Bài viết hay</h3>
            <div class="container">
                <section id="news" class="page">
                    <div class="news-items equal four-columns">
                        <c:forEach items="${hotBlogs}" var="blog">
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
                        </c:forEach>
                    </div>
                </section>
            </div>
        </c:if>
    </div>
</layout:wrapper>