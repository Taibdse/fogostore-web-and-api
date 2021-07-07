<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<layout:wrapper>
    <style>
        .page-title-header {
            font-weight: bold;
            font-size: 2em;
        }
    </style>
    <div id="home-wrap" class="content-section">
        <div class="container" style="padding-top: 30px; padding-bottom: 60px">
            <h3 class="text-center page-title-header">Tìm kiếm sản phẩm</h3>
            <section id="projects" data-isotope="load-simple" class="page padding-top-null padding-onlybottom-lg">
                <div class="row">
                    <div class="col-sm-6 mx-auto">
                        <form id="search-form" class="padding-md padding-md-topbottom-null">
                            <input class="form-field" name="searchValue" id="txtSearchValue" type="text" placeholder="Nhập sản phẩm cần tìm...">
                        </form>
                    </div>
                </div>
                <h3>Kết quả tìm kiếm <span id="searchValue"></span></h3>
                <div class="row" id="search-result">
                    <div class="col-12">
                        <div class="projects-items equal four-columns">
                            <c:forEach items="${productPage.content}" var="product">
                                <layout:product product="${product}"/>
                            </c:forEach>
                        </div>
                        <layout:pagination
                                totalPages="${productPage.totalPages}"
                                current="${productPage.pageable.pageNumber + 1}"/>
                    </div>
                </div>
            </section>
        </div>
    </div>
</layout:wrapper>

<script>
  $(function () {
      var $searchForm = $('#search-form');
      var $searchInput = $searchForm.find('#txtSearchValue');
      var $searchValue = $('#searchValue');

      (function () {
          var searchvalue = UrlUtils.getParameterByName("searchValue");
          $searchInput.val(searchvalue);
          if(!isEmpty(searchvalue)) {
              $searchValue.text('(Từ khóa - "' + searchvalue +  '")');
          }
      })();

      $searchForm.on('submit', function (e) {
          e.preventDefault();
          var searchValue = $searchInput.val();
          location.href = '/tim-san-pham?searchValue=' + searchValue;
      });

      $('nav ul.pagination li.page-item').on('click', function (e) {
          e.preventDefault();
          var page = $(this).data('page');
          var searchValue = $searchInput.val();
          var href = location.pathname + '?page=' + page + '&searchValue=' + searchValue;
          location.href = href;
      });

      $('html, body').animate({
          scrollTop: $("#search-result").offset().top
      }, 0);
  })
</script>