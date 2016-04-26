'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('contacts', {
                parent: 'ec',
                url: '/contacts',
                data: {
                    authorities: [],
                    pageTitle: 'contacts.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/ec/contacts/contacts.html',
                        controller: 'ContactsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('contacts');
                        return $translate.refresh();
                    }]
                }
            });
    });
