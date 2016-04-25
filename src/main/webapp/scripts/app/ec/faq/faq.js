'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('faq', {
                parent: 'ec',
                url: '/faq',
                data: {
                    authorities: [],
                    pageTitle: 'faq.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/ec/faq/faq.html',
                        controller: 'FAQController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('faq');
                        return $translate.refresh();
                    }]
                }
            });
    });
