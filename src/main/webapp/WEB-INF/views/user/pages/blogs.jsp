<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:wrapper>
    <layout:page-header title="Bài viết"/>
    <div id="home-wrap" class="content-section">
        <div class="container">
            <div class="row no-margin wrap-text">
                <section id="news" class="page">
                    <c:if test="${blogPage.content.size() == 0}">
                        <h3 class="text-center my-4">Chưa có bài viết nào!</h3>
                    </c:if>
                    <c:if test="${blogPage.content.size() > 0}">
                        <div class="news-items equal three-columns">
                            <c:forEach items="${blogPage.content}" var="blog">
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
                        <layout:pagination
                                totalPages="${blogPage.totalPages}"
                                current="${blogPage.pageable.pageNumber + 1}"/>
                    </c:if>
                </section>
            </div>
        </div>
    </div>
</layout:wrapper>

<script>
    $('nav ul.pagination li.page-item').on('click', function (e) {
        e.preventDefault();
        var page = $(this).data('page');
        var href = location.pathname + '?page=' + page;
        location.href = href;
    })
</script>