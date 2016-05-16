'use strict';

angular.module('jeducenterApp')
	.controller('StudentsSetDeleteController', function($scope, $uibModalInstance, entity, StudentsSet) {

        $scope.studentsSet = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            StudentsSet.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
