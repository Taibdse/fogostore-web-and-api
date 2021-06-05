<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true" %>
<style>
    footer h5 {
        margin-bottom: 20px !important;
    }

    footer a {
        color: black;
    }

    footer ul.list-group li {
        margin-bottom: 10px !important;
    }

    footer ul.social {
        float: none;
    }
</style>
<footer>
    <div class="container">
        <div class="row no-margin">
            <div class="col-lg-3 col-md-6 col-6 text px-1">
                <h5>CHÍNH SÁCH KHÁCH HÀNG</h5>
                <ul class="list-group">
                    <c:forEach items="${policies}" var="policy">
                        <li class="mb-5"><a href="/chinh-sach/${policy.slug}">${policy.name}</a></li>
                    </c:forEach>
                </ul>
            </div>
            <div class="col-lg-2 col-md-6 col-6 text px-1">
                <h5>MENU</h5>
                <ul class="list-group">
                    <li>
                        <a href="/trang-chu">Trang chủ</a>
                    </li>
                    <li>
                        <a href="/ve-chung-toi">Giới thiệu</a>
                    </li>
                    <li>
                        <a href="/bai-viet">Bài viết</a>
                    </li>
                </ul>
            </div>
            <div class="col-lg-4 col-md-6 col-6 text px-1">
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
            <div class="col-lg-3 col-md-6 col-6 text px-1">
                <h5>MẠNG XÃ HỘI</h5>
                <ul class="social text-left">
                    <li><a href="${shop.facebookLink}" target="_blank">
                        <img style="width: 30px" src="/assets/img/fb.png" alt="/assets/img/fb.png">
                    </a></li>
                    <li><a href="${shop.youtubeLink}" target="_blank">
                        <img style="width: 30px" src="/assets/img/youtube.png" alt="/assets/img/youtube.png">
                    </a></li>
                    <li><a href="${shop.instagramLink}" target="_blank">
                        <img style="width: 30px" src="/assets/img/instagram.png" alt="/assets/img/instagram.png">
                    </a></li>
                    <li><a href="${shop.zaloLink}" target="_blank">
                        <img style="width: 30px" src="/assets/img/zalo.png" alt="/assets/img/zalo.png">
                    </a></li>
                </ul>
            </div>
        </div>
    </div>
</footer>