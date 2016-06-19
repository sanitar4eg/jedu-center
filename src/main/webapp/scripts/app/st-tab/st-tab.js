'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('st-tab', {
                abstract: true,
                parent: 'site'
            });
    });
