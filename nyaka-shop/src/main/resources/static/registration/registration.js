angular.module('app').controller('registrationController', function ($scope, $http, $localStorage, $rootScope, $window) {
    const contextPath = $rootScope.contextPath;

    $scope.createUser = function () {
        if (!$scope.newUser || !$scope.newUser.firstName || $scope.newUser.firstName.length < 1
            || !$scope.newUser.lastName || $scope.newUser.lastName.length < 1
            || !$scope.newUser.email || $scope.newUser.email.length < 1
            || !$scope.newUser.login || $scope.newUser.login < 1
            || !$scope.newUser.password || $scope.newUser.password < 1
            || !$scope.newUser.password2 || $scope.newUser.password2 < 1) {
            alert('Заполнены не все обязательные поля!');
            return;
        }
        if ($scope.newUser.password !== $scope.newUser.password2) {
            alert('Пароли не совпадают!')
            $scope.newUser.password = null;
            $scope.newUser.password2 = null;
            return;
        }
        $http.post(contextPath + '/api/v1/users', $scope.newUser)
            .then(function (response) {
                if (response.data === 'BAD_REQUEST') {
                    alert('Логин ' + $scope.newUser.login + ' уже зарегистрирован!')
                    return;
                }
                if (response.data === 'BAD_GATEWAY') {
                    alert('Пользователь с email ' + $scope.newUser.email + ' уже зарегистрирован!')
                    return;
                }
                alert($scope.newUser.login + ', поздравляем! Вы успешно зарегистрированы!')
                $window.location.href = contextPath + '/#!/login'
            });
    }

});