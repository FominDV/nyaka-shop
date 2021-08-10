(function ($localStorage) {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider) {
        $routeProvider
            // .when('/', {
            //     templateUrl: 'index.html',
            //     controller: 'indexController'
            // })
            .when('/login', {
                templateUrl: 'login/login.html',
                controller: 'loginController'
            } )
            // .when('/products', {
            //     templateUrl: 'products/products.html',
            //     controller: 'productsController'
            // })
            // .when('/cart', {
            //     templateUrl: 'cart/cart.html',
            //     controller: 'cartController'
            // })
            // .when('/orders', {
            //     templateUrl: 'orders/orders.html',
            //     controller: 'ordersController'
            // })
            .otherwise({
                redirectTo: '/'
            });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
    }
})();

angular.module('app').controller('indexController', function ($rootScope, $scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/nya/api/v1';

    $scope.clearUser = function () {
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
    };

    $rootScope.isUserLoggedIn = function () {
        if ($localStorage.currentUser) {
            return true;
        } else {
            return false;
        }
    };
});