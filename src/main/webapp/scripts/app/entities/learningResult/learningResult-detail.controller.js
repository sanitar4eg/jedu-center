'use strict';

angular.module('jeducenterApp')
    .controller('LearningResultDetailController', function ($scope, $rootScope, $stateParams, entity, LearningResult, Student) {
        $scope.learningResult = entity;
        $scope.load = function (id) {
            LearningResult.get({id: id}, function(result) {
                $scope.learningResult = result;
            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:learningResultUpdate', function(event, result) {
            $scope.learningResult = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
