'use strict';

angular.module('jeducenterApp').controller('GroupOfStudentDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'GroupOfStudent', 'Student',
        function($scope, $stateParams, $uibModalInstance, entity, GroupOfStudent, Student) {

        $scope.groupOfStudent = entity;
        $scope.students = Student.query();
        $scope.load = function(id) {
            GroupOfStudent.get({id : id}, function(result) {
                $scope.groupOfStudent = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jeducenterApp:groupOfStudentUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.groupOfStudent.id != null) {
                GroupOfStudent.update($scope.groupOfStudent, onSaveSuccess, onSaveError);
            } else {
                GroupOfStudent.save($scope.groupOfStudent, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
