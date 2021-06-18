<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:wrapper>
<%--    <layout:page-header title="Chi tiết bài viết" />--%>
    <div id="home-wrap" class="content-section">
        <div class="container" style="padding-top: 30px; padding-bottom: 60px">
            <c:if test="${blog != null}">
                <div class="row">
                    <h3 class="text-center" style="margin-top: 20px; margin-bottom: 20px">${blog.title}</h3>
                    <div class="col-md-8 mx-auto float-none">
                        <div class="editor-content">${blog.content}</div>
                    </div>
                </div>
            </c:if>

            <c:if test="${blog == null}">
                <h4 class="text-center">NOT FOUND</h4>
            </c:if>

        </div>
    </div>
</layout:wrapper>