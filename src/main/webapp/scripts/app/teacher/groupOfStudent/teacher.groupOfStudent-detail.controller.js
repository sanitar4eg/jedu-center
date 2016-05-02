'use strict';

angular.module('jeducenterApp')
    .controller('TeacherGroupOfStudentDetailController', function ($scope, $rootScope, $stateParams, entity,
                                                                   GroupOfStudent, Student, TimeTable) {
        $scope.groupOfStudent = entity;
        $scope.students = [];
        $scope.checked = [];
        $scope.load = function (id) {
            GroupOfStudent.get({id: id}, function(result) {
                $scope.groupOfStudent = result;
            });
            Student.query({groupOfStudent: $scope.groupOfStudent.id}, function (result) {
                $scope.students = result;
            });
        };
        $scope.load($stateParams.id);

        $scope.deleteFromGroup = function () {
            $scope.checked.forEach(function (student) {
                var index = $scope.students.indexOf(student);
                if(index > -1) {
                    $scope.students.splice(index, 1);
                }
                student.groupOfStudent = null;
                Student.update(student);
            });
        };

        var unsubscribe = $rootScope.$on('jeducenterApp:groupOfStudentUpdate', function(event, result) {
            $scope.groupOfStudent = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
