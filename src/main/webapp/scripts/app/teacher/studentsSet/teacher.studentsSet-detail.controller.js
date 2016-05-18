'use strict';

angular.module('jeducenterApp')
    .controller('TeacherStudentsSetDetailController', function ($scope, $state, $rootScope, $stateParams, entity,
                                                                StudentsSet, Student, GroupOfStudent) {
        $scope.studentsSet = entity;
        $scope.tabs = [
            {title: "Студенты", route: "teacher.studentsSet.detail.students", active: true, view: "students"},
            {title: "Группы", route: "teacher.studentsSet.detail.groups", active: false, view: "groups"}
        ];

        $scope.load = function (id) {
            StudentsSet.get({id: id}, function (result) {
                $scope.studentsSet = result;
            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:studentsSetUpdate', function (event, result) {
            $scope.studentsSet = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.tabs.forEach(function (tab, i, arr) {
            if (tab.active) {
                $state.go(tab.route);
                return;
            }
        })

    });
