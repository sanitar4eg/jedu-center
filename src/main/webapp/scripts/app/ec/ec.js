'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ec', {
                abstract: true,
                parent: 'site'
            });
    });
