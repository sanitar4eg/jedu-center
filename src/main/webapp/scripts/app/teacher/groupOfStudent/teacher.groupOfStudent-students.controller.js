'use strict';

angular.module('jeducenterApp')
    .controller('TeacherGroupOfStudentStudentsController', function ($scope, $rootScope, $stateParams, entity,
                                                                   GroupOfStudent, Student) {
        $scope.groupOfStudent = entity;
        $scope.students = [];
        $scope.checked = [];
        $scope.load = function (id) {
            console.log(JSON.stringify(entity));
            GroupOfStudent.get({id: id}, function(result) {
                $scope.groupOfStudent = result;
                Student.query({isActive:true, type: $scope.groupOfStudent.type, filter: "groupOfStudent-is-null"}, function (result) {
                    $scope.students = result;
                });
            });
        };
        $scope.load($stateParams.id);

        $scope.addStudents = function () {
            $scope.checked.forEach(function (student) {
                console.log(JSON.stringify(student));
                // Student.update(student);
            });
        };

        var unsubscribe = $rootScope.$on('jeducenterApp:groupOfStudentUpdate', function(event, result) {
            $scope.groupOfStudent = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
