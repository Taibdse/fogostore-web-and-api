$(function () {
    addActiveClassToMenu();
    bindEventsChangeCollapseIcon();
});

function bindEventsChangeCollapseIcon() {
    $('.collapse').on('show.bs.collapse', function (e) {
        $(e.target).siblings('span').find('i').removeClass('fa-plus').addClass('fa-minus');
    })
    $('.collapse').on('hide.bs.collapse', function (e) {
        $(e.target).siblings('span').find('i').removeClass('fa-minus').addClass('fa-plus');
    })
}

function addActiveClassToMenu() {
    var pathname = window.location.pathname;
    if (pathname === '/') pathname = "/trang-chu";

    function setActiveToMenuItem($lis) {
        Array.from($lis).forEach(function (li) {
            var href = $(li).find('> a').attr('href');
            if (pathname.indexOf(href) > -1) {
                $(li).find('> a').addClass('active-item');
            }
        });
    }

    setActiveToMenuItem($('#header #sidemenu .menu-holder > ul > li'));
}

