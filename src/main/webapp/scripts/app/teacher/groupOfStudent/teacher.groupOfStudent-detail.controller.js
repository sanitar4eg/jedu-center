'use strict';

angular.module('jeducenterApp')
    .controller('TeacherGroupOfStudentDetailController', function ($scope, $state, $rootScope, $stateParams, entity,
                                                                   GroupOfStudent, Student, RegisterStudents) {
        $scope.groupOfStudent = entity;
        $scope.students = [];
        $scope.master = false;
        $scope.checked = [];
        $scope.load = function (id) {
            GroupOfStudent.get({id: id}, function (result) {
                $scope.groupOfStudent = result;
            });
            Student.query({groupOfStudent: id}, function (result) {
                $scope.students = result;
            });
        };
        $scope.load($stateParams.id);

        $scope.deleteFromGroup = function () {
            $scope.checked.forEach(function (student) {
                var index = $scope.students.indexOf(student);
                if (index > -1) {
                    $scope.students.splice(index, 1);
                }
                student.groupOfStudent = null;
                Student.update(student);
            });
        };

        var onUpdateSuccess = function (result) {
            $state.go('report.result', {results: result});
        };

        $scope.register = function () {
            RegisterStudents.update($scope.checked, onUpdateSuccess);
        };

        $scope.changeAll = function () {
            $scope.checked.splice(0, $scope.checked.length);
            if ($scope.master) {
                $scope.students.forEach(function (student) {
                    $scope.checked.push(student);
                });
            }
        };

        var unsubscribe = $rootScope.$on('jeducenterApp:groupOfStudentUpdate', function (event, result) {
            $scope.groupOfStudent = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
