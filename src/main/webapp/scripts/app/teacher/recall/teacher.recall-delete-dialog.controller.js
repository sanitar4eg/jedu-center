'use strict';

angular.module('jeducenterApp')
	.controller('TeacherRecallDeleteController', function($scope, $uibModalInstance, entity, Recall) {

        $scope.recall = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Recall.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
