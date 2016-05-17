'use strict';

angular.module('jeducenterApp')
    .controller('TeacherStudentsSetDetailController', function ($scope, $rootScope, $stateParams, entity, StudentsSet, Student, GroupOfStudent) {
        $scope.studentsSet = entity;
        $scope.load = function (id) {
            StudentsSet.get({id: id}, function(result) {
                $scope.studentsSet = result;
            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:studentsSetUpdate', function(event, result) {
            $scope.studentsSet = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
