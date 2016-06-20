'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('st-tab.timeTable', {
                parent: 'st-tab',
                url: '/st-tab/timeTables',
                data: {
                    authorities: ['ROLE_STUDENT'],
                    pageTitle: 'jeducenterApp.timeTable.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/st-tab/timeTable/st-tab.timeTable.html',
                        controller: 'StTimeTableController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeTable');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
    });
