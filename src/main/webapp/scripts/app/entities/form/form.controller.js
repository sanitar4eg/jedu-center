'use strict';

angular.module('jeducenterApp')
    .controller('FormController', function ($scope, $state, Form) {

        $scope.forms = [];
        $scope.loadAll = function() {
            Form.query(function(result) {
               $scope.forms = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.form = {
                file: null,
                creationTime: null,
                isActive: false,
                id: null
            };
        };
    });
