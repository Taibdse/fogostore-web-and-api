<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" trimDirectiveWhitespaces="true"%>

<div class="slider-social mt-5" >
    <ul>
        <li class="facebook d-inline mr-2 mb-3">
            <a href="${shop.facebookLink}" target="_blank">
                <i class="icofont icofont-social-facebook"></i>
            </a>
        </li>
        <li class="youtube d-inline mr-2 mb-3">
            <a href="${shop.youtubeLink}" target="_blank">
                <i class="icofont icofont-social-youtube"></i>
            </a>
        </li>
        <li class="zalo d-inline mb-3">
            <a href="${shop.zaloLink}" target="_blank">
                <i class="icofont icofont-social-zalo"></i>
            </a>
        </li>
    </ul>
</div>