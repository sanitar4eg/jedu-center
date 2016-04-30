'use strict';

angular.module('jeducenterApp')
    .controller('GroupOfStudentController', function ($scope, $state, GroupOfStudent, ParseLinks) {

        $scope.groupOfStudents = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            GroupOfStudent.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.groupOfStudents = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();


        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.groupOfStudent = {
                name: null,
                type: null,
                description: null,
                isActive: false,
                id: null
            };
        };
    });
