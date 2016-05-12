'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('reasonForLeaving', {
                parent: 'entity',
                url: '/reasonForLeavings',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jeducenterApp.reasonForLeaving.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/reasonForLeaving/reasonForLeavings.html',
                        controller: 'ReasonForLeavingController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reasonForLeaving');
                        $translatePartialLoader.addPart('typeOfReason');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('reasonForLeaving.detail', {
                parent: 'entity',
                url: '/reasonForLeaving/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jeducenterApp.reasonForLeaving.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/reasonForLeaving/reasonForLeaving-detail.html',
                        controller: 'ReasonForLeavingDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('reasonForLeaving');
                        $translatePartialLoader.addPart('typeOfReason');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'ReasonForLeaving', function($stateParams, ReasonForLeaving) {
                        return ReasonForLeaving.get({id : $stateParams.id});
                    }]
                }
            })
            .state('reasonForLeaving.new', {
                parent: 'reasonForLeaving',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/reasonForLeaving/reasonForLeaving-dialog.html',
                        controller: 'ReasonForLeavingDialogController',
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
                        $state.go('reasonForLeaving', null, { reload: true });
                    }, function() {
                        $state.go('reasonForLeaving');
                    })
                }]
            })
            .state('reasonForLeaving.edit', {
                parent: 'reasonForLeaving',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/reasonForLeaving/reasonForLeaving-dialog.html',
                        controller: 'ReasonForLeavingDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['ReasonForLeaving', function(ReasonForLeaving) {
                                return ReasonForLeaving.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('reasonForLeaving', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('reasonForLeaving.delete', {
                parent: 'reasonForLeaving',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/reasonForLeaving/reasonForLeaving-delete-dialog.html',
                        controller: 'ReasonForLeavingDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['ReasonForLeaving', function(ReasonForLeaving) {
                                return ReasonForLeaving.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('reasonForLeaving', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
