<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<%@ page import="com.example.fogostore.common.enumeration.PageType" %>
<%@ page import="com.example.fogostore.common.enumeration.SocialPluginPosition" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<head>
    <title>${pageMetadata.title}</title>
    <meta charset="utf-8">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta name="description" content="${pageMetadata.description}">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="keywords" content="${pageMetadata.keywords}">
    <meta name="HandheldFriendly" content="true">
    <meta name="author" content="Fogostore team">
    <meta name="robots" content="index, follow"/>
    <!-- Favicon -->
    <link rel="shortcut icon" type="image/x-icon" href="${website.logoWebsiteImage}">
    <%--    Social Media Friendly--%>
    <meta property="og:title" content="${pageMetadata.title}">
    <meta property="og:description" content="${pageMetadata.description}">
    <meta property="og:image" content="http://fogostore.vn${pageMetadata.image}">
    <meta property="og:image:secure_url" content="https://fogostore.vn${pageMetadata.image}">
    <meta property="og:locale" content=vi_VN"/>
    <meta property="og:type" content="${pageMetadata.pageType == PageType.PRODUCT_DETAIL ? 'product' : 'website'}"/>
    <meta property="article:publisher" content="${shop.facebookLink}"/>
    <meta property="og:site_name" content="Fogostore"/>
    <meta property="og:url" content="https://fogostore.vn${requestScope['javax.servlet.forward.request_uri']}"/>
    <c:if test="${pageMetadata.pageType == PageType.PRODUCT_DETAIL}">
    <meta property="og:price:amount" content="${pageMetadata.productPrice}">
    <meta property="og:price:currency" content="VND">
    <meta property="product:brand" content="FOGOSTORE">
    <meta property="product:availability" content="in stock">
    <meta property="product:condition" content="new">
    <meta property="product:retailer_item_id" content="${pageMetadata.productId}">
    </c:if>
    <meta property="twitter:title" content="${pageMetadata.title}">
    <meta property="twitter:url" content="https://fogostore.vn${requestScope['javax.servlet.forward.request_uri']}">
    <meta property="twitter:card" content="summary">
    <!-- Bootstrap -->
    <link rel="stylesheet" href="/assets/css/bootstrap/bootstrap.min.css">
    <!-- Optional theme -->
    <link rel="stylesheet" href="/assets/css/bootstrap/bootstrap-theme.min.css">
    <!-- Jquery confirm -->
    <link rel="stylesheet" href="/assets/css/jquery-confirm.min.css">
    <!-- Custom css -->
    <link rel="stylesheet" href="/assets/css/style.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="/assets/css/font-awesome.min.css">
    <link rel="stylesheet" href="/assets/css/ionicons.min.css">
    <!-- Flexslider -->
    <link rel="stylesheet" href="/assets/css/flexslider.css">
    <!-- Owl -->
    <link rel="stylesheet" href="/assets/css/owl.carousel.css">
    <!-- Magnific Popup -->
    <link rel="stylesheet" href="/assets/css/magnific-popup.css">
    <link rel="stylesheet" href="/assets/css/rdw.css">
    <link rel="stylesheet" href="/assets/css/custom/style.css">
    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <layout:socialPlugins position="${SocialPluginPosition.HEAD_TAG}" />

</head>