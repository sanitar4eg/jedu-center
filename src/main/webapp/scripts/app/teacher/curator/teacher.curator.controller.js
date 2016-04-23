'use strict';

angular.module('jeducenterApp')
    .controller('TeacherCuratorController', function ($scope, $state, Curator) {

        $scope.curators = [];
        $scope.loadAll = function() {
            Curator.query(function(result) {
               $scope.curators = result;
            });
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.curator = {
                firstName: null,
                lastName: null,
                email: null,
                department: null,
                isActive: false,
                id: null
            };
        };
    });
