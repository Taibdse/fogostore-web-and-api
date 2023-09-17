<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ attribute name="product" type="com.example.fogostore.dto.product.ProductDto" %>
<%@ attribute name="carouselLazyLoad" type="java.lang.Boolean" required="false" %>
<%@ attribute name="lazyLoadWithLazySizes" type="java.lang.Boolean" required="false" %>
<div 
    class="single-item shop one-item design branding product-card"
    data-id="${product.id}"
    data-price="${product.price}"
    data-name="${product.name}"
    data-image="${product.mainImage}"
    data-slug="${product.slug}"
    data-type-id="${product.type != null ? product.type.id : null}"
    data-type-name="${product.type != null ? product.type.name : ""}"
    data-available="${product.available ? 1 : 0}">
    <div class="item" style="margin: 0">
         <c:choose>
            <c:when test="${!carouselLazyLoad && !lazyLoadWithLazySizes}">
                <img
                    src="${product.mainImage}"
                    alt="${product.mainImage}">
            </c:when>
            <c:when test="${carouselLazyLoad}">
                <img
                    class="owl-lazy"
                    data-src="${product.mainImage}"
                    alt="${product.mainImage}">
            </c:when>
            <c:when test="${lazyLoadWithLazySizes}">
                <img
                    class="lazyload blur-up"
                    data-src="${product.mainImage}"
                    alt="${product.mainImage}">
            </c:when>
        </c:choose>
        <div class="content" style="margin-bottom: 10px">
            <i class="icon ion-ios-cart-outline add-to-cart"></i>
            <h5 class="font-weight-bold" style="font-size: 0.9em">${product.name}</h5>
            <c:if test="${product.productTypes.size() == 0}">
                <div class="price-block">
                    <div class="price"><layout:money price="${product.price}"/></div>
                    <c:if test="${product.oldPrice != null && product.oldPrice > 0}">
                        <div class="old-price"><layout:money price="${product.oldPrice}"/></div>
                    </c:if>
                </div>
            </c:if>
            <br/>
            <div class="product-type-block">
                <c:forEach items="${product.productTypes}" var="productType">
                    <div class="item">
                        <layout:money price="${productType.price}"/>
                        <span style="margin-left: 10px" class="d-inline-block">${productType.name}</span>
                    </div>
                </c:forEach>
            </div>
        </div>
        <a href="/san-pham/${product.slug}" class="link"></a>
    </div>
</div>