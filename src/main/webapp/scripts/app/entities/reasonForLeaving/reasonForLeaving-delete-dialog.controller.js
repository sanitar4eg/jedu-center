'use strict';

angular.module('jeducenterApp')
	.controller('ReasonForLeavingDeleteController', function($scope, $uibModalInstance, entity, ReasonForLeaving) {

        $scope.reasonForLeaving = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            ReasonForLeaving.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
