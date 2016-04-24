'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('recall', {
                parent: 'entity',
                url: '/recalls',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.recall.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/recall/recalls.html',
                        controller: 'RecallController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('recall');
                        $translatePartialLoader.addPart('typeRecallEnumeration');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('recall.detail', {
                parent: 'entity',
                url: '/recall/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.recall.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/recall/recall-detail.html',
                        controller: 'RecallDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('recall');
                        $translatePartialLoader.addPart('typeRecallEnumeration');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Recall', function($stateParams, Recall) {
                        return Recall.get({id : $stateParams.id});
                    }]
                }
            })
            .state('recall.new', {
                parent: 'recall',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/recall/recall-dialog.html',
                        controller: 'RecallDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    type: null,
                                    name: null,
                                    description: null,
                                    pathToFile: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('recall', null, { reload: true });
                    }, function() {
                        $state.go('recall');
                    })
                }]
            })
            .state('recall.edit', {
                parent: 'recall',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/recall/recall-dialog.html',
                        controller: 'RecallDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Recall', function(Recall) {
                                return Recall.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('recall', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('recall.delete', {
                parent: 'recall',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/recall/recall-delete-dialog.html',
                        controller: 'RecallDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Recall', function(Recall) {
                                return Recall.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('recall', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
