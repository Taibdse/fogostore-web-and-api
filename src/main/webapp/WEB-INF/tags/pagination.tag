<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ attribute name="totalPages" type="java.lang.Integer" %>
<%@ attribute name="current" type="java.lang.Integer" %>

<c:if test="${totalPages > 0}">
    <nav aria-label="Page navigation example">
        <ul class="pagination">
            <c:if test="${current != 1}">
                <li class="page-item" data-page="${current - 1}">
                    <a class="page-link" href="#">
                        <i class="fa fa-angle-left"></i>
                    </a>
                </li>
            </c:if>
            <c:forEach begin="1" end="${totalPages}" varStatus="status">
                <li data-page="${status.count}" class="page-item ${current == status.count ? "active" : ""}">
                    <a href="#">${status.count}</a>
                </li>
            </c:forEach>
            <c:if test="${current != totalPages}">
                <li class="page-item" data-page="${current + 1}">
                    <a class="page-link" href="#">
                        <i class="fa fa-angle-right"></i>
                    </a>
                </li>
            </c:if>
        </ul>
    </nav>
</c:if>
