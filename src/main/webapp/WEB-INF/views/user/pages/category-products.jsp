<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:wrapper>
    <layout:page-header title="Sản phẩm theo danh mục"/>
    <div id="home-wrap" class="content-section">
        <div class="container-fluid" style="padding-top: 30px; padding-bottom: 60px">
            <c:if test="${category != null}">
                <h3 class="text-center">${category.name}</h3>
                <section id="projects" data-isotope="load-simple" class="page padding-top-null padding-onlybottom-lg">
                    <div class="row">
                        <div class="col-md-3">
                            <h4 class="sidebar-widget" style="border-bottom: 1px solid #ccc">Danh mục
                                <button class="btn d-lg-none float-right" data-target="#categoriesSidebar"
                                        data-toggle="collapse" aria-expanded="true"
                                        aria-controls="collapseExample">
                                    <i class="fa fa-bars" style="font-size: 1.6em"></i>
                                </button>
                                <div class="clearfix"></div>
                            </h4>
                            <div class="widget-categories collapse d-lg-block" id="categoriesSidebar">
                                <ul class="categories-list">
                                    <c:forEach items="${categoryMap}" var="category">
                                        <li>
                                            <a class="d-inline-block"
                                               href="/danh-muc/${category.value.value.slug}">${category.value.value.name}</a>
                                            <c:if test="${category.value.children != null && category.value.children.size() > 0}">
                                        <span class="float-right" data-toggle="collapse"
                                              data-target="#category${category.key}"
                                              style="font-size: 15px; cursor: pointer">
                                            <i class="fa fa-plus"></i>
                                        </span>
                                                <ul class="collapse" id="category${category.key}"
                                                    style="padding-left: 10px">
                                                    <c:forEach items="${category.value.children}" var="child">
                                                        <li>
                                                            <a class="d-inline-block"
                                                               href="/danh-muc/${child.value.slug}">${child.value.name}</a>
                                                        </li>
                                                        <c:if test="${child.children != null && child.children.size() > 0}">
                                                    <span class="float-right" data-toggle="collapse"
                                                          data-target="#category${category.key}"
                                                          style="font-size: 15px; cursor: pointer">
                                                        <i class="fa fa-plus"></i>
                                                    </span>
                                                            <ul class="collapse" id="category${category.key}"
                                                                style="padding-left: 20px">
                                                                <c:forEach items="${child.children}"
                                                                           var="grandchild">
                                                                    <a class="d-inline-block"
                                                                       href="/danh-muc/${grandchild.value.slug}">${grandchild.value.name}</a>
                                                                </c:forEach>
                                                            </ul>
                                                        </c:if>
                                                    </c:forEach>
                                                </ul>
                                            </c:if>
                                        </li>
                                    </c:forEach>
                                </ul>
                            </div>
                        </div>
                        <div class="col-md-9">
                            <div class="projects-items equal three-columns">
                                <c:forEach items="${productPage.content}" var="product">
                                    <layout:product product="${product}"/>
                                </c:forEach>
                            </div>
                            <layout:pagination
                                    totalPages="${productPage.totalPages}"
                                    current="${productPage.pageable.pageNumber + 1}"/>
                        </div>
                    </div>
                </section>
            </c:if>
            <c:if test="${category == null}">
                <h4 class="text-center">Không tìm thấy danh mục này</h4>
            </c:if>
        </div>
    </div>
</layout:wrapper>

<script>
    $('nav ul.pagination li.page-item').on('click', function (e) {
        e.preventDefault();
        var page = $(this).data('page');
        var href = location.pathname + '?page=' + page;
        location.href = href;
    });

    setActiveSidebarLinks();

    function setActiveSidebarLinks() {
        for (var i = 0; i < $('#categoriesSidebar a').length; i++) {
            var href = $('#categoriesSidebar a').eq(i).attr("href").trim();
            if (href === location.pathname) {
                var $li = $('#categoriesSidebar a').eq(i).parent('li');
                $li.addClass('active');
                $li.parent('ul.collapse').collapse('show');
                return;
            }
        }
    }


</script>