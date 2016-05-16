'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('groupOfStudent', {
                parent: 'entity',
                url: '/groupOfStudents',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.groupOfStudent.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/groupOfStudent/groupOfStudents.html',
                        controller: 'GroupOfStudentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('groupOfStudent');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('groupOfStudent.detail', {
                parent: 'entity',
                url: '/groupOfStudent/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.groupOfStudent.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/groupOfStudent/groupOfStudent-detail.html',
                        controller: 'GroupOfStudentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('groupOfStudent');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GroupOfStudent', function($stateParams, GroupOfStudent) {
                        return GroupOfStudent.get({id : $stateParams.id});
                    }]
                }
            })
            .state('groupOfStudent.new', {
                parent: 'groupOfStudent',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/groupOfStudent/groupOfStudent-dialog.html',
                        controller: 'GroupOfStudentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    isActive: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('groupOfStudent', null, { reload: true });
                    }, function() {
                        $state.go('groupOfStudent');
                    })
                }]
            })
            .state('groupOfStudent.edit', {
                parent: 'groupOfStudent',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/groupOfStudent/groupOfStudent-dialog.html',
                        controller: 'GroupOfStudentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GroupOfStudent', function(GroupOfStudent) {
                                return GroupOfStudent.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('groupOfStudent', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('groupOfStudent.delete', {
                parent: 'groupOfStudent',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/groupOfStudent/groupOfStudent-delete-dialog.html',
                        controller: 'GroupOfStudentDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['GroupOfStudent', function(GroupOfStudent) {
                                return GroupOfStudent.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('groupOfStudent', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
