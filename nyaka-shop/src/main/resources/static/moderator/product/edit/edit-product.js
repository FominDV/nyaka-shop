angular.module('app').controller('editProductsController', function ($scope, $http, $localStorage, $rootScope, $window) {
    const contextPath = $rootScope.contextPath;
    $scope.fd = null;

    $scope.loadPage = function () {
        $scope.currentProduct = $rootScope.changingProduct;
    }

    $scope.edit = function () {
        $http.put(contextPath + '/api/v1/products', $scope.currentProduct)
            .then(function (response) {
                alert('Product was changed success');
                $window.location.href = contextPath + '/#!/product'
            });
    }

    $rootScope.selectFile = function (files) {
        $scope.fd = new FormData();
        $scope.fd.append("file", files[0]);
        $scope.fd.append("id", $scope.currentProduct.id);
        $http({
            url: contextPath + '/api/v1/resources',
            method: 'POST',
            withCredentials: true,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity,
            data: $scope.fd
        }).then(function (response) {
            alert('Image of product was changed success')
            $scope.currentProduct.imageUrl = response.data.text;
        });
    }

    $scope.loadPage();

});