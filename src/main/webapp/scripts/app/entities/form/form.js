'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('form', {
                parent: 'entity',
                url: '/forms',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jeducenterApp.form.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/form/forms.html',
                        controller: 'FormController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('form');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('form.detail', {
                parent: 'entity',
                url: '/form/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jeducenterApp.form.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/form/form-detail.html',
                        controller: 'FormDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('form');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Form', function($stateParams, Form) {
                        return Form.get({id : $stateParams.id});
                    }]
                }
            })
            .state('form.new', {
                parent: 'form',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/form/form-dialog.html',
                        controller: 'FormDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    pathToFile: null,
                                    creationTime: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('form', null, { reload: true });
                    }, function() {
                        $state.go('form');
                    })
                }]
            })
            .state('form.edit', {
                parent: 'form',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/form/form-dialog.html',
                        controller: 'FormDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Form', function(Form) {
                                return Form.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('form', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('form.delete', {
                parent: 'form',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/form/form-delete-dialog.html',
                        controller: 'FormDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Form', function(Form) {
                                return Form.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('form', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
