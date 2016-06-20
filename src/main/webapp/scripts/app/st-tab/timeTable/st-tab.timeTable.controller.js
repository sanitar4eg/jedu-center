'use strict';

angular.module('jeducenterApp')
    .controller('StTimeTableController', function ($scope, $state, Lesson, CurrentEntity) {

        $scope.student = {};
        $scope.groupOfStudent = {};
        $scope.lessons = [];
        $scope.loadAll = function() {
            CurrentEntity.get({}, function (result) {
                $scope.student = result;
                Lesson.query({timeTable: $scope.student.groupOfStudent.timeTable.id},function(result) {
                    $scope.lessons = result;
                });
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
