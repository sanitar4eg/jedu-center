'use strict';

angular.module('jeducenterApp')
    .controller('TimeTableDetailController', function ($scope, $rootScope, $stateParams, entity, TimeTable, GroupOfStudent, Lesson) {
        $scope.timeTable = entity;
        $scope.load = function (id) {
            TimeTable.get({id: id}, function(result) {
                $scope.timeTable = result;
            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:timeTableUpdate', function(event, result) {
            $scope.timeTable = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
