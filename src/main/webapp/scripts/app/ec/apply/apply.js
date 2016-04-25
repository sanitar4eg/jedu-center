'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('apply', {
                parent: 'ec',
                url: '/apply',
                data: {
                    authorities: [],
                    pageTitle: 'apply.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/ec/apply/apply.html',
                        controller: 'ApplyController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('apply');
                        return $translate.refresh();
                    }]
                }
            });
    });
