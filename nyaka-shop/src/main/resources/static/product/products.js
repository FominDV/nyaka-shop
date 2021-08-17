angular.module('app').controller('productsController', function ($scope, $http, $localStorage, $rootScope) {
    const contextPath = 'http://localhost:8189/nya';

    $scope.addToCart = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/add/' + productId,
            method: 'GET'
        }).then(function (response) {

        });
    }

    $scope.clearFilter = function () {
        $scope.filter = null;
    }

    $scope.loadPage = function (pageIndex = 1) {
        $scope.pageIndex = pageIndex;
        $http({
            url: contextPath + '/api/v1/products',
            method: 'GET',
            params: {
                'page': pageIndex,
                'min': $scope.filter ? $scope.filter.min : null,
                'max': $scope.filter ? $scope.filter.max : null,
                'title': $scope.filter ? $scope.filter.title : null
            }
        }).then(function (response) {
            $scope.productsPage = response.data;
            $scope.navList = $rootScope.generatePagesIndexes(1, $scope.productsPage.totalPages);
        });
    };

    $scope.isCurrentIndex = function (pageIndex) {
        return $scope.pageIndex == pageIndex;
    }

    $scope.loadPage();
});