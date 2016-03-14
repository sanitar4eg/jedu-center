'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('evaluation', {
                parent: 'entity',
                url: '/evaluations',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jeducenterApp.evaluation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/evaluation/evaluations.html',
                        controller: 'EvaluationController'
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
            .state('evaluation.detail', {
                parent: 'entity',
                url: '/evaluation/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jeducenterApp.evaluation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/evaluation/evaluation-detail.html',
                        controller: 'EvaluationDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('evaluation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Evaluation', function($stateParams, Evaluation) {
                        return Evaluation.get({id : $stateParams.id});
                    }]
                }
            })
            .state('evaluation.new', {
                parent: 'evaluation',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/evaluation/evaluation-dialog.html',
                        controller: 'EvaluationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    value: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('evaluation', null, { reload: true });
                    }, function() {
                        $state.go('evaluation');
                    })
                }]
            })
            .state('evaluation.edit', {
                parent: 'evaluation',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/evaluation/evaluation-dialog.html',
                        controller: 'EvaluationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Evaluation', function(Evaluation) {
                                return Evaluation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('evaluation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('evaluation.delete', {
                parent: 'evaluation',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/evaluation/evaluation-delete-dialog.html',
                        controller: 'EvaluationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Evaluation', function(Evaluation) {
                                return Evaluation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('evaluation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
