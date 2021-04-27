<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ attribute name="onMenu" type="java.lang.Boolean" %>

<div class="header-cart ${onMenu ? 'cart-small-device cart' : ''}">
    <button class="icon-cart">
        <i class="ti-shopping-cart"></i>
        <span class="count-style">0</span>
        <span class="count-price-add">0 đ</span>
    </button>
    <div class="shopping-cart-content">
        <ul></ul>
        <div>
            <div class="shopping-cart-total">
                <h4>Tổng: <span>0 đ</span></h4>
            </div>
            <div class="shopping-cart-btn">
                <a class="btn-style cr-btn" href="/dat-hang">Tiến hành đặt hàng</a>
            </div>
        </div>
    </div>
</div>