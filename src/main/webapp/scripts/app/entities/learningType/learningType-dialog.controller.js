'use strict';

angular.module('jeducenterApp').controller('LearningTypeDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'LearningType', 'Student', 'GroupOfStudent',
        function($scope, $stateParams, $uibModalInstance, entity, LearningType, Student, GroupOfStudent) {

        $scope.learningType = entity;
        $scope.students = Student.query();
        $scope.groupofstudents = GroupOfStudent.query();
        $scope.load = function(id) {
            LearningType.get({id : id}, function(result) {
                $scope.learningType = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jeducenterApp:learningTypeUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.learningType.id != null) {
                LearningType.update($scope.learningType, onSaveSuccess, onSaveError);
            } else {
                LearningType.save($scope.learningType, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
