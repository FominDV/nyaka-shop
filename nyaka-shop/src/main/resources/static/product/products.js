angular.module('app').controller('productsController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/nya';

    $scope.addToCart = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/add/' + productId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.loadPage = function (pageIndex = 1, minPrice, maxPrice) {
        $http({
            url: contextPath + '/api/v1/products/',
            method: 'GET',
            params: {
                page: pageIndex,
                min: minPrice,
                max: maxPrice
            }
        }).then(function (response) {
            $scope.productsPage = response.data;
            $scope.navList = $scope.generatePagesIndexes(1, $scope.productsPage.totalPages);
        });
    };

    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

    $scope.loadPage();
});