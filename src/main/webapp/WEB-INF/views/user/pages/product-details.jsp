<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:wrapper hideHeaderBanner="true">
    <style>
        .price-block > .current-price {
            font-weight: bold;
            color: var(--main-color);
            font-size: 2em;
        }

        .price-block > .old-price {
            font-size: 1.5em;
            text-decoration: line-through;
            font-style: italic;
            margin-left: 10px;
        }
    </style>

    <div id="home-wrap" class="content-section" style="padding-top: 0px;">
        <c:if test="${product != null}">
        <div data-id="${product.id}"
             data-price="${product.price}"
             data-name="${product.name}"
             data-image="${product.mainImage}"
             data-slug="${product.slug}"
             data-type-id="${product.type != null ? product.type.id : null}"
             data-type-name="${product.type != null ? product.type.name : ""}"
             class="d-none"
             id="productDetails"></div>
            <div class="container">
                <div class="row no-margin wrap-text">
                    <div class="col-md-6 padding-leftright-null">
                        <div class="text small padding-top-null">
                            <img src="${product.mainImage}" alt="${product.name}" class="img-post">
                        </div>
                        <div class="row no-margin">
                            <div class="project-images grid text">
                                <div class="col-xs-6 col-sm-3 padding-leftright-null">
                                    <a href="${product.mainImage}" class="lightbox">
                                        <div class="image" style="background-image:url(${product.mainImage})"></div>
                                    </a>
                                </div>
                                <c:forEach items="${product.subImages}" var="subImage">
                                    <div class="col-xs-6 col-sm-3 padding-leftright-null">
                                        <a href="${subImage}" class="lightbox">
                                            <div class="image" style="background-image:url(${subImage})"></div>
                                        </a>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 padding-leftright-null">
                        <div class="row no-margin">
                            <div id="product-header">
                                <div class="text small padding-top-null padding-md-bottom">
                                    <h1 class="product-name">${product.name}</h1>
                                    <div class="price-block">
                                        <span id="product-price" class="current-price"><layout:money price="${product.price}"/></span>
                                        <c:if test="${product.oldPrice != null && product.oldPrice > 0}">
                                            <span class="old-price" id="product-old-price"><layout:money price="${product.oldPrice}"/></span>
                                        </c:if>
                                    </div>
                                    <c:forEach items="${product.productTypes}" var="productType" varStatus="status">
                                        <div 
                                            style="font-size: 1.1em" 
                                            data-product-type-name="${productType.name}"
                                            data-product-type-price="${productType.price}"
                                            data-product-type-old-price="${productType.oldPrice}"
                                            data-product-type-display-price="<layout:money price="${productType.price}" />"
                                            data-product-type-display-old-price="<layout:money price="${productType.oldPrice}" />"
                                            data-product-type-id="${productType.id}"
                                            class="select-product-type btn-select mb-3 ${status.count == 1 ? "active" : ""}"
                                        >
                                            <layout:money price="${productType.price}"/>
                                            <span style="margin-left: 10px"
                                                  class="d-inline-block">${productType.name}</span>
                                        </div>
                                    </c:forEach>
                                    <div class="add-to-cart" style="margin-top: 15px">
                                       <input id="productQuantity" type="number" step="1" min="1" max="10" name="quantity" value="1" title="Qtà" class="input-text qty text" size="4">
                                       <button id="btnAddToCart" type="submit" class="btn-alt small border">Thêm vào giỏ hàng</button>
                                       <button id="btnBuyNow" type="submit" class="btn-alt small border btn-fogo-primary">Mua ngay</button>
                                    </div>
                                    <div style="margin-top: 20px" class="editor-content">${product.description}</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <div class="row no-margin">
                <div class="border-separator"></div>
            </div>
            <div class="container">
                <div class="row no-margin wrap-text">
                    <div class="col-md-12 padding-leftright-null">
                        <div class="text padding-bottom-null small">
                            <ul class="nav nav-tabs product" role="tablist">
                                <li role="presentation" class="active">
                                    <a href="#tab-one" aria-controls="tab-one" role="tab" data-toggle="tab">
                                        Thông số kỹ thuật
                                    </a>
                                </li>
                            </ul>
                            <div class="tab-content">
                                <div role="tabpanel" class="tab-pane active editor-content" id="tab-one">
                                        ${product.techInfo}
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <c:if test="${product.relatedProducts.size() > 0}">
                <div class="container">
                    <div class="row no-margin wrap-text">
                        <h4 style="margin-bottom: 30px">Sản phẩm liên quan</h4>
                        <div class="product-gallery">
                            <c:forEach items="${product.relatedProducts}" var="relatedProduct">
                                <div class="item">
                                    <a href="/san-pham/${relatedProduct.slug}">
                                        <layout:product product="${relatedProduct}"/>
                                    </a>
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </c:if>

            <div class="row no-margin">
                <div class="border-separator"></div>
            </div>
        </c:if>
        <c:if test="${product == null}">
            <h3 class="text-center" style="margin-bottom: 20px">Không tìm thấy sản phẩm này</h3>
        </c:if>
    </div>
</layout:wrapper>