angular.module('app').controller('ordersController', function ($scope, $http, $localStorage, $rootScope) {
    const contextPath = 'http://localhost:8189/nya/api/v1/orders/';

    $scope.loadOrders = function (pageIndex = 1) {
        $scope.pageIndex = pageIndex;
        $http({
            url: contextPath,
            method: 'GET',
            params: {
                'page': pageIndex,
            }
        }).then(function (response) {
            $scope.orders = response.data.content;
            $scope.navList = $rootScope.generatePagesIndexes(1, response.data.totalPages);
            $scope.getOrderDetails(response.data.content[0])
        });
    }

    $scope.getOrderDetails = function (order) {
        if (order) {
            $http.get(contextPath + order.id + '/items')
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

    $scope.loadOrders();
});