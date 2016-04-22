'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('curator', {
                parent: 'entity',
                url: '/curators',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.curator.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/curator/curators.html',
                        controller: 'CuratorController'
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
            .state('curator.detail', {
                parent: 'entity',
                url: '/curator/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.curator.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/curator/curator-detail.html',
                        controller: 'CuratorDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('curator');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Curator', function($stateParams, Curator) {
                        return Curator.get({id : $stateParams.id});
                    }]
                }
            })
            .state('curator.new', {
                parent: 'curator',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/curator/curator-dialog.html',
                        controller: 'CuratorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    firstName: null,
                                    lastName: null,
                                    email: null,
                                    department: null,
                                    isActive: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('curator', null, { reload: true });
                    }, function() {
                        $state.go('curator');
                    })
                }]
            })
            .state('curator.edit', {
                parent: 'curator',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/curator/curator-dialog.html',
                        controller: 'CuratorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Curator', function(Curator) {
                                return Curator.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('curator', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('curator.delete', {
                parent: 'curator',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/curator/curator-delete-dialog.html',
                        controller: 'CuratorDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Curator', function(Curator) {
                                return Curator.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('curator', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
