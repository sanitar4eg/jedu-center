'use strict';

angular.module('jeducenterApp')
    .controller('StudentHistoryController', function ($scope, $state, StudentHistory) {

        $scope.students = [];
        $scope.loadAll = function(dateTime) {
            StudentHistory.query({dateTime: '22-01-2016-17-15-00'}, function(result) {
                $scope.students = result;
            });
        };
        $scope.loadAll();

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.student = {
                firstName: null,
                lastName: null,
                middleName: null,
                type: null,
                email: null,
                phone: null,
                university: null,
                specialty: null,
                course: null,
                groupName: null,
                id: null
            };
        };
    });
