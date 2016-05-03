'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('report.result', {
                parent: 'report',
                url: '/report/operation-result',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                    pageTitle: 'Результат операции'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/report/operationResult/operation-result.html',
                        controller: 'OperationResultController'
                    }
                },
                params: {
                    results: null
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            });
    });
