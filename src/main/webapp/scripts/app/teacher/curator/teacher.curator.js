'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.curator', {
                parent: 'teacher',
                url: '/teacher/curators',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.curator.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/curator/teacher.curators.html',
                        controller: 'TeacherCuratorController'
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
            .state('teacher.curator.detail', {
                parent: 'teacher',
                url: '/teacher/curator/{id}',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.curator.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/curator/teacher.curator-detail.html',
                        controller: 'TeacherCuratorDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('curator');
                        $translatePartialLoader.addPart('student');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Curator', function($stateParams, Curator) {
                        return Curator.get({id : $stateParams.id});
                    }]
                }
            })
            .state('teacher.curator.new', {
                parent: 'teacher.curator',
                url: '/teacher/new',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/curator/teacher.curator-dialog.html',
                        controller: 'TeacherCuratorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    firstName: null,
                                    lastName: null,
                                    email: null,
                                    department: null,
                                    isActive: true,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.curator', null, { reload: true });
                    }, function() {
                        $state.go('teacher.curator');
                    })
                }]
            })
            .state('teacher.curator.edit', {
                parent: 'teacher.curator',
                url: '/teacher/{id}/edit',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/curator/teacher.curator-dialog.html',
                        controller: 'TeacherCuratorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Curator', function(Curator) {
                                return Curator.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.curator', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.curator.delete', {
                parent: 'teacher.curator',
                url: '/teacher/{id}/delete',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
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
                        $state.go('teacher.curator', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.curator.disable', {
                parent: 'teacher.curator',
                url: '/teacher/{id}/disable',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/curator/teacher.curator-disable-dialog.html',
                        controller: 'TeacherCuratorDisableController',
                        size: 'md',
                        resolve: {
                            entity: ['Curator', function(Curator) {
                                return Curator.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.curator', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
