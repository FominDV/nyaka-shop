angular.module('app').controller('loginController', function ($rootScope, $scope, $http, $localStorage, $window) {
    const contextPath = 'http://localhost:8189/nya';

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.currentUser = {email: $scope.user.email, token: response.data.token};

                    $scope.user.email = null;
                    $scope.user.password = null;
                    $window.location.href= contextPath + '/#!/main'
                }
            }, function errorCallback(response) {
            });
    };

});