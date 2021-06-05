<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<div class="text small padding-topbottom-null">
    <div class="project-gallery">
        <c:forEach items="${website.homePageImages}" var="homePageImage">
            <div class="item">
                <img class="img-responsive" src="${homePageImage}" alt="homePageImage">
            </div>
        </c:forEach>
    </div>
</div>
<div class="clearfix" style="margin-bottom: 30px"></div>