'use strict';

angular.module('jeducenterApp').controller('TeacherRecallDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Recall', 'Student', 'Curator',
        'fileService', 'RecallFile',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, Recall, Student, Curator,
                 fileService, RecallFile) {

        $scope.recall = entity;
        $scope.students = Student.query();
        $scope.curators = Curator.query();
        $scope.load = function(id) {
            Recall.get({id : id}, function(result) {
                $scope.recall = result;
            });
        };

        var onSaveSuccess = function (result) {
            var file = fileService.getFile();
            if (file != null && $scope.recall.id == null) {
                var formData = new FormData();
                formData.append("file", file);
                RecallFile.uploadFile({id: result.id}, formData);
            }
            $scope.$emit('jeducenterApp:recallUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            var file = fileService.getFile();
            $scope.isSaving = true;
            if ($scope.recall.id != null) {
                Recall.update($scope.recall, onSaveSuccess, onSaveError);
            } else {
                Recall.save($scope.recall, onSaveSuccess, onSaveError);
            }
            if (file != null && $scope.recall.id != null) {
                var formData = new FormData();
                formData.append("file", file);
                RecallFile.uploadFile({id: $scope.recall.id}, formData);
            }
        };

        $scope.clear = function() {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
}]);
