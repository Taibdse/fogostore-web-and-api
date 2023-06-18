var orderKey = 'fogostore-customer'

var orderModule = (function () {
    var model = {
        getCustomerInfo: function () {
            var json = localStorage.getItem(orderKey);
            if(ValidationUtils.isEmpty(json)) return {}
            try{
                return JSON.parse(json);
            } catch (err) {
                return {}
            }
        },
        saveCustomerInfo: function (customer) {
            localStorage.setItem(orderKey, JSON.stringify(customer));
        },
    };

    var view = {
        bindEvents: function (){
            $('#btnOrder').on('click', viewModel.handleSubmitOrder);
            $('.form-field').on('input', viewModel.handleChangeCustomInfo);

            $('.payment-methods').on('change', viewModel.handleChangePaymentMethod)
        },
        renderCustomerInfo: function (customer){
            $('#customerFullname').val(customer.customerFullname);
            $('#customerEmail').val(customer.customerEmail);
            $('#customerPhone').val(customer.customerPhone);
            $('#customerAddress').val(customer.customerAddress);
            $('#customerNote').val(customer.customerNote);
            $('.payment-methods').first().find('input').prop('checked', true);
        },
        getCustomerInfo: function (){
            var customerFullname = $('#customerFullname').val();
            var customerEmail = $('#customerEmail').val();
            var customerPhone = $('#customerPhone').val();
            var customerAddress = $('#customerAddress').val();
            var customerNote = $('#customerNote').val();
            var paymentMethodId = $('.payment-methods').parent().find('input:checked').val();

            var order = {
                customerFullname: customerFullname,
                customerPhone: customerPhone,
                customerAddress: customerAddress,
                customerEmail: customerEmail,
                customerNote: customerNote,
                paymentMethodId: paymentMethodId,
            }

            return order;

        }
    };

    var viewModel = {
        processSubmitOrder: function (){
            cartModule.view.toggleLoader(true);
            var order = view.getCustomerInfo();

            var orderDetails = [];
            cartModule.model.getCartData().forEach(function (cartItem){
                orderDetails.push({
                    productId: cartItem.productId,
                    quantity: StringUtils.getNumber(cartItem.quantity),
                    productTypeId: cartItem.productTypeId,
                })
            })
            if(orderDetails.length == 0) return location.href = '/';

            order.orderDetails = orderDetails;

            orderService.submitOrder(order, function (res){
                cartModule.view.toggleLoader(false);
                if(res.success){
                    AlertUtils.showAlert({
                        title: constants.order_success_title,
                        content: constants.order_fail_content,
                        type: 'green'
                    })

                    setTimeout(function (){
                        cartModule.viewModel.handleClearCart();
                        delete order.orderDetails;
                        model.saveCustomerInfo(order)
                        // location.href = '/don-hang/' + res.data.id;
                        location.href = '/';
                    }, 1000);
                } else {
                    var errorContent = ''
                    for(var error of Object.values(res.errors)){
                        errorContent += error + '<br/>';
                    }
                    AlertUtils.showAlert({
                        title: constants.order_fail_title,
                        content: errorContent,
                        type: 'red'
                    })
                }

            }, function (err){
                console.log(err);
                cartModule.view.toggleLoader(false);
                AlertUtils.showAlert({
                    title: constants.order_fail_title,
                    content: constants.server_error,
                    type: 'red'
                })
            })
        },

        handleSubmitOrder: function (e){
            e.preventDefault();
            AlertUtils.showConfirm({
                title: '',
                content: constants.confirm_order
            }, function () {
                viewModel.processSubmitOrder();
            }, function () {

            });
        },
       
        handleChangeCustomInfo: function (e) {
            const customer = view.getCustomerInfo();
            model.saveCustomerInfo(customer);
        },

        init: function (){
            view.bindEvents();
            var customerInfo = model.getCustomerInfo();
            view.renderCustomerInfo(customerInfo)
        },
    }


    return {
        view: view,
        model: model,
        viewModel: viewModel
    }
})()

orderModule.viewModel.init();
