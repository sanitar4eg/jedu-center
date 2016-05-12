'use strict';

angular.module('jeducenterApp')
    .controller('ReasonForLeavingDetailController', function ($scope, $rootScope, $stateParams, entity, ReasonForLeaving, Student) {
        $scope.reasonForLeaving = entity;
        $scope.load = function (id) {
            ReasonForLeaving.get({id: id}, function(result) {
                $scope.reasonForLeaving = result;
            });
        };
        var unsubscribe = $rootScope.$on('jeducenterApp:reasonForLeavingUpdate', function(event, result) {
            $scope.reasonForLeaving = result;
        });
        $scope.$on('$destroy', unsubscribe);

    });
