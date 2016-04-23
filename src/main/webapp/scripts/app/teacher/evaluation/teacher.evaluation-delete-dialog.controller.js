'use strict';

angular.module('jeducenterApp')
	.controller('EvaluationDeleteController', function($scope, $uibModalInstance, entity, Evaluation) {

        $scope.evaluation = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Evaluation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
