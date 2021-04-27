<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ attribute name="product" type="com.example.fogostore.dto.ProductDto" %>

<div class="single-item shop one-item design branding">
    <div class="item">
        <img src="assets/img/shop1.jpg" alt="">
        <div class="content">
<%--            <i class="icon ion-ios-cart-outline"></i>--%>
            <div>
                <h3>MacBook Pro 2020 13-inch (Silver)</h3>
                <span class="price">29.500.000 vnd</span>
            </div>
            <p>4GHz quad-core Intel Core i5
                8GB 2133MHz memory
                256GB flash storage
            </p>
        </div>
        <a href="single-product.html" class="link"></a>
    </div>
</div>

<%--<c:if test="${product != null}">--%>
<%--    <div class="product-wrapper"--%>
<%--         data-id="${product.id}"--%>
<%--         data-price="${product.price}"--%>
<%--         data-name="${product.name}"--%>
<%--         data-image="${product.mainImage}"--%>
<%--         data-slug="${product.slug}"--%>
<%--         data-type-id="${product.type != null ? product.type.id : null}"--%>
<%--         data-type-name="${product.type != null ? product.type.name : ""}"--%>
<%--    >--%>
<%--        <div class="product-img">--%>
<%--            <a href="/san-pham/${product.slug}">--%>
<%--                <img class="watermark-logo-image" src="${product.mainImage}" alt="product.mainImage">--%>
<%--            </a>--%>

<%--            <div class="p-2 product-content">--%>
<%--                <h5 class="">--%>
<%--                    <a class="" href="/san-pham/${product.slug}">--%>
<%--                            ${product.name}--%>
<%--                    </a>--%>
<%--                </h5>--%>
<%--                <div class="product-price mt-1 float-left">--%>
<%--                    <span class="text-main d-block">--%>
<%--                        <layout:money price="${product.price}"/>--%>
<%--                    </span>--%>
<%--                </div>--%>
<%--                <div class="product-action float-right mt-2">--%>
<%--                    <a class="action-plus-2 add-to-cart bg-main" title="Thêm vào giỏ hàng" href="#">--%>
<%--                        <i class="fas fa-cart-plus text-white "></i>--%>
<%--                    </a>--%>
<%--                </div>--%>
<%--                <div class="clearfix"></div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</c:if>--%>
