'use strict';

angular.module('jeducenterApp')
    .controller('CurTabStudentDetailController', function ($scope, $rootScope, $stateParams, entity, Student,
                                                            User, GroupOfStudent, Curator, Form, Recall) {
        $scope.student = entity;
        $scope.recalls = [];
        $scope.load = function (id) {
            Student.get({id: id}, function (result) {
                $scope.student = result;
            });
        };

        Recall.query({student: $stateParams.id}, function (result) {
            $scope.recalls = result;
        });
        var unsubscribe = $rootScope.$on('jeducenterApp:studentUpdate', function (event, result) {
            $scope.student = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
