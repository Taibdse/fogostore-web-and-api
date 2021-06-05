var searchProductTimeout;
var $searchProductInput;

$(function () {
    addActiveClassToMenu();
    bindEventsChangeCollapseIcon();

    $(".product-gallery").owlCarousel({
        loop: true,
        autoplay: true,
        responsive : {
            0:{
                items:2,
            },
            767 : {
                items: 3
            },
            900 : {
                items: 4
            }
        }
    });

    $(".three-item-gallery").owlCarousel({
        loop: true,
        autoplay: true,
        responsive : {
            0:{
                items:2,
            },
            767 : {
                items: 3
            },
        }
    });
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

function isEmpty(val) {
    if (val === null || val === undefined) return true;
    if (typeof val === 'string' && val.trim().length === 0) return true;
    if (typeof val === 'object' && Object.keys(val).length === 0) return true;
    return false;
}

function toMoneyFormat(val) {
    if (isEmpty(val)) return '';
    return Number(val).toFixed(1).replace(/\d(?=(\d{3})+\.)/g, '$&,').slice(0, -2);
}

function getSuggestedProducts(keyword) {
    return $.ajax({
        type: 'GET',
        url: '/api/products/end-user/suggestions?keyword=' + keyword,
    });
}

function renderSuggestedProducts(products) {
    removeSuggestedProductList();
    var $ul = $('<ul class="suggested-products-list"></ul>');
    for (var i = 0; i < products.length; i++) {
        var p = products[i];
        var $li = $('<li class="suggested-products-list-item">' +
            '<a href="/san-pham/' + p.slug + '">' +
            '<img src="' + p.mainImage + '"> <span class="name">' + p.name + '</span> <span class="product-suggested-price">' + toMoneyFormat(p.price) + ' đ</span>' +
            '</a>' +
            '</li>')

        $ul.append($li);
    }
    $('#search-product-input-group').append($ul);
}

function renderLoadingSuggestedProducts() {
    var $ul = $('<ul class="suggested-products-list">Loading...</ul>');
    $('#search-product-input-group').append($ul);
}

function removeSuggestedProductList(){
    $('.suggested-products-list').remove();
}

$(function () {
    $searchProductInput = $('#search-product-input');

    $searchProductInput.focus();

    $searchProductInput.on('input', function (e) {
        clearTimeout(searchProductTimeout);
        searchProductTimeout = setTimeout(function () {
            //get products list
            removeSuggestedProductList();
            var searchValue = e.target.value;
            if (!isEmpty(searchValue) && searchValue.trim().length >= 2) {
                renderLoadingSuggestedProducts();
                $.ajax({
                    method: 'GET',
                    url: '/api/products/end-user/suggestions?keyword=' + searchValue,
                    success: function (res) {
                        renderSuggestedProducts(res);
                    },
                    error: function (err) {
                        removeSuggestedProductList();
                        console.log(err);
                    }
                })
            }
        }, 300);
    });
})

