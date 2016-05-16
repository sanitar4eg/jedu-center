'use strict';

angular.module('jeducenterApp')
    .controller('LearningTypeDetailController', function ($scope, $rootScope, $stateParams, entity, LearningType, Student, GroupOfStudent) {
        $scope.learningType = entity;
        $scope.load = function (id) {
            LearningType.get({id: id}, function(result) {
                $scope.learningType = result;
            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:learningTypeUpdate', function(event, result) {
            $scope.learningType = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
