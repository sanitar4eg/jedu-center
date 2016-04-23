'use strict';

angular.module('jeducenterApp')
    .controller('TeacherCuratorDetailController', function ($scope, $rootScope, $stateParams, entity, Curator, User, Student, Recall) {
        $scope.curator = entity;
        $scope.load = function (id) {
            Curator.get({id: id}, function(result) {
                $scope.curator = result;
            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:curatorUpdate', function(event, result) {
            $scope.curator = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
