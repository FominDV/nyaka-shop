angular.module('app').controller('editProductsController', function ($scope, $http, $localStorage, $rootScope, $window) {
    const contextPath = $rootScope.contextPath;
    $scope.fd = null;

    $scope.loadPage = function () {
        $scope.currentProduct = $rootScope.changingProduct;
        $scope.getImage();
    }

    $scope.edit = function () {
        if(!$scope.currentProduct.price || $scope.currentProduct.price<1 || !$scope.currentProduct.categories || !$scope.currentProduct.categories[0]){
            alert('Заполнены не все обязательные атрибуты');
            return;
        }
        $http.put(contextPath + '/api/v1/products', $scope.currentProduct)
            .then(function (response) {
                alert('Атрибуты товара были изменены');
                $window.location.href = contextPath + '/#!/product'
            });
    }

    $rootScope.selectFile = function (files) {
        $scope.fd = new FormData();
        $scope.fd.append("file", files[0]);
        $scope.fd.append("id", $scope.currentProduct.id);
        $http({
            url: contextPath + '/api/v1/resources',
            method: 'POST',
            withCredentials: true,
            headers: {'Content-Type': undefined},
            transformRequest: angular.identity,
            data: $scope.fd
        }).then(function (response) {
            alert('Изображение было успешно изменено')
            $scope.currentProduct.imageUrl = response.data.text;
            $scope.getImage();
        });
    }

    $scope.getImage = function () {
        $http({
            url: contextPath + '/api/v1/products/image',
            method: 'GET',
            params: {
                'productId': $scope.currentProduct.id
            }
        }).then(function (response) {
            $scope.currentProduct.imageUrl = response.data;
           // $scope.image = 'img/not_found.jpg';
        });
    };

    $scope.loadCategories = function () {
        $http.get(contextPath + '/api/v1/categories')
            .then(function (response) {
                $scope.categoryList = response.data;
            });
    }

    $scope.chooseCategory = function (category) {
        $scope.chosenCategory = category;
    }

    $scope.addCategory = function () {
        if(!$scope.chosenCategory){
            alert('Категория не выбрана!')
            return;
        }
        if($scope.currentProduct.categories.filter(c=>c.id == $scope.chosenCategory.id)[0]){
            alert('Данная категория уже была добавлена');
        }else {
            $scope.currentProduct.categories.push($scope.chosenCategory);
        }
    }

    $scope.removeCategory = function () {
        // alert($scope.chosenCategoryList.indexOf($scope.chosenCategory));
        if(!$scope.chosenCategory){
            alert('Категория не выбрана!')
            return;
        }
        if(!$scope.currentProduct.categories.filter(c=>c.id == $scope.chosenCategory.id)[0]){
            alert('Данная категория не была добавлена');
        }else {
            $scope.currentProduct.categories.splice($scope.currentProduct.categories.indexOf($scope.currentProduct.categories.filter(c=>c.id == $scope.chosenCategory.id)[0]),1);
        }
    }

    $scope.loadPage();
    $scope.loadCategories();

});