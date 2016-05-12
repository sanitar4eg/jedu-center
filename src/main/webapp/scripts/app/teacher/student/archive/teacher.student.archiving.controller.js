'use strict';

angular.module('jeducenterApp').controller('TeacherStudentArchivingController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'StudentArchive',
        function ($scope, $stateParams, $uibModalInstance, entity, StudentArchive) {

            $scope.student = entity;

            var onSaveSuccess = function (result) {
                $scope.$emit('jeducenterApp:learningResultUpdate', result);
                $uibModalInstance.close(result);
                $scope.isSaving = false;
            };

            var onSaveError = function (result) {
                $scope.isSaving = false;
            };

            $scope.save = function () {
                $scope.isSaving = true;
                StudentArchive.update($scope.student, onSaveSuccess, onSaveError);
            };

            $scope.clear = function () {
                $uibModalInstance.dismiss('cancel');
            };
        }]);
