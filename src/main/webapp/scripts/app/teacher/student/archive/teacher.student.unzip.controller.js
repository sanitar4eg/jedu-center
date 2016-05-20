'use strict';

angular.module('jeducenterApp').controller('TeacherStudentUnzipController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentArchiving',
        function ($scope, $stateParams, $uibModalInstance, entity, StudentArchiving) {

            $scope.student = entity;
            $scope.clear = function() {
                $uibModalInstance.dismiss('cancel');
            };
            $scope.confirmDelete = function (id) {
                StudentArchiving.unzip({id: id},
                    function () {
                        $uibModalInstance.close(true);
                    });
            };

        }]);
