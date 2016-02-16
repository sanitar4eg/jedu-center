'use strict';

angular.module('jeducenterApp')
    .controller('StudentIntegrationController', function ($scope, $state, StudentIntegration) {

        $scope.refresh = function () {

        };

        $scope.getImportFile = function () {

            StudentIntegration.query(function() {

            });
        };

    });
