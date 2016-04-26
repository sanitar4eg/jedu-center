'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('specialties', {
                parent: 'ec',
                url: '/specialties',
                data: {
                    authorities: [],
                    pageTitle: 'specialties.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/ec/specialties/specialties.html',
                        controller: 'SpecialtiesController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('specialties');
                        return $translate.refresh();
                    }]
                }
            });
    });
