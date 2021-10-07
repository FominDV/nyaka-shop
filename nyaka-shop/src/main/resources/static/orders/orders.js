angular.module('app').controller('ordersController', function ($scope, $http, $localStorage, $rootScope, $window) {
    const contextPath = 'http://localhost:8189/nya';

    $scope.loadOrders = function (pageIndex = 1) {
        $scope.pageIndex = pageIndex;
        $http({
            url: contextPath + '/api/v1/orders/',
            method: 'GET',
            params: {
                'page': pageIndex,
            }
        }).then(function (response) {
            $scope.orders = response.data.content;
            $scope.navList = $rootScope.generatePagesIndexes(1, response.data.totalPages);
            $scope.getOrderDetails(response.data.content[0])
            $scope.renderPaymentButtons();
        });
    }

    $scope.getOrderDetails = function (order) {
        if (order) {
            $http.get(contextPath + '/api/v1/orders/' + order.id + '/items')
                .then(function (response) {
                    $scope.order = order;
                    $scope.items = response.data;
                });
        }
    }

    $scope.hasOrder = function () {
        return $scope.order;
    }

    $scope.hasOrders = function () {
        return $scope.orders && $scope.orders[0];
    }

    $scope.isCurrentOrder = function (orderId) {
        return $scope.order && $scope.order.id == orderId;
    }

    $scope.isCurrentIndex = function (pageIndex) {
        return $scope.pageIndex == pageIndex;
    }

    $scope.renderPaymentButtons = function () {
        paypal.Buttons({
            createOrder: function (data, actions) {
                if ($scope.order.status == 'PAID') {
                    alert('Order was already paid!')
                    return;
                }
                return fetch(contextPath + '/api/v1/paypal/create/' + $scope.order.id, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function (response) {
                    return response.text();
                });
            },

            onApprove: function (data, actions) {
                return fetch(contextPath + '/api/v1/paypal/capture/' + data.orderID, {
                    method: 'post',
                    headers: {
                        'content-type': 'application/json'
                    }
                }).then(function (response) {
                    alert("Order was paid success!")
                    $window.location.href = contextPath + '/#!/product';
                });
            },

            onCancel: function (data) {
                console.log("Order canceled: " + data);
            },

            onError: function (err) {
                console.log(err);
            }
        }).render('#paypal-buttons');
    }

    $scope.loadOrders();
});