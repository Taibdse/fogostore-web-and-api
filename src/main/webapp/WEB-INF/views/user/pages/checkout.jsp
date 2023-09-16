<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
prefix="layout" tagdir="/WEB-INF/tags" %> <%@ page language="java"
contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:wrapper hideHeaderBanner="true">
  <div id="home-wrap" class="content-section">
    <h3 class="page-title-header">Đặt hàng</h3>
    <div class="container">
      <div class="row">
        <div class="col-md-4" id="contact-form">
          <h4 class="heading">Thông tin khách hàng</h4>
          <div class="">
            <label for="customerFullname" class="sm-text">Họ tên*</label>
            <input class="form-field" name="customerFullname" id="customerFullname" type="text" placeholder="Họ tên">
          </div>
          <div class="">
            <label for="customerEmail" class="sm-text">Email*</label>
            <input class="form-field" name="customerEmail" id="customerEmail" type="text" placeholder="Email">
          </div>
          <div class="">
            <label for="customerPhone" class="sm-text">Số điện thoại*</label>
            <input class="form-field" name="customerPhone" id="customerPhone" type="text" placeholder="Số điện thoại">
          </div>
          <div class="">
            <label for="customerAddress" class="sm-text">Địa chỉ nhận hàng*</label>
            <input class="form-field" name="customerAddress" id="customerAddress" type="text" placeholder="Địa chỉ nhận hàng">
          </div>
          <div class="">
            <label for="customerNote" class="sm-text">Ghi chú cho shop</label>
            <textarea class="form-field" name="customerNote" id="customerNote" type="text" placeholder="Ghi chú cho shop"></textarea>
          </div>
        </div>
        <div class="col-md-8">
          <h4 class="heading">Thông tin sản phẩm</h4>
          <div class="table-responsive">
            <table class="table cart checkout">
               
            </table>
        </div>
        </div>
      </div>
      <div class="row my-5">
        <div class="col-sm-12 text-center">
          <a id="btnOrder" class="btn-alt small border" href="#">Đặt hàng</a>
        </div>
      </div>
  </div>
</layout:wrapper>
