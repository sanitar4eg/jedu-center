'use strict';

angular.module('jeducenterApp').controller('EvaluationDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Evaluation', 'Lesson', 'Student',
        function($scope, $stateParams, $uibModalInstance, entity, Evaluation, Lesson, Student) {

        $scope.evaluation = entity;
        $scope.lessons = Lesson.query();
        $scope.students = Student.query();
        $scope.load = function(id) {
            Evaluation.get({id : id}, function(result) {
                $scope.evaluation = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jeducenterApp:evaluationUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.evaluation.id != null) {
                Evaluation.update($scope.evaluation, onSaveSuccess, onSaveError);
            } else {
                Evaluation.save($scope.evaluation, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
