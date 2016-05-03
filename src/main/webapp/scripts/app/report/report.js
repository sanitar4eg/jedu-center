'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('report', {
                abstract: true,
                parent: 'site'
            });
    });
