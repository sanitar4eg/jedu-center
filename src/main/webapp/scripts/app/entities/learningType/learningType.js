'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('learningType', {
                parent: 'entity',
                url: '/learningTypes',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.learningType.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/learningType/learningTypes.html',
                        controller: 'LearningTypeController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('learningType');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('learningType.detail', {
                parent: 'entity',
                url: '/learningType/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.learningType.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/learningType/learningType-detail.html',
                        controller: 'LearningTypeDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('learningType');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LearningType', function($stateParams, LearningType) {
                        return LearningType.get({id : $stateParams.id});
                    }]
                }
            })
            .state('learningType.new', {
                parent: 'learningType',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/learningType/learningType-dialog.html',
                        controller: 'LearningTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    isActive: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('learningType', null, { reload: true });
                    }, function() {
                        $state.go('learningType');
                    })
                }]
            })
            .state('learningType.edit', {
                parent: 'learningType',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/learningType/learningType-dialog.html',
                        controller: 'LearningTypeDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LearningType', function(LearningType) {
                                return LearningType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('learningType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('learningType.delete', {
                parent: 'learningType',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/learningType/learningType-delete-dialog.html',
                        controller: 'LearningTypeDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LearningType', function(LearningType) {
                                return LearningType.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('learningType', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
