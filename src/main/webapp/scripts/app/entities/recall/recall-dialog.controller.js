'use strict';

angular.module('jeducenterApp').controller('RecallDialogController',
    ['$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Recall', 'Student', 'Curator',
        'fileService', 'RecallFile',
        function($scope, $stateParams, $uibModalInstance, DataUtils, entity, Recall, Student, Curator,
                 fileService, RecallFile) {
        $scope.recall = entity;
        $scope.students = Student.query();
        $scope.curators = Curator.query();
        $scope.load = function (id) {
            Recall.get({id: id}, function (result) {
                $scope.recall = result;
            });
        };
        var osSaveFileSuccess = function (result) {
            console.log(JSON.stringify(result));
            $scope.$emit('jeducenterApp:recallUpdate', result);
            $uibModalInstance.close(result);
            $scope.isSaving = false;
        };
        var onSaveFileError = function (result) {
            $scope.isSaving = false;
        };

        var onSaveSuccess = function (result) {
            var file = fileService.getFile();
            if (file != null) {
                var formData = new FormData();
                formData.append("file", file);
                RecallFile.uploadFile({id: result.id}, formData, osSaveFileSuccess, onSaveFileError);
            } else {
                $scope.$emit('jeducenterApp:recallUpdate', result);
                $uibModalInstance.close(result);
            }
        };
        var onSaveError = function (result) {
            console.log(JSON.stringify(result));
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
        };

        $scope.clear = function () {
            $uibModalInstance.dismiss('cancel');
        };

        $scope.abbreviate = DataUtils.abbreviate;

        $scope.byteSize = DataUtils.byteSize;
}]);
