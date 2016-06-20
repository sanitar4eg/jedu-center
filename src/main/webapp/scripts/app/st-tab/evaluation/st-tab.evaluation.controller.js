'use strict';

angular.module('jeducenterApp')
    .controller('StEvaluationController', function ($scope, $state, Evaluation, ParseLinks, CurrentEntity) {

        $scope.student = {};
        $scope.evaluations = [];
        $scope.average = 0.0;

        $scope.loadAll = function () {
            CurrentEntity.get({}, function (result) {
                $scope.student = result;
                Evaluation.query({student: $scope.student.id}, function (result) {
                    $scope.evaluations = result;
                    calculateAverage(result);
                });
            });
        };
        $scope.loadAll();

        var calculateAverage = function (evaluations) {
            var sum = 0.0;
            var int = 0;

            evaluations.forEach(function (evaluation) {
                if(evaluation.value !== "undefined") {
                    sum += evaluation.value;
                    int++;
                }
            });
            if (int >0 ) {
                $scope.average = sum / int;
            }
        }
    });
