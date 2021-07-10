<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ attribute name="position" type="com.example.fogostore.common.enumeration.SocialPluginPosition" %>
<c:forEach items="${socialPlugins}" var="socialPlugin">
    <c:if test="${socialPlugin.position == position}">
        ${socialPlugin.code}
    </c:if>
</c:forEach>
