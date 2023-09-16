<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %> 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>

<header id="header" class="border">
  <div class="container">
    <nav class="navbar navbar-default">
      <div id="logo">
        <a class="navbar-brand" href="/">
          <img src="${website.logoWebsiteImage}" class="normal" alt="logo" />
          <img src="${website.logoWebsiteImage}" class="retina" alt="logo" />
        </a>
      </div>
      <div id="sidemenu">
        <div class="menu-holder">
          <ul>
            <c:forEach items="${categoryMap}" var="categoryNode">
              <li class="submenu">
                <a href="/danh-muc/${categoryNode.value.value.slug}"
                  >${categoryNode.value.value.name}</a
                >
                <c:if test="${categoryNode.value.children.size() > 0}">
                  <ul class="sub-menu">
                    <c:forEach
                      items="${categoryNode.value.children}"
                      var="childCategoryNode"
                    >
                      <li>
                        <a href="/danh-muc/${childCategoryNode.value.slug}"
                          >${childCategoryNode.value.name}</a
                        >
                      </li>
                    </c:forEach>
                  </ul>
                </c:if>
              </li>
            </c:forEach>
            <li class="shop">
              <a href="/gio-hang" class="cart"
                ><i class="icon ion-bag"></i><span class="count">0</span></a
              >
              <!-- <ul class="cart-wrap">
                <div class="shopping-cart">
                  <ul class="list-cart">
                    <li class="item">
                      <img src="assets/img/shop1.jpg" alt="" />
                      <div class="content">
                        <h5>MacBook Cover</h5>
                        <span class="quantity"> 1 x 299.00$ </span>
                      </div>
                    </li>
                  </ul>
                  <p class="total">Subtotal: <span>$299.00</span></p>
                  <p class="buttons">
                    <a href="#" class="my-cart">View Cart</a>
                    <a href="#" class="checkout">Checkout</a>
                  </p>
                </div>
              </ul> -->
            </li>
            <!-- <li class="submenu">
              <a href="/gio-hang">Giỏ hàng</a>
            </li> -->
          </ul>
        </div>
      </div>
      <div id="menu-responsive-sidemenu">
        <div class="menu-button">
          <span class="bar bar-1"></span>
          <span class="bar bar-2"></span>
          <span class="bar bar-3"></span>
        </div>
      </div>
      <!--  Shop Icon Mobile -->
      <div class="shop-mobile">
        <ul>
          <li class="shop">
            <a href="/gio-hang" class="cart">
              <i class="icon ion-bag"></i><span class="count">0</span>
            </a>
          </li>
        </ul>
      </div>
      <!--  END Shop Icon Mobile -->
    </nav>
    <div
      id="search-product-input-group"
      class="input-group bg-danger float-right"
      style="max-width: 400px; min-width: 250px"
    >
      <input
        type="text"
        class="form-control"
        placeholder="Nhập tên sản phẩm"
        aria-describedby="basic-addon2"
        id="search-product-input"
      />
      <%--
      <span class="input-group-addon" id="basic-addon2"
        >--%> <%-- <i class="fa fa-search"></i>--%> <%-- </span
      >--%>
    </div>
  </div>
</header>
