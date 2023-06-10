angular.module('app').controller('profileController', function ($scope, $http, $localStorage, $rootScope, $window) {
    const contextPath = $rootScope.contextPath;

    $scope.updateUser = function () {
        if (!$scope.user || !$scope.user.firstName || $scope.user.firstName.length < 1
            || !$scope.user.lastName || $scope.user.lastName.length < 1
            || !$scope.user.email || $scope.user.email.length < 1) {
            alert('Заполнены не все обязательные поля!');
            return;
        }
        $http.put(contextPath + '/api/v1/users', $scope.user)
            .then(function (response) {
                if (response.data === 'BAD_GATEWAY') {
                    alert('Пользователь с email ' + $scope.user.email + ' уже зарегистрирован!');
                    return;
                }
                alert($scope.user.login + ', поздравляем! Вы успешно отредактировали профиль!');
            });
    }

    $scope.updatePassword = function () {
        if (!$scope.password || !$scope.password.currentPassword || $scope.password.currentPassword.length < 1
            || !$scope.password.newPassword || $scope.password.newPassword.length < 1
            || !$scope.newPassword || $scope.newPassword.length < 1) {
            alert('Заполнены не все обязательные поля!');
            return;
        }
        if ($scope.password.newPassword !== $scope.newPassword) {
            alert('Новые пароли не совпадают!')
            $scope.password.currentPassword = null;
            $scope.password.newPassword = null;
            $scope.newPassword = null;
            return;
        }
        $http.put(contextPath + '/api/v1/users/password', $scope.password)
            .then(function (response) {
                if (response.data === 'BAD_GATEWAY') {
                    alert('Неверный пароль!');
                    $scope.password.currentPassword = null;
                    $scope.password.newPassword = null;
                    $scope.newPassword = null;
                    return;
                }
                alert($scope.user.login + ', поздравляем! Вы успешно изменили пароль!');
                $scope.password.currentPassword = null;
                $scope.password.newPassword = null;
                $scope.newPassword = null;
            });
    }

    $scope.loadUser = function () {
        $http.get(contextPath + '/api/v1/users')
            .then(function (response) {
                $scope.user = response.data;
            });
    }

    $scope.loadUser();

});