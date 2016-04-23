'use strict';

angular.module('jeducenterApp')
    .controller('TeacherEvaluationDetailController', function ($scope, $rootScope, $stateParams, entity, Evaluation, Lesson, Student) {
        $scope.evaluation = entity;
        $scope.load = function (id) {
            Evaluation.get({id: id}, function(result) {
                $scope.evaluation = result;
            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:evaluationUpdate', function(event, result) {
            $scope.evaluation = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
