'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.evaluation', {
                parent: 'teacher',
                url: '/teacher/evaluations',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.evaluation.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/evaluation/teacher.evaluations.html',
                        controller: 'TeacherEvaluationController'
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
            .state('teacher.evaluation.detail', {
                parent: 'teacher',
                url: '/teacher/evaluation/{id}',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.evaluation.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/evaluation/teacher.evaluation-detail.html',
                        controller: 'TeacherEvaluationDetailController'
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
            .state('teacher.evaluation.new', {
                parent: 'teacher.evaluation',
                url: '/teacher/new',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/evaluation/teacher.evaluation-dialog.html',
                        controller: 'TeacherEvaluationDialogController',
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
                        $state.go('teacher.evaluation', null, { reload: true });
                    }, function() {
                        $state.go('teacher.evaluation');
                    })
                }]
            })
            .state('teacher.evaluation.edit', {
                parent: 'teacher.evaluation',
                url: '/teacher/{id}/edit',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/evaluation/teacher.evaluation-dialog.html',
                        controller: 'TeacherEvaluationDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Evaluation', function(Evaluation) {
                                return Evaluation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.evaluation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.evaluation.delete', {
                parent: 'teacher.evaluation',
                url: '/teacher/{id}/delete',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/evaluation/teacher.evaluation-delete-dialog.html',
                        controller: 'TeacherEvaluationDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Evaluation', function(Evaluation) {
                                return Evaluation.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.evaluation', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
