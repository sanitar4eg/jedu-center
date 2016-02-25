'use strict';

angular.module('jeducenterApp')
    .controller('StudentIntegrationController', function ($scope, $state, $location,  StudentIntegration) {

        $scope.refresh = function () {

        };

        $scope.getImportFile = function () {
            var importUrl = $location.protocol()+"://"+$location.host()+":"+$location.port()+"/api/import/students/";
            window.open(importUrl);
        };

    });
