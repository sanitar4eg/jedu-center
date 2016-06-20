'use strict';

angular.module('jeducenterApp')
    .controller('TcSetIntegrationController', function ($scope, $state, $location, tmhDynamicLocale,
                                                        StudentIntegration, $stateParams, StudentsSet) {
        $scope.studentsSet = {};
        $scope.setId = $stateParams.id;

        StudentsSet.get({id: $scope.setId}, function (result) {
            $scope.studentsSet = result;
        });

        $scope.getExportFile = function () {
            var importUrl = $location.protocol() + "://" + $location.host() + ":" + $location.port() + "/api/export/students/" + $scope.setId;
            window.open(importUrl);
        };

        var inputFile = $("#input-file").fileinput({
            language: tmhDynamicLocale.get()
        });

        var onUpdateSuccess = function (result) {
            $state.go('report.result', {results: result});
        };

        $scope.uploadExportFile = function () {
            var formData = new FormData();
            formData.append("file", inputFile.get(0).files[0]);
            StudentIntegration.uploadFile({id: $scope.setId},formData, onUpdateSuccess);
        };

    });
