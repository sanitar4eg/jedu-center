'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.recall', {
                parent: 'teacher',
                url: '/teacher/recalls',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE'],
                    pageTitle: 'jeducenterApp.recall.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/recall/teacher.recalls.html',
                        controller: 'TeacherRecallController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('recall');
                        $translatePartialLoader.addPart('typeRecallEnumeration');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('teacher.recall.detail', {
                parent: 'teacher',
                url: '/teacher/recall/{id}',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE'],
                    pageTitle: 'jeducenterApp.recall.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/recall/teacher.recall-detail.html',
                        controller: 'TeacherRecallDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('recall');
                        $translatePartialLoader.addPart('typeRecallEnumeration');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Recall', function ($stateParams, Recall) {
                        return Recall.get({id: $stateParams.id});
                    }]
                }
            })
            .state('teacher.recall.new', {
                parent: 'teacher.recall',
                url: '/teacher/new',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE']
                },
                params: {student: null},
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/recall/teacher.recall-dialog.html',
                        controller: 'TeacherRecallDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    type: null,
                                    name: null,
                                    description: null,
                                    file: null,
                                    id: null,
                                    student: $stateParams.student
                                };
                            }
                        }
                    }).result.then(function (result) {
                        $state.go('teacher.recall', null, {reload: true});
                    }, function () {
                        $state.go('teacher.recall');
                    })
                }]
            })
            .state('teacher.recall.edit', {
                parent: 'teacher.recall',
                url: '/teacher/{id}/edit',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/recall/teacher.recall-dialog.html',
                        controller: 'TeacherRecallDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Recall', function (Recall) {
                                return Recall.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('teacher.recall', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.recall.delete', {
                parent: 'teacher.recall',
                url: '/teacher/{id}/delete',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/recall/recall-delete-dialog.html',
                        controller: 'RecallDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Recall', function (Recall) {
                                return Recall.get({id: $stateParams.id});
                            }]
                        }
                    }).result.then(function (result) {
                        $state.go('teacher.recall', null, {reload: true});
                    }, function () {
                        $state.go('^');
                    })
                }]
            });
    });
