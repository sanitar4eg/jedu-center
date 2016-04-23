'use strict';

angular.module('jeducenterApp')
    .controller('TimeTableController', function ($scope, $state, TimeTable) {

        $scope.timeTables = [];
        $scope.loadAll = function() {
            TimeTable.query(function(result) {
               $scope.timeTables = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.timeTable = {
                name: null,
                id: null
            };
        };
    });
