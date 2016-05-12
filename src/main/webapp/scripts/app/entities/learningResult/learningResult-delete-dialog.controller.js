'use strict';

angular.module('jeducenterApp')
	.controller('LearningResultDeleteController', function($scope, $uibModalInstance, entity, LearningResult) {

        $scope.learningResult = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            LearningResult.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
