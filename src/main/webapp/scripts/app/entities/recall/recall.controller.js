'use strict';

angular.module('jeducenterApp')
    .controller('RecallController', function ($scope, $state, DataUtils, Recall) {

        $scope.recalls = [];
        $scope.loadAll = function() {
            Recall.query(function(result) {
               $scope.recalls = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.recall = {
                type: null,
                name: null,
                description: null,
                pathToFile: null,
                id: null
            };
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
    });
