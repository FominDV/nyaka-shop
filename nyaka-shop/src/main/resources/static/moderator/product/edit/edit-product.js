angular.module('app').controller('editProductsController', function ($scope, $http, $localStorage, $rootScope, $window) {
    const contextPath = 'http://localhost:8189/nya';

    $scope.loadPage = function () {
        $scope.currentProduct = $rootScope.changingProduct;
    }

    $scope.edit=function (){
        $http.put(contextPath + '/api/v1/products', $scope.currentProduct)
            .then(function (response) {
            alert('Product was changed success');
            $window.location.href = contextPath + '/#!/product'
        });
    }

    $scope.loadPage();

});