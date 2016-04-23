'use strict';

angular.module('jeducenterApp').controller('TeacherTimeTableDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'entity', 'TimeTable', 'GroupOfStudent', 'Lesson',
        function($scope, $stateParams, $uibModalInstance, entity, TimeTable, GroupOfStudent, Lesson) {

        $scope.timeTable = entity;
        $scope.groupofstudents = GroupOfStudent.query();
        $scope.lessons = Lesson.query();
        $scope.load = function(id) {
            TimeTable.get({id : id}, function(result) {
                $scope.timeTable = result;
            });
        };

        var onSaveSuccess = function (result) {
            $scope.$emit('jeducenterApp:timeTableUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            $scope.isSaving = true;
            if ($scope.timeTable.id != null) {
                TimeTable.update($scope.timeTable, onSaveSuccess, onSaveError);
            } else {
                TimeTable.save($scope.timeTable, onSaveSuccess, onSaveError);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };
}]);
