<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="layout" tagdir="/WEB-INF/tags" %> <%@ page language="java"
contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:wrapper>
  <div id="home-wrap" class="content-section">
    <h3 class="page-title-header">Giỏ hàng</h3>
    <div class="container">
      <div class="row">
        <div class="col-12">
            <div class="table-responsive">
                <table class="table cart">
                   
                </table>
            </div>
            <div id="cart-empty"></div>
        </div>
      </div>
      <div class="row cart-action">
        <div class="col-12">
          <a class="btn-alt small border" href="/">Tiếp tục mua sắm</a>
          <a id="btn-continue-order" class="btn-alt small border ml-auto btn-fogo-primary" href="/dat-hang" style="margin-left: auto;">Tiến hành đặt hàng</a>
        </div>
      </div>
  </div>
</layout:wrapper>
