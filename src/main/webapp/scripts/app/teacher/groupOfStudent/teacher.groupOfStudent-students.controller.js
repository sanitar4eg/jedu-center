'use strict';

angular.module('jeducenterApp')
    .controller('TeacherGroupOfStudentStudentsController', function ($scope, $rootScope, $stateParams, entity,
                                                                     GroupOfStudent, Student) {
        $scope.groupOfStudent = entity;
        $scope.students = [];
        $scope.master = false;
        $scope.checked = [];
        $scope.load = function (id) {
            console.log(JSON.stringify(entity));
            GroupOfStudent.get({id: id}, function (result) {
                $scope.groupOfStudent = result;
                Student.query({
                    isActive: true,
                    type: $scope.groupOfStudent.type,
                    filter: "groupOfStudent-is-null"
                }, function (result) {
                    $scope.students = result;
                });
            });
        };
        $scope.load($stateParams.id);

        $scope.addStudents = function () {
            $scope.checked.forEach(function (student, id) {
                // console.log("Item is :" + JSON.stringify(student.id));
                // Student.update(student);
            });
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
