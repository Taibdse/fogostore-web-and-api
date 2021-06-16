<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!-- Messenger Plugin chat Code -->
<div id="fb-root"></div>

<!-- Your Plugin chat code -->
<div id="fb-customer-chat" class="fb-customerchat">
</div>

<script>
    var chatbox = document.getElementById('fb-customer-chat');
    chatbox.setAttribute("page_id", "100197102254591");
    chatbox.setAttribute("attribution", "biz_inbox");
    chatbox.setAttribute("theme_color", "#DB3364");
    chatbox.setAttribute("logged_in_greeting", "Chúc Bạn 1 Ngày Tốt Lành, Hãy Bấm Vào Đây Nếu Bạn Cần FogoStore Hỗ Trợ Nhé!");
    chatbox.setAttribute("greeting_dialog_display", "show");

    window.fbAsyncInit = function() {
        FB.init({
            xfbml            : true,
            version          : 'v11.0'
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