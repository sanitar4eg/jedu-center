'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('st-tab.contacts', {
                parent: 'st-tab',
                url: '/st-tab/curators',
                data: {
                    authorities: ['ROLE_STUDENT'],
                    pageTitle: 'global.menu.ec.contacts'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/st-tab/contacts/st-tab.contacts.html',
                        controller: 'StContactsController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('curator');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
    });
