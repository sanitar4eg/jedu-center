'use strict';

angular.module('jeducenterApp').controller('LessonDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'Lesson', 'TimeTable', 'Evaluation',
        function($scope, $stateParams, $uibModalInstance, entity, Lesson, TimeTable, Evaluation) {

        $scope.lesson = entity;
        $scope.timetables = TimeTable.query();
        $scope.evaluations = Evaluation.query();
        $scope.load = function(id) {
            Lesson.get({id : id}, function(result) {
                $scope.lesson = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jeducenterApp:lessonUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.lesson.id != null) {
                Lesson.update($scope.lesson, onSaveSuccess, onSaveError);
            } else {
                Lesson.save($scope.lesson, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
        $scope.datePickerForTime = {};

        $scope.datePickerForTime.status = {
            opened: false
        };

        $scope.datePickerForTimeOpen = function($event) {
            $scope.datePickerForTime.status.opened = true;
        };
}]);
