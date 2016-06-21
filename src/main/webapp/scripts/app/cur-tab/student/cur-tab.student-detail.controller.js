'use strict';

angular.module('jeducenterApp')
    .controller('CurTabStudentDetailController', function ($scope, $rootScope, $state, $stateParams, entity, Student,
                                                            User, GroupOfStudent, Curator, Form, Recall, CurrentEntity) {
        $scope.student = entity;
        $scope.curator = entity.curator;
        $scope.recalls = [];

        console.log(JSON.stringify($scope.curator));
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
