'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('student-tab', {
                abstract: true,
                parent: 'site'
            });
    });
