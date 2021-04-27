<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!-- Load Facebook SDK for JavaScript -->
<div id="fb-root"></div>

<script>
    window.fbAsyncInit = function() {
        FB.init({
            xfbml            : true,
            version          : 'v8.0'
        });
    };

    (function(d, s, id) {
        var js, fjs = d.getElementsByTagName(s)[0];
        if (d.getElementById(id)) return;
        js = d.createElement(s); js.id = id;
        js.src = 'https://connect.facebook.net/en_US/sdk/xfbml.customerchat.js';
        fjs.parentNode.insertBefore(js, fjs);
    }(document, 'script', 'facebook-jssdk'));
</script>

<!-- Chat Plugin code -->
<div class="fb-customerchat"
     attribution=setup_tool
     page_id="171029696856627"
     theme_color="#F82102"
     logged_in_greeting="Xin chào, Fmanracing có thể giúp được gì cho bạn"
     logged_out_greeting="Xin chào bạn, fmanracing có thể giúp gì cho bạn?"
     greeting_dialog_display="show">
</div>
