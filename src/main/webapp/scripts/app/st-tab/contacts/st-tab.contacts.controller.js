'use strict';

angular.module('jeducenterApp')
    .controller('StContactsController', function ($scope, $state, Curator, CurrentEntity) {

        $scope.student = {};
        $scope.curator = {};
        $scope.loadAll = function() {
            CurrentEntity.get({}, function (result) {
                $scope.student = result;
                console.log(JSON.stringify(result));
                // Lesson.query({timeTable: $scope.student.groupOfStudent.timeTable.id},function(result) {
                //     $scope.lessons = result;
                // });
            });
        };
        $scope.loadAll();
    });
