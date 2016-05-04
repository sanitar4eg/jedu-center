'use strict';

angular.module('jeducenterApp')
    .controller('StudentIntegrationController', function ($scope, $state, $location, tmhDynamicLocale,
                                                          StudentIntegration) {

        $scope.getExportFile = function () {
            var importUrl = $location.protocol()+"://"+$location.host()+":"+$location.port()+"/api/export/students/";
            window.open(importUrl);
        };

        var inputFile = $("#input-file").fileinput({
            language: tmhDynamicLocale.get()
        });

        var onUpdateSuccess = function (result) {
            $state.go('report.result', {results: result});
        };

        $scope.uploadExportFile=function (){
            var formData=new FormData();
            formData.append("file",inputFile.get(0).files[0]);
            StudentIntegration.uploadFile(formData, onUpdateSuccess);
        };

    });
