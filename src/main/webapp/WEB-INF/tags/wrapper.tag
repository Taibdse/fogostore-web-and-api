<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="hideHeaderBanner" type="java.lang.Boolean" required="false" %>

<!DOCTYPE html>
<html lang="vi">
<c:import url="../inc/head.jsp"/>
<body>
<c:import url="../inc/loader.jsp"/>
<!--  Main Wrap  -->
<div id="main-wrap">
    <c:import url="../inc/header.jsp"/>
    <c:if test="${!hideHeaderBanner}" >
         <c:import url="../inc/banner.jsp"/>
    </c:if>


    <!--  Page Content  -->
    <div id="page-content">
        <jsp:doBody/>
    </div>
    <c:import url="../inc/tel.jsp"/>
    <!--  END Page Content -->
</div>
<c:import url="../inc/footer.jsp"/>
<c:import url="../inc/scripts.jsp"/>
</body>
</html>