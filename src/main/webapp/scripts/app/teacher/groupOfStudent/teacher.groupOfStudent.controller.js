'use strict';

angular.module('jeducenterApp')
    .controller('TeacherGroupOfStudentController', function ($scope, $state, GroupOfStudent) {

        $scope.groupOfStudents = [];
        $scope.loadAll = function() {
            GroupOfStudent.query(function(result) {
               $scope.groupOfStudents = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.groupOfStudent = {
                name: null,
                type: null,
                description: null,
                isActive: false,
                id: null
            };
        };
    });
