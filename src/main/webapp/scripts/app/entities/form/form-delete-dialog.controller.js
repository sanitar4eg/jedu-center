'use strict';

angular.module('jeducenterApp')
	.controller('FormDeleteController', function($scope, $uibModalInstance, entity, Form) {

        $scope.form = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            Form.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
