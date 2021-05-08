<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<style>
    footer h5 {
        margin-bottom: 20px!important;
    }
    footer a {
        color: black;
    }
    footer ul.list-group li {
        margin-bottom: 10px!important;
    }
    footer ul.social {
        float: none;
    }
</style>
<footer>
    <div class="container">
        <div class="row no-margin">
            <div class="col-lg-4 col-md-6 col-6 text">
                <h5>CHÍNH SÁCH KHÁCH HÀNG</h5>
                <ul class="list-group">
                    <c:forEach items="${policies}" var="policy">
                        <li class="mb-5"><a href="/chinh-sach/${policy.slug}">${policy.name}</a></li>
                    </c:forEach>
                </ul>
            </div>
            <div class="col-lg-4 col-md-6 col-6 text">
                <h5>LIÊN HỆ CHÚNG TÔI</h5>
                <ul class="list-group">
                    <li>
                        <strong>Địa chỉ: </strong>
                        <a href="${shop.addressLink}" target="_blank">${shop.shopAddress}</a>
                    </li>
                    <li>
                        <strong>SDT: </strong>
                        <a href="tel:${shop.phone}">${shop.phone}</a>
                    </li>
                    <li>
                        <strong>Email: </strong>
                        <a href="mailto:${shop.email}?subject=subject">${shop.email}</a>
                    </li>
                    <li>
                        <strong>Website: </strong>
                        <a href="${shop.websiteLink}">${shop.websiteLink}</a>
                    </li>
                </ul>
            </div>
            <div class="col-lg-4 col-md-6 col-6 text">
                <h5>MẠNG XÃ HỘI</h5>
                <ul class="social text-left">
                    <li><a href="${shop.facebookLink}" target="_blank"><i class="fa fa-facebook" aria-hidden="true"></i></a></li>
                    <li><a href="${shop.youtubeLink}" target="_blank"><i class="fa fa-youtube" aria-hidden="true"></i></a></li>
                    <li><a href="${shop.instagramLink}" target="_blank"><i class="fa fa-instagram" aria-hidden="true"></i></a></li>
                </ul>
            </div>
        </div>
    </div>
</footer>