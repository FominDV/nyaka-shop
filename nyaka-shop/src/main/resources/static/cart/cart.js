angular.module('app').controller('cartController', function ($rootScope, $scope, $http, $localStorage, $window) {
    const contextPath = $rootScope.contextPath;

    $scope.loadCart = function () {
        $scope.cart=null;
        $http({
            url: contextPath + '/api/v1/cart',
            method: 'GET'
        }).then(function (response) {
            $scope.cart = response.data;
        });
    }

    $scope.incrementCartPosition = function (product) {
        if(product.quentity ==0){
            alert('Нет доступного товара');
            return;
        }
        $http({
            url: contextPath + '/api/v1/cart/add/' + product.id,
            method: 'GET'
        }).then(function (response) {
            $scope.loadCart();
        });
    }

    $scope.decrementCartPosition = function (product) {
        $http({
            url: contextPath + '/api/v1/cart/decrement/' + product.id,
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
            if(!response.data){
                alert('Корзина пуста!');
                $scope.loadCart();
                return;
            }
            alert('Заказ был успешно создан');
            $scope.loadCart();
            $window.location.href = contextPath + '/#!/orders'
        }, function errorCallback(response) {
            alert('Поля заполнены некорректно!');
            $scope.order_info = null;
        });
    }

    $scope.cartIsFull=function (){
        return $scope.cart && $scope.cart.items[0];
    }

    $scope.loadCart();
});