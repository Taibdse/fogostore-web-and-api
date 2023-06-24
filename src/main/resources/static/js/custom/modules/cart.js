var cartKey = 'fogostore-cart'
var $cartOnMenu = $('header .header-cart');
var $productCardList = $('.product-card');
var $countProducts = $('header a.cart span.count');
var $totalCart = $('header .count-price-add');
var $cartTable = $('table.cart');
var $cartAction = $('.cart-action')
var $addToCart = $('#addToCart')
var $btnSelects = $('button.btn-select');

var cartModule = (function () {

    var model = {
        isSameProduct: function (p1, p2) {
            if (ValidationUtils.isEmpty(p1) || ValidationUtils.isEmpty(p2)) return false;
            return p1.productId == p2.productId && p1.productTypeId == p2.productTypeId;
        },

        setProductQuantity: function (id, quantity) {
            var index = model.findItemIndexInCart(id);
            if (index > -1) {
                var cartData = model.getCartData();
                cartData[index].quantity = quantity
            }
            model.saveCart(cartData);
        },

        addToCart: function (product, quantity) {
            var cartData = model.getCartData();

            var canPush = true;
            for (var i = 0; i < cartData.length; i++) {
                if (model.isSameProduct(cartData[i], product)) {
                    cartData[i].quantity = cartData[i].quantity + quantity;
                    canPush = false;
                    break;
                }
            }

            if (canPush) {
                product.quantity = quantity;
                product.id = Math.random().toString();
                cartData.push(product)
            }

            model.saveCart(cartData)
        },

        findItemIndexInCart: function (id) {
            var cartData = model.getCartData();
            return cartData.findIndex(function (item) {
                return item.id == id;
            })
        },

        saveCart: function (cartData) {
            var json = JSON.stringify(cartData);
            localStorage.setItem(cartKey, json);
        },

        getCartData: function () {
            var json = localStorage.getItem(cartKey);
            if (ValidationUtils.isEmpty(json)) return [];
            try {
                var data = JSON.parse(json);
                for (var i = 0; i < data.length; i++) {
                    data[i].totalPrice = Number(data[i].quantity) * Number(data[i].price)
                }
                return data
            } catch (err) {
                return [];
            }
        },

        removeItem: function (id) {
            var cartData = model.getCartData();
            var index = model.findItemIndexInCart(id);
            if (index > -1) {
                cartData.splice(index, 1);
            }
            model.saveCart(cartData)
        },

        getCartTotal: function () {
            var cartData = model.getCartData();

            var totalMoney = 0;
            var totalQuantity = 0;

            cartData.forEach(function (item) {
                totalMoney += Number(item.price) * item.quantity;
                totalQuantity += item.quantity;
            })

            return {
                totalMoney: totalMoney,
                totalQuantity: totalQuantity
            }
        },

        getCartItemTotalPrice: function (id) {
            var cartData = model.getCartData();
            var index = model.findItemIndexInCart(id);
            if (index > -1) {
                return cartData[index].price * cartData[index].quantity;
            }
            return 0;
        },

        clearCart: function() {
            model.saveCart([]);
        }

    }

    var view = {
        renderCartContentOnMenu: function (cartData) {
            var $headerCartContent = $cartOnMenu.find('.shopping-cart-content ul');
            $headerCartContent.html('');

            if (cartData.length == 0) {
                $headerCartContent.html('<h4>' + constants.noProductInCart + '</h4>')
                $cartOnMenu.find('.shopping-cart-content > div').addClass('d-none');
            } else {
                $cartOnMenu.find('.shopping-cart-content > div').removeClass('d-none');
                cartData.forEach(function (item) {
                    var $cartItem = '<li class="single-shopping-cart">' +
                        '<div class="shopping-cart-img">' +
                        '<a href="/san-pham/' + item.slug + '">' +
                        '<img alt="" style="width: 60px" src="' + item.image + '">' +
                        '</a>' +
                        '</div>' +
                        '<div class="shopping-cart-title" style="max-width: 180px">' +
                        '<h3><a href="/san-pham/' + item.slug + '">' + item.name + '</a></h3>' +
                        '<span>' + item.productTypeName + '</span>' +
                        '<span>Giá: ' + StringUtils.getMoneyFormat(item.price) + ' đ</span>' +
                        '<span>Số lượng: ' + item.quantity + '</span>' +
                        '</div>' +
                        '<div class="shopping-cart-delete">' +
                        '<a class="delete-cart-item" href="#">' +
                        '<i class="icofont icofont-ui-delete"></i>' +
                        '</a>' +
                        '</div>' +
                        '</li>';

                    $headerCartContent.append($cartItem);


                    for (var i = 0; i < $headerCartContent.length; i++) {
                        $headerCartContent.eq(i).find('li').last().find('a.delete-cart-item').on('click', function (e) {
                            e.preventDefault();
                            viewModel.handleRemoveCartItem(item);
                        })
                    }

                });
            }

        },

        renderTotalMoney: function (total, onMenu) {
            total = StringUtils.getMoneyFormat(total);
            if (onMenu) {
                $totalCart.html(total + ' đ');
                $cartOnMenu.find('.shopping-cart-total > h4 > span').html(total + ' đ');
            } else {

            }
        },

        renderTotalProductQuantity: function (total) {
            $countProducts.html(total)
        },

        renderProductsOnCartTable: function($cartTable, cartData, canEdit) {
            if ($cartTable.length == 0) return;

            $cartTable.html('');

            var $thead = ' <thead>' +
            '<tr>' +
            '<th class="">Sản phẩm</th>' +
            '<th class="">Ảnh</th>' +
            '<th class="">Thông tin</th>' +
            '<th class="">Đơn Giá</th>' +
            '<th class="">Số lượng</th>' +
            '<th class="">Tổng tiền</th>' +
            '<th class="">Xóa</th>' +
            '</tr>' +
            '</thead>';

            if(canEdit) {
                $thead = ' <thead>' +
                    '<tr>' +
                    '<th class="product-name">Sản phẩm</th>' +
                    '<th class="">Ảnh</th>' +
                    '<th class="">Thông tin</th>' +
                    '<th class="">Đơn Giá</th>' +
                    '<th class="">Số lượng</th>' +
                    '<th class="">Tổng tiền</th>' +
                    '<th class="">Xóa</th>' +
                    '</tr>' +
                    '</thead>';
            } else {
                $thead = ' <thead>' +
                    '<tr>' +
                    '<th class="">Sản phẩm</th>' +
                    '<th class="">Ảnh</th>' +
                    '<th class="">Thông tin</th>' +
                    '<th class="">Đơn Giá</th>' +
                    '<th class="">Số lượng</th>' +
                    '<th class="">Tổng tiền</th>' +
                    '</tr>' +
                    '</thead>';
            }

        var $tbody = $('<tbody></tbody>');

        $cartTable.append($thead).append($tbody);

        cartData.forEach(function (item) {
            var $cartItem;

            if(canEdit) {
                $cartItem = '<tr>' +
                    '<td class="product-name">' +
                    '<a href="/san-pham/' + item.slug + '">' + item.name + '</a>' +
                    '</td>' +
                    '<td class="product-thumbnail">' +
                    '<a href="/san-pham/' + item.slug + '"><img src="' + item.image + '" alt=""></a>' +
                    '</td>' +
                    '<td class="">' + item.productTypeName + '</td>' +
                    '<td class="product-price text-nowrap">' +
                    '<span class="amount">' + StringUtils.getMoneyFormat(item.price) + ' đ</span>' +
                    '</td>' +
                    '<td class="product-quantity">' +
                    '<input type="number" step="1" min="1" max="10" name="quantity" value="' + item.quantity + '" class="" size="4">' +
                    '</td>' +
                    '<td class="product-subtotal text-nowrap">' + StringUtils.getMoneyFormat(item.totalPrice) + ' đ</td>' +
                    '<td class="product-cart-icon">' +
                    '<a href="#" class="delete-product"><i class="icon ion-trash-a" style="font-size: 20px"></i></a>' +
                    '</td>' +
                    '</tr>';
            } else {
                $cartItem = '<tr>' +
                    '<td class="product-name">' +
                    '<a href="/san-pham/' + item.slug + '">' + item.name + '</a>' +
                    '</td>' +
                    '<td class="product-thumbnail">' +
                    '<a href="/san-pham/' + item.slug + '"><img src="' + item.image + '" alt=""></a>' +
                    '</td>' +
                    '<td class="">' + item.productTypeName + '</td>' +
                    '<td class="product-price text-nowrap">' +
                    '<span class="amount">' + StringUtils.getMoneyFormat(item.price) + ' đ</span>' +
                    '</td>' +
                    '<td class="product-quantity">'+ item.quantity +'</td>' +
                    '<td class="product-subtotal text-nowrap">' + StringUtils.getMoneyFormat(item.totalPrice) + ' đ</td>' +
                    '</tr>';
            }

            $tbody.append($cartItem);

            // bind events delete cart-item, update quantity
            if (canEdit) {
                $tbody.find('tr td.product-cart-icon a.delete-product').last().on('click', function (e) {
                    e.preventDefault();
                    viewModel.handleRemoveCartItem(item);
                })
    
                $tbody.find('tr td.product-quantity input').last().on('input', function (e) {
                    viewModel.handleChangeQuantity(e, item.id);
                })
            }
        });
        },

        renderCartOnCartPage: function (cartData) {
            if ($cartTable.length == 0) return;

            $cartTable.html('');
            if (cartData.length == 0) {
                $('#cart-empty').html('<h3 class="text-center my-5 font-weight-bold">' + constants.noProductInCart + '</h3>');
                $cartAction.addClass('d-none');
            } else {
                $('#cart-empty').html('');
                $cartAction.removeClass('d-none');
                view.renderProductsOnCartTable($cartTable, cartData, true);
            }
        },

        renderCartOnCheckoutPage: function (cartData) {
            var $cartTable = $('.cart.checkout');
            view.renderProductsOnCartTable($cartTable, cartData, false);
        },

        showProductAddedMessage: function () {
            AlertUtils.showAlert({
                title: constants.product_added_title,
                content: constants.product_added_content,
                type: 'green'
            })
        },

        renderCartItemTotal: function ($ele, total) {
            $ele.text(StringUtils.getMoneyFormat(total) + ' đ');
        },

        bindEvents: function () {
            //init events for product search page
            for (var i = 0; i < $productCardList.length; i++) {
                $productCardList.eq(i).find('i.add-to-cart').on('click', viewModel.handleAddToCart);
            }

            //init events for product detail page
            for (var i = 0; i < $('.select-product-type').length; i++) {
                $('.select-product-type').eq(i).on('click', function (e) {
                    $(e.target).parent().find('.select-product-type.btn-select.active').removeClass('active');
                    $(e.target).addClass('active');
                    var price = $(e.target).data('product-type-display-price')
                    var oldPrice = $(e.target).data('product-type-display-old-price');
                    $('#product-price').text(price);
                    var tempOldPrice = +oldPrice.replaceAll(/\D/g, "");
                    if (tempOldPrice > 0) {
                        $('#product-old-price').text(oldPrice);
                    } else {
                        $('#product-old-price').text('');
                    }
                })
            }

            $addToCart.on('click', viewModel.handleAddProductInCartDetails)
        },

        toggleLoader: function (open) {
            if(open) $("#myloader").fadeIn(0);
            else $("#myloader").fadeOut(0);
            // if (open) $('#ftco-loader').addClass('show');
            // else $('#ftco-loader').removeClass('show');
        }
    };

    var viewModel = {
        handleRemoveCartItem: function (cartItem) {
            model.removeItem(cartItem.id);
            viewModel.updateCartOnMenu();
            viewModel.updateCartOnCartPage()
        },

        updateCartOnMenu: function () {
            var cartData = model.getCartData();
            var totalCart = model.getCartTotal();
            view.renderCartContentOnMenu(cartData);
            view.renderTotalMoney(totalCart.totalMoney, true)
            view.renderTotalProductQuantity(totalCart.totalQuantity);
        },

        updateCartOnCartPage: function () {
            var cartData = model.getCartData();
            view.renderCartOnCartPage(cartData)
        },

        handleAddToCart: function (e) {
            e.preventDefault();
            e.stopPropagation()
            var $ele = $(e.target).parents('.product-card');
            var newProduct = {
                productId: $ele.data('id'),
                name: $ele.data('name'),
                price: Number($ele.data('price')),
                image: $ele.data('image'),
                slug: $ele.data('slug'),
                productTypeId: $ele.data('type-id'),
                productTypeName: $ele.data('type-name'),
                available: $ele.data('available'),
            }
            if (newProduct.available == '0') {
                AlertUtils.showAlert({
                    title: constants.product_runout_title,
                    content: constants.product_runout_content,
                    type: 'red'
                })
            } else {
                model.addToCart(newProduct, 1);
                viewModel.updateCartOnMenu()
                view.showProductAddedMessage();
            }
        },

        handleAddProductInCartDetails: function (e) {
            e.preventDefault();
            var $productDetails = $('#productDetails');
            var productId = $productDetails.data('id');
            var name = $productDetails.data('name');
            var price = Number($productDetails.data('price'));
            var image = $productDetails.data('image');
            var slug = $productDetails.data('slug');
            var quantity = $('#productQuantity').val();
            var productTypeId = $('.select-product-type.active').data('product-type-id');
            var productTypeName = $productDetails.data('type-name');
            if (!ValidationUtils.isEmpty(productTypeId)) {
                price = +$('.select-product-type.active').data('product-type-price');
                productTypeName = $('.select-product-type.active').data('product-type-name');
            }

            var product = {
                productId: productId,
                name: name,
                price: price,
                image: image,
                slug: slug,
                productTypeId: productTypeId,
                productTypeName: productTypeName
            }

            model.addToCart(product, StringUtils.getNumber(quantity));
            viewModel.updateCartOnMenu()
            view.showProductAddedMessage();
        },

        handleChangeQuantity: function (e, id) {
            var $ele = $(e.target);
            var quantity = $ele.val();
            model.setProductQuantity(id, StringUtils.getNumber(quantity))
            var cartItemTotalPrice = model.getCartItemTotalPrice(id);
            var $renderedEle = $ele.parents('tr').find('td.product-subtotal');
            view.renderCartItemTotal($renderedEle, cartItemTotalPrice)
        },

        handleClearCart: function () {
            model.clearCart();
            viewModel.updateCartOnMenu();
            viewModel.updateCartOnCartPage();
        },

        init: function () {
            view.bindEvents();
            var cartData = model.getCartData();

            // redirect to homepage when user in checkout page without cart-products
            if(location.href.includes('/dat-hang') && cartData.length === 0) return location.href = "/";

            var ids = [];
            for (var item of cartData) {
                ids.push(item.productId);
            }
            if(ids.length === 0) {
                view.renderCartOnCartPage(cartData);
                view.renderCartOnCheckoutPage(cartData);
                viewModel.updateCartOnMenu();
                return;
            }

            // call api to fetch latest cart-products info
            view.toggleLoader(true)
            productService.getProductsInCart(ids.join(','), function (res) {
                var loadedProducts = res;
                for (var i = 0; i < cartData.length; i++) {
                    var cartItem = cartData[i];
                    var index = loadedProducts.findIndex(function (p) {
                        return p.id == cartItem.productId;
                    });
                    if (index > -1) {
                        var loadedProduct = loadedProducts[index];
                        cartItem.name = loadedProduct.name;
                        cartItem.image = loadedProduct.mainImage;
                        cartItem.price = loadedProduct.newPrice;
                        for (var j = 0; j < loadedProduct.productTypes.length; j++) {
                            if (cartItem.productTypeId == loadedProduct.productTypes[j].id) {
                                cartItem.price = loadedProduct.productTypes[j].price;
                                break;
                            }
                        }
                    } else {
                        cartData.splice(i, 1);
                    }
                }

                model.saveCart(cartData)
                view.renderCartOnCartPage(cartData);
                view.renderCartOnCheckoutPage(cartData);
                viewModel.updateCartOnMenu();
                view.toggleLoader(false)
            }, function (err) {
                console.log(err)
                view.toggleLoader(false)
            })
        }
    }

    return {
        model: model,
        view: view,
        viewModel: viewModel
    }

})()

cartModule.viewModel.init();