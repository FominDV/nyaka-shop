angular.module('app').controller('shipmentsController', function ($scope, $http, $localStorage, $rootScope, $window) {

    const contextPath = $rootScope.contextPath;

    $scope.loadShipments = function (pageIndex = 1) {
        $scope.pageIndexShipment = pageIndex;
        if($scope.shipment) {$scope.shipment.quantity = 0;}
        $http({
            url: contextPath + '/api/v1/shipments',
            method: 'GET',
            params: {
                'pageIndex': pageIndex,
                'productId': $rootScope.changingProduct.id
            }
        }).then(function (response) {
            $scope.shipmentPage = response.data;
            $scope.navListShipment = $rootScope.generatePagesIndexes(1, $scope.shipmentPage.totalPages);
        });
    };

    $scope.isCurrentIndex = function (pageIndex) {
        return $scope.pageIndexShipment == pageIndex;
    }

    $scope.createShipment = function () {
        if(!$scope.shipment || !$scope.shipment.quantity || $scope.shipment.quantity == 0){
            alert('Необходимо указать количество поступления не равное нулю');
            return;
        }
        $scope.shipment.productId = $rootScope.changingProduct.id;
            $http.post(contextPath + '/api/v1/shipments', $scope.shipment)
                .then(function (response) {
                    alert('Поступление товара ' + $rootScope.changingProduct.title + ' в количестве ' + $scope.shipment.quantity + ' создано');
                    $scope.loadShipments();
                });
        }

    $scope.loadShipments();

});