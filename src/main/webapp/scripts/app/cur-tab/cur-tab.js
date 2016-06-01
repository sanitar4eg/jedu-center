'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cur-tab', {
                abstract: true,
                parent: 'site'
            });
    });
