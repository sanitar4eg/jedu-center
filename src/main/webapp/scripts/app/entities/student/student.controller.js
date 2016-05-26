'use strict';

angular.module('jeducenterApp')
    .controller('StudentController', function ($scope, $state, Student, ParseLinks) {

        $scope.students = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            Student.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.students = result;
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
            $scope.student = {
                lastName: null,
                firstName: null,
                middleName: null,
                email: null,
                phone: null,
                university: null,
                specialty: null,
                faculty: null,
                course: null,
                isActive: false,
                gotJob: false,
                comment: null,
                id: null
            };
        };
    });
