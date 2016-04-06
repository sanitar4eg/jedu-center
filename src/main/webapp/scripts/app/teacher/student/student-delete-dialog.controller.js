'use strict';

angular.module('jeducenterApp')
	.controller('StudentDeleteController', function($scope, $uibModalInstance, entity, Student) {

        $scope.student = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Student.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
