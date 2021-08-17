angular.module('app').controller('cartController', function ($scope, $http, $localStorage, $window) {
    const contextPath = 'http://localhost:8189/nya';

    $scope.loadCart = function () {
        $http({
            url: contextPath + '/api/v1/cart',
            method: 'GET'
        }).then(function (response) {
            $scope.cart = response.data;
        });
    }

    $scope.incrementCartPosition = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/add/' + productId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.decrementCartPosition = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/decrement/' + productId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.clearCart = function () {
        $http({
            url: contextPath + '/api/v1/cart/clear',
            method: 'GET'
        }).then(function (response) {
            $scope.cart = null;
        });
    }

    $scope.removeItemFromCart = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/remove/' + productId,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.createOrder = function () {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'POST',
            params: {
                phone: $scope.order_info.phone,
                address: $scope.order_info.address
            }
        }).then(function successCallback(response) {
            alert('Order was created');
            $scope.loadCart();
            $window.location.href = contextPath + '/#!/orders'
        }, function errorCallback(response) {
            alert('The fields are filled in incorrectly');
            $scope.order_info = null;
        });
    }

    $scope.cartIsFull=function (){
        return $scope.cart && $scope.cart.items[0];
    }

    $scope.loadCart();
});