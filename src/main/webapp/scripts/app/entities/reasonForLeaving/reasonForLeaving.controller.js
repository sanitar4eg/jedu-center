'use strict';

angular.module('jeducenterApp')
    .controller('ReasonForLeavingController', function ($scope, $state, ReasonForLeaving, ParseLinks) {

        $scope.reasonForLeavings = [];
        $scope.predicate = 'id';
        $scope.reverse = true;
        $scope.page = 1;
        $scope.loadAll = function() {
            ReasonForLeaving.query({page: $scope.page - 1, size: 20, sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'id']}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.totalItems = headers('X-Total-Count');
                $scope.reasonForLeavings = result;
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
            $scope.reasonForLeaving = {
                type: null,
                description: null,
                id: null
            };
        };
    });
