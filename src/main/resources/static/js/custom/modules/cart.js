var cartKey = 'fogostore-cart'
var $cartOnMenu = $('header .header-cart');
var $productCardList = $('.product-card');
var $countProducts = $('header a.cart span.count');
var $totalCart = $('header .count-price-add');
var $cartTable = $('table.cart');
var $cartAction = $('.cart-action')
var $addToCart = $('#btnAddToCart');
var $btnBuyNow = $('#btnBuyNow');
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

        addToCart: function (product) {
            var cartData = model.getCartData();

            var canPush = true;
            for (var i = 0; i < cartData.length; i++) {
                var cartItem = cartData[i];
                if (model.isSameProduct(cartItem, product)) {
                    cartItem.quantity = cartItem.quantity + product.quantity;
                    canPush = false;
                    break;
                } else if (product.shouldBuyNow) {
                    cartItem.isSelected = false;
                }
            }

            if (canPush) {
                product.id = Math.random().toString();
                cartData.push(product);
            }
            if(product.shouldBuyNow) {
                delete product.shouldBuyNow;
                product.isSelected = true;
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

        getSelectedCartData: function() {
            var cartData = model.getCartData();
            return cartData.filter(function(item) { return item.isSelected });
        },

        removeItem: function (id) {
            var cartData = model.getCartData();
            var index = model.findItemIndexInCart(id);
            if (index > -1) {
                cartData.splice(index, 1);
            }
            model.saveCart(cartData)
        },

        updateCartItemSelected: function(cartItemId, checked) {
            var cartData = model.getCartData();
            var index = model.findItemIndexInCart(cartItemId);
            cartData[index].isSelected = checked;
            model.saveCart(cartData);
        },

        updateAllCartItemsSelected: function(checked) {
            var cartData = model.getCartData();
            cartData.forEach(function(cartItem) {
                cartItem.isSelected = checked;
            });
            model.saveCart(cartData);
        },

        isSelectedAllCartItems: function() {
            var cartData = model.getCartData();
            return cartData.every(function(item) { return item.isSelected });
        },

        hasSelectedCartItems: function() {
            var cartData = model.getCartData();
            return cartData.some(function(item) { return item.isSelected });
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
        },

        clearSelectedCartItems: function() {
            var cartData = model.getCartData();
            var unselectedCartItems = cartData.filter(function(item) { return !item.isSelected });
            model.saveCart(unselectedCartItems);
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

            var $thead = $('<thead></thead>');

            if(canEdit) {
                $thead.html('<tr>' +
                        '<th class=""><input type="checkbox" class="input-product-selected-all" /></th>' +
                        '<th class="product-name">Sản phẩm</th>' +
                        '<th class="">Ảnh</th>' +
                        '<th class="">Thông tin</th>' +
                        '<th class="">Đơn Giá</th>' +
                        '<th class="">Số lượng</th>' +
                        '<th class="">Tổng tiền</th>' +
                        '<th class="">Xóa</th>' +
                    '</tr>');
            } else {
                $thead.html('<tr>' +
                    '<th class="">Sản phẩm</th>' +
                    '<th class="">Ảnh</th>' +
                    '<th class="">Thông tin</th>' +
                    '<th class="">Đơn Giá</th>' +
                    '<th class="">Số lượng</th>' +
                    '<th class="">Tổng tiền</th>' +
                    '</tr>')
            }

            var $tbody = $('<tbody></tbody>');

            $cartTable.append($thead).append($tbody);

            var $inputProductSelectedAll = $thead.find('tr input.input-product-selected-all');
            if (canEdit) {
               if(model.isSelectedAllCartItems()) {
                    $inputProductSelectedAll.prop('checked', true);
               }
               $inputProductSelectedAll.on('change', function (e) {
                   viewModel.handleChangeAllCartItemsSelected(e)
               })
            }

            cartData.forEach(function (item) {
                var $cartItem;

                if(canEdit) {
                    $cartItem = '<tr data-id="'+ item.id +'">' +
                        '<td class="product-name">' +
                        '<input type="checkbox" class="input-product-selected" />' +
                        '</td>' +
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
                        '<input type="number" step="1" min="1" max="10" name="quantity" value="' + item.quantity + '" class="input-quantity" size="4">' +
                        '</td>' +
                        '<td class="product-subtotal text-nowrap">' + StringUtils.getMoneyFormat(item.totalPrice) + ' đ</td>' +
                        '<td class="product-cart-icon">' +
                        '<a href="#" class="delete-product"><i class="icon ion-trash-a" style="font-size: 20px"></i></a>' +
                        '</td>' +
                        '</tr>';
                } else {
                    $cartItem = '<tr data-id="'+ item.id +'">' +
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

                // bind events delete cart-item, update quantity, checkbox select product
                if (canEdit) {
                    var $tr = $tbody.find('tr').last();

                    $tr.find('td.product-cart-icon a.delete-product').on('click', function (e) {
                        e.preventDefault();
                        viewModel.handleRemoveCartItem(item);
                    })

                    $tr.find('td.product-quantity input.input-quantity').on('input', function (e) {
                        viewModel.handleChangeQuantity(e, item.id);
                    });

                    var $latestInputProductSelected = $tr.find('input.input-product-selected');
                    $latestInputProductSelected.on('change', function(e) {
                        viewModel.handleChangeCartItemSelected(e, item);
                    });
                    view.checkSelectedCartItem($cartTable, item, item.isSelected);
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

        showNoCartItemSelectedAlert: function() {
            AlertUtils.showAlert({
                title: constants.no_cart_item_selected_title,
                content: constants.no_cart_item_selected_content,
                type: 'red'
            })
        },

        renderCartItemTotal: function ($ele, total) {
            $ele.text(StringUtils.getMoneyFormat(total) + ' đ');
        },

        getProductDetailsInfo: function($productDetailsWrapper, isDetailPage) {
            var newProduct;
            if(isDetailPage) {
                var productId = $productDetailsWrapper.data('id');
                var name = $productDetailsWrapper.data('name');
                var price = Number($productDetailsWrapper.data('price'));
                var image = $productDetailsWrapper.data('image');
                var slug = $productDetailsWrapper.data('slug');
                var quantity = Number($('#productQuantity').val());
                var productTypeId = $('.select-product-type.active').data('product-type-id');
                var productTypeName = $productDetailsWrapper.data('type-name');
                var available = $productDetailsWrapper.data('available');

                if (!ValidationUtils.isEmpty(productTypeId)) {
                    price = +$('.select-product-type.active').data('product-type-price');
                    productTypeName = $('.select-product-type.active').data('product-type-name');
                }

                newProduct = {
                    productId: productId,
                    name: name,
                    price: price,
                    image: image,
                    slug: slug,
                    productTypeId: productTypeId,
                    productTypeName: productTypeName,
                    available: available,
                    quantity
                }
            } else {
                newProduct = {
                    productId: $productDetailsWrapper.data('id'),
                    name: $productDetailsWrapper.data('name'),
                    price: Number($productDetailsWrapper.data('price')),
                    image: $productDetailsWrapper.data('image'),
                    slug: $productDetailsWrapper.data('slug'),
                    productTypeId: $productDetailsWrapper.data('type-id'),
                    productTypeName: $productDetailsWrapper.data('type-name'),
                    available: $productDetailsWrapper.data('available'),
                    quantity: 1
                }
            }

            if (newProduct.available == '0') {
                AlertUtils.showAlert({
                    title: constants.product_runout_title,
                    content: constants.product_runout_content,
                    type: 'red'
                });
                return null;
            }
            return newProduct;
        },

        bindEvents: function () {
            // init events for product search page
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

            $addToCart.on('click', viewModel.handleAddProductInCartDetails);

            $btnBuyNow.on('click', viewModel.handleBuyNow);

            $('#btn-continue-order').on('click', viewModel.handleProcessCheckout)
        },

        toggleLoader: function (open) {
            if(open) $("#myloader").fadeIn(0);
            else $("#myloader").fadeOut(0);
        },

        redirectToCheckoutPage: function() {
            window.location.href = "/dat-hang";
        },

        checkSelectedCartHeader: function ($cartTable, checked) {
            $cartTable.find('thead input.input-product-selected-all').prop('checked', checked);
        },

        checkSelectedAllCartItem: function($cartTable, checked) {
            view.checkSelectedCartHeader($cartTable, checked);
            $cartTable.find('tbody tr input.input-product-selected').prop('checked', checked);
            if(checked) {
                $cartTable.find('tbody tr').addClass('selected');
            } else {
                $cartTable.find('tbody tr').removeClass('selected');
            }
        },

        checkSelectedCartItem: function($cartTable, item, checked) {
            var $tr = $cartTable.find('tbody tr[data-id="'+ item.id +'"]')
            $tr.find('input.input-product-selected').prop('checked', checked);
            if(checked) $tr.addClass('selected');
            else $tr.removeClass('selected');
        },
    };

    var viewModel = {
        handleProcessCheckout: function(e) {
            e.preventDefault();
            var hasSelectedCartItems = model.hasSelectedCartItems();
            if (!hasSelectedCartItems) {
                view.showNoCartItemSelectedAlert()
            } else {
                view.redirectToCheckoutPage();
            }
        },

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
            var $productCard = $(e.target).parents('.product-card');
            var newProduct = view.getProductDetailsInfo($productCard, false);
            if(newProduct) {
                model.addToCart(newProduct);
                viewModel.updateCartOnMenu()
                view.showProductAddedMessage();
            }
        },

        handleAddProductInCartDetails: function (e) {
            e.preventDefault();
            var $productDetails = $('#productDetails');
            var product = view.getProductDetailsInfo($productDetails, true);
            if(product) {
                model.addToCart(product);
                viewModel.updateCartOnMenu()
                view.showProductAddedMessage();
            }
        },

         handleBuyNow: function (e) {
            e.preventDefault();
            var $productDetails = $('#productDetails');
            var product = view.getProductDetailsInfo($productDetails, true);
            product.shouldBuyNow = true;
            model.addToCart(product);
            view.redirectToCheckoutPage();
        },

        handleChangeQuantity: function (e, id) {
            var $ele = $(e.target);
            var quantity = $ele.val();
            model.setProductQuantity(id, StringUtils.getNumber(quantity))
            var cartItemTotalPrice = model.getCartItemTotalPrice(id);
            var $renderedEle = $ele.parents('tr').find('td.product-subtotal');
            view.renderCartItemTotal($renderedEle, cartItemTotalPrice);
        },

        handleChangeCartItemSelected: function(e, item) {
            var itemSelected = e.target.checked;
            model.updateCartItemSelected(item.id, itemSelected);
            var $cartTable = $(e.target).parents('table.cart');
            var selectedAll = model.isSelectedAllCartItems();
            view.checkSelectedCartItem($cartTable, item, itemSelected);
            view.checkSelectedCartHeader($cartTable, selectedAll);
        },

        handleChangeAllCartItemsSelected: function(e) {
            var selectedAll = e.target.checked;
            model.updateAllCartItemsSelected(selectedAll);
            var $cartTable = $(e.target).parents('table.cart');
            view.checkSelectedAllCartItem($cartTable, selectedAll);
        },

        handleClearCart: function () {
            model.clearCart();
            viewModel.updateCartOnMenu();
            viewModel.updateCartOnCartPage();
        },

        init: function () {
            view.bindEvents();
            var cartData = model.getCartData();
            var selectedCartData = model.getSelectedCartData();

            // redirect to homepage when user in checkout page without cart-products
            if(location.href.includes('/dat-hang') && cartData.length === 0) return location.href = "/";

            var ids = [];
            for (var item of cartData) {
                ids.push(item.productId);
            }
            if(ids.length === 0) {
                view.renderCartOnCartPage(cartData);
                view.renderCartOnCheckoutPage(selectedCartData);
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

                model.saveCart(cartData);
                var selectedCartData = model.getSelectedCartData();
                view.renderCartOnCartPage(cartData);
                view.renderCartOnCheckoutPage(selectedCartData);
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