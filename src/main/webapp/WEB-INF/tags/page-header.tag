<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="title" type="java.lang.String" %>
<style>
    .page-header {
        background-repeat: no-repeat;
    }
</style>
<div class="padding-leftright-null">
    <div id="page-header" style="background-image: url('${website.subPageImage}')">
        <div class="text">
            <h1 class="margin-bottom-small" style="color: #fff">${title}</h1>
        </div>
    </div>
</div>