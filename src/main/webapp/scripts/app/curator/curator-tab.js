'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('curator-tab', {
                abstract: true,
                parent: 'site'
            });
    });
