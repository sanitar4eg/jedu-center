'use strict';

angular.module('jeducenterApp').controller('FormDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Form', 'Student',
        function($scope, $stateParams, $uibModalInstance, entity, Form, Student) {

        $scope.form = entity;
        $scope.students = Student.query();
        $scope.load = function(id) {
            Form.get({id : id}, function(result) {
                $scope.form = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jeducenterApp:formUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.form.id != null) {
                Form.update($scope.form, onSaveSuccess, onSaveError);
            } else {
                Form.save($scope.form, onSaveSuccess, onSaveError);
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
