'use strict';

angular.module('jeducenterApp')
	.controller('TeacherCuratorDeleteController', function($scope, $uibModalInstance, entity, Curator) {

        $scope.curator = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Curator.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
