<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="title" type="java.lang.String" %>

<div class="breadcrumb-area p-170" style="background-image: url(${website.subPageImage})">
    <div class="container-fluid">
        <div class="breadcrumb-content text-center">
            <h2>${title}</h2>
            <ul>
                <li>
                    <a href="/trang-chu">Trang chủ</a>
                </li>
                <li>${title}</li>
            </ul>
        </div>
    </div>
</div>



