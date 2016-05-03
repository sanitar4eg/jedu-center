'use strict';

angular.module('jeducenterApp')
    .controller('OperationResultController', function ($scope, $stateParams) {
        $scope.results = $stateParams.results;

    });
