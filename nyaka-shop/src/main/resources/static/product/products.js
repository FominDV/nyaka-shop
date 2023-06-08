angular.module('app').controller('productsController', function ($scope, $http, $localStorage, $rootScope, $window) {
    const contextPath = $rootScope.contextPath;

    $scope.addToCart = function (productId) {
        $http({
            url: contextPath + '/api/v1/cart/add/' + productId,
            method: 'GET'
        }).then(function (response) {
            if($scope.productsPage.content.filter(p => p.id == productId)[0].quentity<=0) {alert('Доступный товар отсутствует');}
            else
            {$scope.productsPage.content.filter(p => p.id == productId)[0].quentity = $scope.productsPage.content.filter(p => p.id == productId)[0].quentity -1;}
        }, function errorCallback(response) {
            alert('Доступный товар отсутствует');
        });
    }

    $scope.clearFilter = function () {
        $scope.filter = null;
        $scope.chosenCategory = null;
        $scope.chosenBrand = null;
    }

    $scope.loadPage = function (pageIndex = 1) {
        $scope.pageIndex = pageIndex;
        $http({
            url: contextPath + '/api/v1/products',
            method: 'GET',
            params: {
                'page': pageIndex,
                'min': $scope.filter ? $scope.filter.min : null,
                'max': $scope.filter ? $scope.filter.max : null,
                'title': $scope.filter ? $scope.filter.title : null,
                'brandId': $scope.chosenBrand ? $scope.chosenBrand.id : null,
                'categoryId': $scope.chosenCategory ? $scope.chosenCategory.id : null
            }
        }).then(function (response) {
            $scope.feedback=null;
            $scope.productsPage = response.data;
            $scope.navList = $rootScope.generatePagesIndexes(1, $scope.productsPage.totalPages);
            $scope.deteilsProduct = $scope.productsPage.content ? $scope.productsPage.content[0] : null;
            $scope.loadFeedbacks();
        });
    };

    $scope.isCurrentIndex = function (pageIndex) {
        return $scope.pageIndex == pageIndex;
    }

    $scope.moveToEdit = function (product) {
        $rootScope.changingProduct = product;
        $window.location.href = contextPath + '/#!/moderator/product/edit'
    }

    $rootScope.getImageUrl = function (product) {
        return product.imageUrl ? product.imageUrl : 'img/not_found.jpg';
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

    $scope.chooseCategory = function (category) {
        $scope.chosenCategory = category;
    }

    $scope.chooseBrand = function (brand) {
        $scope.chosenBrand = brand;
    }

    $scope.deteils = function (p) {
        $scope.deteilsProduct = p;
        $scope.loadFeedbacks();
        $scope.feedback=null;
    }

    $scope.isCurrentIndexFeedbacks = function (pageIndex) {
        return $scope.pageIndexFeedback == pageIndex;
    }

    $scope.loadFeedbacks = function (pageIndex = 1) {
        $scope.pageIndexFeedback = pageIndex;
        $http({
            url: contextPath + '/api/v1/feedbacks',
            method: 'GET',
            params: {
                'pageIndex': pageIndex,
                'productId': $scope.deteilsProduct ? $scope.deteilsProduct.id : null
            }
        }).then(function (response) {
            $scope.feedback=null;
            $scope.feedbacksPage = response.data;
            $scope.navListFeedback = $rootScope.generatePagesIndexes(1, $scope.feedbacksPage.totalPages);
        });
    };

    $scope.sendFeedback = function () {
        if(!$scope.feedback||!$scope.deteilsProduct) {alert('Отзыв не заполнен!');return;}
        $http({
            url: contextPath + '/api/v1/feedbacks/create',
            method: 'GET',
            params: {
                'text': $scope.feedback,
                'productId': $scope.deteilsProduct.id
            }
        }).then(function (response) {
            $scope.feedback=null;
            $scope.loadFeedbacks();
        });
    };

    $scope.canComment = function () {
        return $scope.isUser() && $scope.deteilsProduct.isBoughtByUser;
    }

    $scope.loadCategories();
    $scope.loadBrands();
    $scope.loadPage();
});