'use strict';

angular.module('jeducenterApp').controller('FormDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Form', 'Student', 'fileService', 'FormFile',
        function($scope, $stateParams, $uibModalInstance, entity, Form, Student, fileService, FormFile) {

        $scope.form = entity;
        $scope.students = Student.query();
        $scope.load = function(id) {
            Form.get({id : id}, function(result) {
                $scope.form = result;
            });
        };

        var onSaveSuccess = function (result) {
            var file = fileService.getFile();
            if (file != null && $scope.form.id == null) {
                var formData = new FormData();
                formData.append("file", file);
                FormFile.uploadFile({id: result.id}, formData);
            }
            $scope.$emit('jeducenterApp:formUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            var file = fileService.getFile();
            $scope.isSaving = true;
            if ($scope.form.id != null) {
                Form.update($scope.form, onSaveSuccess, onSaveError);
            } else {
                Form.save($scope.form, onSaveSuccess, onSaveError);
            }
            if (file != null && $scope.form.id != null) {
                var formData = new FormData();
                formData.append("file", file);
                FormFile.uploadFile({id: $scope.form.id}, formData);
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
