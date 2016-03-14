'use strict';

angular.module('jeducenterApp')
    .controller('GroupOfStudentDetailController', function ($scope, $rootScope, $stateParams, entity, GroupOfStudent, Student, TimeTable) {
        $scope.groupOfStudent = entity;
        $scope.load = function (id) {
            GroupOfStudent.get({id: id}, function(result) {
                $scope.groupOfStudent = result;
            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:groupOfStudentUpdate', function(event, result) {
            $scope.groupOfStudent = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
