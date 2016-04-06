'use strict';

angular.module('jeducenterApp')
	.controller('GroupOfStudentDeleteController', function($scope, $uibModalInstance, entity, GroupOfStudent) {

        $scope.groupOfStudent = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            GroupOfStudent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
