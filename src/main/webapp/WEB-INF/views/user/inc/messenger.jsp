<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!-- Messenger Plugin chat Code -->
<div id="fb-root"></div>
<script>
    window.fbAsyncInit = function() {
        FB.init({
            xfbml            : true,
            version          : 'v10.0'
        });
    };

    (function(d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) return;
        js = d.createElement(s); js.id = id;
        js.src = 'https://connect.facebook.net/vi_VN/sdk/xfbml.customerchat.js';
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
</script>

<!-- Your Plugin chat code -->
<div class="fb-customerchat"
     attribution="setup_tool"
     page_id="100197102254591"
     theme_color="#DB3364"
     logged_in_greeting="Chúc Bạn 1 Ngày Tốt Lành, Hãy Bấm Vào Đây Nếu Bạn Cần Fogostore Hỗ Trợ Nhé!"
     logged_out_greeting="Chúc Bạn 1 Ngày Tốt Lành, Hãy Bấm Vào Đây Nếu Bạn Cần Fogostore Hỗ Trợ Nhé!"
     greeting_dialog_display="show">
</div>