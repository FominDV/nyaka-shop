angular.module('app').controller('createProductsController', function ($scope, $http, $localStorage, $rootScope, $window) {
    const contextPath = $rootScope.contextPath;
    $scope.categoryList = null;
    $scope.brandList = null;
    $scope.countryList = null;
    $scope.chosenCategoryList = [];
    $scope.newProduct = null;

    $scope.createProduct = function () {
        if($scope.newProduct.barcode.match(/^[0-9]+$/)==null||$scope.newProduct.barcode.length !== 13){
            alert('Штрихкод должен содержать 13 цифр!')
            return;
        }
        if (!$scope.chosenCountry || !$scope.chosenBrand || $scope.chosenCategoryList.length == 0 ||!$scope.newProduct.title||$scope.newProduct.title.length<1||!$scope.newProduct.price||$scope.newProduct.price<1) {
            alert('Заполнены не все атрибуты!')
        } else {
            $scope.newProduct.categories = $scope.chosenCategoryList;
            $scope.newProduct.brand=$scope.chosenBrand;
            $scope.newProduct.country=$scope.chosenCountry;
            $http.post(contextPath + '/api/v1/products', $scope.newProduct)
                .then(function (response) {
                    $scope.newProduct.id = response.data;
                    $rootScope.changingProduct = $scope.newProduct;
                    alert('Продукт ' + response.data + ' был создан');
                    $window.location.href = contextPath + '/#!/moderator/product/edit'
                });
        }
    }

    $scope.loadCategories = function () {
        $http.get(contextPath + '/api/v1/categories')
            .then(function (response) {
                $scope.categoryList = response.data;
            });
    }

    $scope.loadBrands = function () {
        $http.get(contextPath + '/api/v1/brands')
            .then(function (response) {
                $scope.brandList = response.data;
            });
    }

    $scope.loadCountries = function () {
        $http.get(contextPath + '/api/v1/countries')
            .then(function (response) {
                $scope.countryList = response.data;
            });
    }

    $scope.chooseCategory = function (category) {
        $scope.chosenCategory = category;
    }

    $scope.chooseBrand = function (brand) {
        $scope.chosenBrand = brand;
    }

    $scope.chooseCountry = function (country) {
        $scope.chosenCountry = country;
    }

    $scope.addCategory = function () {
        if(!$scope.chosenCategory){
            alert('Категория не выбрана!')
            return;
        }
        if($scope.chosenCategoryList.indexOf($scope.chosenCategory)>=0){
            alert('Данная категория уже была добавлена');
        }else {
            $scope.chosenCategoryList.push($scope.chosenCategory);
        }
    }

    $scope.removeCategory = function () {
        // alert($scope.chosenCategoryList.indexOf($scope.chosenCategory));
        if(!$scope.chosenCategory){
            alert('Категория не выбрана!')
            return;
        }
        if($scope.chosenCategoryList.indexOf($scope.chosenCategory)<0){
            alert('Данная категория не была добавлена');
        }else {
            $scope.chosenCategoryList.splice($scope.chosenCategoryList.indexOf($scope.chosenCategory),1);
        }
    }

    $scope.loadCategories();
    $scope.loadBrands();
    $scope.loadCountries();

});