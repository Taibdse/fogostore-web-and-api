

var shopModule = (function () {
   var model = {

   }

   var view = {}

   var viewModel = {
       init: function () {
           for(var i = 0; i < $btnSelects.length; i++){
               $btnSelects.eq(i).on('click', function (e) {
                   $(e.target).parent('product-attribute-select').find('.btn-select.active').removeClass('active');
                   $(e.target).addClass('active');
               })
           }
       }
   }

    return {
       model: model,
        view: view,
        viewModel: viewModel
    }
})()

shopModule.viewModel.init();