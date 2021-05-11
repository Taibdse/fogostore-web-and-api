<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ attribute name="product" type="com.example.fogostore.dto.product.ProductDto" %>

<div class="single-item shop one-item design branding">
    <div class="item">
        <img src="${product.mainImage}" alt="${product.name}">
        <div class="content">
            <div style="margin-bottom: 10px" class="text-center">
                <h5 class="font-weight-bold" style="font-size: 0.9em">${product.name}</h5>
                <c:if test="${product.productTypes.size() == 0}">
                    <div class="text-center" style="font-size: 0.9em; margin-top: 10px"><layout:money price="${product.price}"/></div>
                </c:if>
                <br/>
                <c:forEach items="${product.productTypes}" var="productType">
                    <div class="text-center d-block" style="font-size: 0.9em">
                        <layout:money price="${productType.price}"/>
                        <span style="margin-left: 10px" class="d-inline-block">${productType.name}</span>
                    </div>
                </c:forEach>
            </div>
        </div>
        <a href="/san-pham/${product.slug}" class="link"></a>
    </div>
</div>