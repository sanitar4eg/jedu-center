'use strict';

angular.module('jeducenterApp')
	.controller('TeacherTimeTableDeleteController', function($scope, $uibModalInstance, entity, TimeTable) {

        $scope.timeTable = entity;
        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.confirmDelete = function (id) {
            TimeTable.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        };

    });
