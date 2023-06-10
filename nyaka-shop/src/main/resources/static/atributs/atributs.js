angular.module('app').controller('atributsController', function ($scope, $http, $localStorage, $rootScope, $window) {
    const contextPath = $rootScope.contextPath;
    $scope.categoryList = null;
    $scope.brandList = null;
    $scope.countryList = null;

    $scope.createBrand = function () {
        if ($scope.newBrand == null || $scope.newBrand.length < 1) {
            alert('Введите название нового бренда!');
            $scope.newBrand = null;
            return;
        } else {
            $http.post(contextPath + '/api/v1/brands', $scope.newBrand)
                .then(function (response) {
                    if (response.data === 'BAD_REQUEST') {
                        alert('Бренд ' + $scope.newBrand + ' уже существует!');
                        $scope.newBrand = null;
                        return;
                    }
                    alert('Бренд ' + $scope.newBrand + ' был успешно создан');
                    $scope.loadBrands();
                    $scope.newBrand = null;
                });
        }
    }

    $scope.removeBrand = function () {
        if (!$scope.chosenBrand) {
            alert('Не выбран бренд для удаления!');
            return;
        } else {
            $http({
                url: contextPath + '/api/v1/brands',
                method: 'DELETE',
                params: {
                    'brandId': $scope.chosenBrand.id
                }
            }).then(function (response) {
                if (response.data === 'BAD_REQUEST') {
                    alert('Бренд ' + $scope.chosenBrand.title + ' относится к некоторым товарам! Удаление невозможно!');
                    $scope.chosenBrand = null;
                    return;
                }
                alert('Бренд ' + $scope.chosenBrand.title + ' был успешно удалён');
                $scope.loadBrands();
                $scope.chosenBrand = null;
            });
        }
    }

    $scope.createCountry = function () {
        if ($scope.newCountry == null || $scope.newCountry.length < 1) {
            alert('Введите название новой страны-производителя!');
            $scope.newCountry = null;
            return;
        } else {
            $http.post(contextPath + '/api/v1/countries', $scope.newCountry)
                .then(function (response) {
                    if (response.data === 'BAD_REQUEST') {
                        alert('Страна-производитель ' + $scope.newCountry + ' уже существует!');
                        $scope.newCountry = null;
                        return;
                    }
                    alert('Страна-производитель ' + $scope.newCountry + ' была успешно создана');
                    $scope.loadCountries();
                    $scope.newCountry = null;
                });
        }
    }

    $scope.removeCountry = function () {
        if (!$scope.chosenCountry) {
            alert('Не выбрана страна-производитель для удаления!');
            return;
        } else {
            $http({
                url: contextPath + '/api/v1/countries',
                method: 'DELETE',
                params: {
                    'countryId': $scope.chosenCountry.id
                }
            }).then(function (response) {
                if (response.data === 'BAD_REQUEST') {
                    alert('Страна-производитель ' + $scope.chosenCountry.title + ' относится к некоторым товарам! Удаление невозможно!');
                    $scope.chosenCountry = null;
                    return;
                }
                alert('Страна-производитель ' + $scope.chosenCountry.title + ' была успешно удалена');
                $scope.loadCountries();
                $scope.chosenCountry = null;
            });
        }
    }

    $scope.createCategory = function () {
        if ($scope.newCategory == null || $scope.newCategory.length < 1) {
            alert('Введите название новой категории!');
            $scope.newCategory = null;
            return;
        } else {
            $http.post(contextPath + '/api/v1/categories', $scope.newCategory)
                .then(function (response) {
                    if (response.data === 'BAD_REQUEST') {
                        alert('Категория ' + $scope.newCategory + ' уже существует!');
                        $scope.newCategory = null;
                        return;
                    }
                    alert('Категория ' + $scope.newCategory + ' была успешно создана');
                    $scope.loadCategories();
                    $scope.newCategory = null;
                });
        }
    }

    $scope.removeCategory = function () {
        if (!$scope.chosenCategory) {
            alert('Не выбрана категория для удаления!');
            return;
        } else {
            $http({
                url: contextPath + '/api/v1/categories',
                method: 'DELETE',
                params: {
                    'categoryId': $scope.chosenCategory.id
                }
            }).then(function (response) {
                if (response.data === 'BAD_REQUEST') {
                    alert('Категория ' + $scope.chosenCategory.title + ' относится к некоторым товарам! Удаление невозможно!');
                    $scope.chosenCategory = null;
                    return;
                }
                alert('Категория ' + $scope.chosenCategory.title + ' была успешно удалена');
                $scope.loadCategories();
                $scope.chosenCategory = null;
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

    $scope.loadCategories();
    $scope.loadBrands();
    $scope.loadCountries();

});