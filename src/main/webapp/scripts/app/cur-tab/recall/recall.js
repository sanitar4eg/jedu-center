'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cur-tab.recall.new', {
                parent: 'cur-tab',
                url: '/cur-tab/recall/new',
                data: {
                    authorities: ['ROLE_CURATOR']
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
            .state('cur-tab.recall.edit', {
                parent: 'cur-tab',
                url: 'cur-tab/recall/{id}/edit',
                data: {
                    authorities: ['ROLE_CURATOR']
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
            .state('cur-tab.recall.delete', {
                parent: 'cur-tab',
                url: 'cur-tab/recall/{id}/delete',
                data: {
                    authorities: ['ROLE_CURATOR']
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
