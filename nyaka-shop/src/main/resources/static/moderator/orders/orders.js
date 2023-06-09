angular.module('app').controller('ordersEditController', function ($scope, $http, $localStorage, $rootScope, $window) {
    const contextPath = $rootScope.contextPath;

    $scope.loadOrders = function (pageIndex = 1) {
        $scope.pageIndex = pageIndex;
        $http({
            url: contextPath + '/api/v1/orders/filter',
            method: 'GET',
            params: {
                'page': pageIndex,
                'status' : $scope.chosenStatus
            }
        }).then(function (response) {
            $scope.orders = response.data.content;
            $scope.navList = $rootScope.generatePagesIndexes(1, response.data.totalPages);
            $scope.getOrderDetails(response.data.content[0])
            //$scope.renderPaymentButtons();
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

    $scope.loadStatuses = function () {
        $http.get(contextPath + '/api/v1/status')
            .then(function (response) {
                $scope.statusList = response.data;
            });
        // alert($scope.statusList[0].statusName);
        // alert($scope.statusList[0].dsName);
    }

    $scope.chooseStatus = function (status) {
        $scope.chosenStatus = status.statusName;
    }

    $scope.clearFilter = function () {
        $scope.chosenStatus = null;
    }

    $scope.getButtonChangeName = function (order) {
        if(order.status == 'NEW'){
            return 'Собрать';
        }
        if(order.status == 'COLLECTING'){
            return 'Доставить';
        }
        if(order.status == 'DELIVERING'){
            return 'Завершить';
        }
    }

    $scope.showChangeButton = function (order) {
        if(order.status == 'FINISHED' || order.status == 'CANCELED'){
            return false;
        }
        return true;
    }

    $scope.showCancelButton = function (order) {
        if(order.status == 'FINISHED' || order.status == 'CANCELED'){
            return false;
        }
        return true;
    }

    $scope.cancelOrder = function (order) {
        if(order.status == 'FINISHED' || order.status == 'CANCELED'){
            alert("Невозможно отменить заказ");
            return;
        }
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'DELETE',
            params: {
                'orderId' : order.id
            }
        }).then(function successCallback(response) {
            alert('Заказ № ' +order.id +' был отменён');
            $scope.loadOrders();
        }, function errorCallback(response) {

        });
    }

    $scope.changeStatus = function (order) {
        if(order.status == 'FINISHED' || order.status == 'CANCELED'){
            alert("Невозможно изменить статус заказа");
            return;
        }
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'PUT',
            params: {
                'orderId' : order.id
            }
        }).then(function successCallback(response) {
            alert('Статус заказа № ' +order.id +' был изменён на ' + response.data.dsName);
            $scope.loadOrders();
            }, function errorCallback(response) {

            });
    }

    $scope.loadOrders();
    $scope.loadStatuses();
});