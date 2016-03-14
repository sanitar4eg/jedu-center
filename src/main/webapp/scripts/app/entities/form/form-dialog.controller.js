'use strict';

angular.module('jeducenterApp').controller('FormDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Form', 'Student',
        function($scope, $stateParams, $uibModalInstance, $q, entity, Form, Student) {

        $scope.form = entity;
        $scope.students = Student.query({filter: 'form-is-null'});
        $q.all([$scope.form.$promise, $scope.students.$promise]).then(function() {
            if (!$scope.form.student || !$scope.form.student.id) {
                return $q.reject();
            }
            return Student.get({id : $scope.form.student.id}).$promise;
        }).then(function(student) {
            $scope.students.push(student);
        });
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
