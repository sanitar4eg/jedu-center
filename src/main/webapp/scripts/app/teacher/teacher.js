'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher', {
                abstract: true,
                parent: 'site'
            });
    });
