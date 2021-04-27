<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<%@ attribute name="totalPages" type="java.lang.Integer" %>
<%@ attribute name="current" type="java.lang.Integer" %>

<c:if test="${totalPages > 0}">
    <div class="paginations text-center mt-20" data-current="${current}">
        <ul>
            <c:if test="${current != 1}">
                <li data-page="${current - 1}" class="page-item"><a href="#"><i class="fa fa-angle-left"></i></a></li>
            </c:if>
            <c:forEach begin="1" end="${totalPages}" varStatus="status">
                <li data-page="${status.count}" class="page-item ${current == status.count ? "active" : ""}"><a href="#">${status.count}</a></li>
            </c:forEach>
            <c:if test="${current != totalPages}">
                <li data-page="${current + 1}" class="page-item" ><a href="#"><i class="fa fa-angle-right"></i></a></li>
            </c:if>
        </ul>
    </div>
</c:if>
