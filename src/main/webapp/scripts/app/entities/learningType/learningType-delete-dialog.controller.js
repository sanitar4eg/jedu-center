'use strict';

angular.module('jeducenterApp')
	.controller('LearningTypeDeleteController', function($scope, $uibModalInstance, entity, LearningType) {

        $scope.learningType = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LearningType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
