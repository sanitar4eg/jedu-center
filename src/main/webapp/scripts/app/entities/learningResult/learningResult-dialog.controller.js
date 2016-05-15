'use strict';

angular.module('jeducenterApp').controller('LearningResultDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LearningResult', 'Student',
        function($scope, $stateParams, $uibModalInstance, entity, LearningResult, Student) {

        $scope.learningResult = entity;
        $scope.students = Student.query();
        $scope.load = function(id) {
            LearningResult.get({id : id}, function(result) {
                $scope.learningResult = result;
            });
        };

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
            if ($scope.learningResult.id != null) {
                LearningResult.update($scope.learningResult, onSaveSuccess, onSaveError);
            } else {
                LearningResult.save($scope.learningResult, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForCreationTime = {};

        $scope.datePickerForCreationTime.status = {
            opened: false
        };

        $scope.datePickerForCreationTimeOpen = function($event) {
            $scope.datePickerForCreationTime.status.opened = true;
        };
}]);
