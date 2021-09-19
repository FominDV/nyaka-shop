angular.module('app').controller('editProductsController', function ($scope, $http, $localStorage, $rootScope, $window) {
    const contextPath = 'http://localhost:8189/nya';

    $scope.loadPage = function () {
        $scope.currentProduct = $rootScope.changingProduct;
    }

    $scope.loadPage();

});