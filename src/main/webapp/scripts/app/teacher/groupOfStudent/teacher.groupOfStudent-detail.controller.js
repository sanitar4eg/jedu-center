'use strict';

angular.module('jeducenterApp')
    .controller('TeacherGroupOfStudentDetailController', function ($scope, $rootScope, $stateParams, entity,
                                                                   GroupOfStudent, Student, TimeTable) {
        $scope.groupOfStudent = entity;
        $scope.students = [];
        $scope.load = function (id) {
            GroupOfStudent.get({id: id}, function(result) {
                $scope.groupOfStudent = result;
            });
            Student.query({groupOfStudent: $scope.groupOfStudent.id}, function (result) {
                $scope.students = result;
            });
        };
        $scope.load($scope.groupOfStudent.id);
        var unsubscribe = $rootScope.$on('jeducenterApp:groupOfStudentUpdate', function(event, result) {
            $scope.groupOfStudent = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
