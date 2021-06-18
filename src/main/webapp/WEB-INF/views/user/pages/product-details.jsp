<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:wrapper>
    <style>
        .product-price {
            font-weight: bold;
            color: var(--main-color);
            font-size: 2em;
        }
    </style>
<%--    <layout:page-header title="Thông tin sản phẩm"/>--%>
    <div id="home-wrap" class="content-section" style="padding-top: 30px;">
        <!-- Product Intro -->
        <c:if test="${product != null}">
            <div class="container">
                <div class="row no-margin wrap-text padding-onlytop-lg">
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
                            <!-- Single Services -->
                            <div id="product-header">
                                <div class="text small padding-top-null padding-md-bottom">
                                    <h1 class="product-name">${product.name}</h1>
                                    <span class="product-price">
                                    <layout:money price="${product.price}"/>
                                </span>
                                    <c:forEach items="${product.productTypes}" var="productType">
                                        <div style="font-size: 1.1em">
                                            <layout:money price="${productType.price}"/>
                                            <span style="margin-left: 10px"
                                                  class="d-inline-block">${productType.name}</span>
                                        </div>
                                    </c:forEach>
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
                        <h4>Sản phẩm liên quan</h4>
                        <div class="product-gallery">
                            <c:forEach items="${product.relatedProducts}" var="relatedProduct">
                                <div class="item">
                                    <a href="/san-pham/${relatedProduct.slug}">
                                        <layout:product product="${relatedProduct}" />
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