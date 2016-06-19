'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('st-tab.evaluation', {
                parent: 'st-tab',
                url: '/st-tab/evaluations',
                data: {
                    authorities: ['ROLE_STUDENT'],
                    pageTitle: 'jeducenterApp.evaluation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/st-tab/evaluation/st-tab.evaluations.html',
                        controller: 'StEvaluationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('evaluation');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
    });
