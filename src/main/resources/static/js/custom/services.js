var productService = (function () {

    function getProductsInCart(ids, onSuccess, onError){
        $.ajax({
            method: 'GET',
            url: '/api/products/end-user/products-in-cart?ids=' + ids,
            success: onSuccess,
            error: onError
        })
    }

    return {
        getProductsInCart: getProductsInCart
    }

})();

var orderService = (function () {
    function submitOrder(order, onSuccess, onError){
        $.ajax({
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            type: 'POST',
            url: '/api/orders/end-user/submit',
            data: JSON.stringify(order),
            success: onSuccess,
            error: onError
        })
    }

    return {
        submitOrder: submitOrder
    }
})();