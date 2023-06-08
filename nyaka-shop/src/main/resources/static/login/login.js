angular.module('app').controller('loginController', function ($rootScope, $scope, $http, $localStorage, $window) {
    const contextPath = $rootScope.contextPath;

    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $scope.setRoles(response.data.token);
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.currentUser = {username: $scope.user.email, token: response.data.token};
                    $scope.user.email = null;
                    $scope.user.password = null;
                    $window.location.href = contextPath + '/#!/main'
                }
            }, function errorCallback(response) {
                alert('Неверный логин или пароль');
                $scope.clearUser();
                $scope.user.email = null;
                $scope.user.password = null;
            });
    };

    $scope.setRoles = function (jwt) {
        let jwtData = jwt.split('.')[1];
        let decodedJwtJsonData = $window.atob(jwtData);
        let decodedJwtData = JSON.parse(decodedJwtJsonData)
        $localStorage.roles = decodedJwtData.roles;
    }

    $scope.clearUser = function () {
        delete $localStorage.currentUser;
        $localStorage.roles = null;
        $http.defaults.headers.common.Authorization = '';
    }

    $scope.clearUser();
});