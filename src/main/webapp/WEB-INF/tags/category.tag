<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="category" type="com.example.fogostore.model.Category" %>


<c:if test="${category != null}">
    <div class="product-wrapper mb-30">
        <div class="product-img">
            <a href="/danh-muc/${category.slug}">
                <img class="watermark-logo-image" src="${category.image}" alt="${category.name}">
            </a>

            <div class="p-3">
                <h5 class="">
                    ${category.name}
                </h5>
            </div>
        </div>
    </div>
</c:if>
