angular.module('app').controller('createProductsController', function ($scope, $http, $localStorage, $rootScope, $window) {
    const contextPath = 'http://localhost:8189/nya';
    $scope.categoryList = null;
    $scope.chosenCategory = null;
    $scope.newProduct = null;

    $scope.createProduct = function () {
        if (!$scope.chosenCategory) {
            alert('You should choose the product category!')
        } else {
            $scope.newProduct.category = $scope.chosenCategory.title;
            $http.post(contextPath + '/api/v1/products', $scope.newProduct)
                .then(function (response) {
                    $scope.newProduct.id = response.data;
                    $rootScope.changingProduct = $scope.newProduct;
                    alert('Product ' + response.data + ' was created success');
                    $window.location.href = contextPath + '/#!/moderator/product/edit'
                });
        }
    }

    $scope.loadCategories = function () {
        $http.get(contextPath + '/api/v1/categories')
            .then(function (response) {
                $scope.categoryList = response.data;
            });
    }

    $scope.chooseCategory = function (category) {
        $scope.chosenCategory = category;
    }

    $scope.loadCategories();

});