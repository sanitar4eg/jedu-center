'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('studentsSet', {
                parent: 'entity',
                url: '/studentsSets',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jeducenterApp.studentsSet.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/studentsSet/studentsSets.html',
                        controller: 'StudentsSetController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('studentsSet');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('studentsSet.detail', {
                parent: 'entity',
                url: '/studentsSet/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jeducenterApp.studentsSet.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/studentsSet/studentsSet-detail.html',
                        controller: 'StudentsSetDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('studentsSet');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'StudentsSet', function($stateParams, StudentsSet) {
                        return StudentsSet.get({id : $stateParams.id});
                    }]
                }
            })
            .state('studentsSet.new', {
                parent: 'studentsSet',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/studentsSet/studentsSet-dialog.html',
                        controller: 'StudentsSetDialogController',
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
                        $state.go('studentsSet', null, { reload: true });
                    }, function() {
                        $state.go('studentsSet');
                    })
                }]
            })
            .state('studentsSet.edit', {
                parent: 'studentsSet',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/studentsSet/studentsSet-dialog.html',
                        controller: 'StudentsSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['StudentsSet', function(StudentsSet) {
                                return StudentsSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('studentsSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('studentsSet.delete', {
                parent: 'studentsSet',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/studentsSet/studentsSet-delete-dialog.html',
                        controller: 'StudentsSetDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['StudentsSet', function(StudentsSet) {
                                return StudentsSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('studentsSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
