'use strict';

angular.module('jeducenterApp')
    .controller('MainController', function ($scope, Principal, MainNote, ParseLinks) {
        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.isAuthenticated = Principal.isAuthenticated;


            $scope.notes = [];
            $scope.predicate = 'date';
            $scope.reverse = false;
            $scope.page = 0;
            $scope.loadAll = function () {
                MainNote.query({
                    page: $scope.page,
                    size: 20,
                    sort: [$scope.predicate + ',' + ($scope.reverse ? 'asc' : 'desc'), 'date']
                }, function (result, headers) {
                    $scope.links = ParseLinks.parse(headers('link'));
                    for (var i = 0; i < result.length; i++) {
                        $scope.notes.push(result[i]);
                    }
                });
            };

            $scope.loadAll();

        });
    });
