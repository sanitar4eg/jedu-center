'use strict';

angular.module('jeducenterApp')
    .controller('ApplyController', function ($scope, $state, $translate, fileService, ApplyFile) {

        var onSaveSuccess = function (result) {
            $scope.$emit('jeducenterApp:formUpdate', result);
            alert($translate.instant("apply.completed"));
            $state.go('home');
        };

        var onSaveError = function (result) {
            $scope.isSaving = false;
        };

        $scope.save = function () {
            var file = fileService.getFile();
            var formData = new FormData();
            formData.append("file", file);
            ApplyFile.uploadApply(formData, onSaveSuccess, onSaveError);

        };

    });
