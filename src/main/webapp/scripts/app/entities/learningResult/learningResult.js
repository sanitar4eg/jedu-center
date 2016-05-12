'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('learningResult', {
                parent: 'entity',
                url: '/learningResults',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.learningResult.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/learningResult/learningResults.html',
                        controller: 'LearningResultController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('learningResult');
                        $translatePartialLoader.addPart('typeOfResult');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('learningResult.detail', {
                parent: 'entity',
                url: '/learningResult/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.learningResult.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/learningResult/learningResult-detail.html',
                        controller: 'LearningResultDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('learningResult');
                        $translatePartialLoader.addPart('typeOfResult');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'LearningResult', function($stateParams, LearningResult) {
                        return LearningResult.get({id : $stateParams.id});
                    }]
                }
            })
            .state('learningResult.new', {
                parent: 'learningResult',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/learningResult/learningResult-dialog.html',
                        controller: 'LearningResultDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    type: null,
                                    description: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('learningResult', null, { reload: true });
                    }, function() {
                        $state.go('learningResult');
                    })
                }]
            })
            .state('learningResult.edit', {
                parent: 'learningResult',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/learningResult/learningResult-dialog.html',
                        controller: 'LearningResultDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['LearningResult', function(LearningResult) {
                                return LearningResult.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('learningResult', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('learningResult.delete', {
                parent: 'learningResult',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/learningResult/learningResult-delete-dialog.html',
                        controller: 'LearningResultDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['LearningResult', function(LearningResult) {
                                return LearningResult.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('learningResult', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
