(function ($localStorage) {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)
        .run(run);

    function config($routeProvider, $httpProvider) {

        $routeProvider
            .when('/main', {
                templateUrl: 'main/main.html',
                controller: 'mainController'
            })
            .when('/login', {
                templateUrl: 'login/login.html',
                controller: 'loginController'
            })
            .when('/product', {
                templateUrl: 'product/products.html',
                controller: 'productsController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            // .when('/orders', {
            //     templateUrl: 'orders/orders.html',
            //     controller: 'ordersController'
            // })
            .otherwise({
                redirectTo: '/'
            });

        $httpProvider.interceptors.push(function ($q, $location) {
            return {
                'responseError': function (rejection, $localStorage, $http) {
                    if (rejection.status == 401 || rejection.status == 403) {
                        location.href = 'http://localhost:8189/nya/#!/login';
                    }
                    var defer = $q.defer();
                    defer.reject(rejection);
                    return defer.promise;
                }
            };
        });
    }

    function run($rootScope, $http, $localStorage) {
        if ($localStorage.currentUser) {
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }
    }
})();

angular.module('app').controller('indexController', function ($rootScope, $scope, $http, $localStorage, $window) {
    const contextPath = 'http://localhost:8189/nya';

    $scope.clearUser = function () {
        delete $localStorage.currentUser;
        $localStorage.roles = null;
        $http.defaults.headers.common.Authorization = '';
        $window.location.href = contextPath + '/#!/main'
    };

    $rootScope.isUserLoggedIn = function () {
        if ($http.defaults.headers.common.Authorization != '' && $localStorage.roles && $localStorage.roles[0]) {
            return true;
        } else {
            return false;
        }
    };

    $rootScope.isModerator = function () {
        return $http.defaults.headers.common.Authorization != '' && $localStorage.roles && $localStorage.roles.indexOf('ROLE_MODERATOR') != -1;
    }

    $rootScope.isUser = function () {
        return $http.defaults.headers.common.Authorization != '' && $localStorage.roles && $localStorage.roles.indexOf('ROLE_USER') != -1;
    }


    $window.location.href = '#!/main'

});