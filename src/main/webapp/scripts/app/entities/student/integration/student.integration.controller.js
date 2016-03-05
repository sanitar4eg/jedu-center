'use strict';

angular.module('jeducenterApp')
    .controller('StudentIntegrationController', function ($scope, $state, $location, $http, tmhDynamicLocale,  StudentIntegration) {

        $scope.getImportFile = function () {
            var importUrl = $location.protocol()+"://"+$location.host()+":"+$location.port()+"/api/import/students/";
            window.open(importUrl);
        };

        var inputFile = $("#input-file").fileinput({
            language: tmhDynamicLocale.get()
        });

        $scope.uploadExportFile=function (){
            var formData=new FormData();
            formData.append("file",inputFile.get(0).files[0]);
            $http({
                method: 'POST',
                url: "api/export/students/",
                headers: {'Content-Type': undefined},
                data: formData,
                transformRequest: function(data, headersGetterFunction) {
                    return data;
                }
            });

        };

    });
