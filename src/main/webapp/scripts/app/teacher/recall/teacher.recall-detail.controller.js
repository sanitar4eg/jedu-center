'use strict';

angular.module('jeducenterApp')
    .controller('TeacherRecallDetailController', function ($scope, $rootScope, $stateParams, DataUtils, entity, Recall, Student, Curator) {
        $scope.recall = entity;
        $scope.load = function (id) {
            Recall.get({id: id}, function(result) {
                $scope.recall = result;
            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:recallUpdate', function(event, result) {
            $scope.recall = result;
        });
        $scope.$on('$destroy', unsubscribe);

        $scope.byteSize = DataUtils.byteSize;
    });
