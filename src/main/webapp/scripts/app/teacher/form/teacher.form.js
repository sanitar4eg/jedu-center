'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.form', {
                parent: 'teacher',
                url: '/teacher/forms',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_TEACHER'],
                    pageTitle: 'jeducenterApp.form.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/form/teacher.forms.html',
                        controller: 'TeacherFormController'
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
            .state('teacher.form.detail', {
                parent: 'teacher',
                url: '/teacher/form/{id}',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_TEACHER'],
                    pageTitle: 'jeducenterApp.form.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/form/teacher.form-detail.html',
                        controller: 'TeacherFormDetailController'
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
            .state('teacher.form.new', {
                parent: 'teacher.form',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_TEACHER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/form/teacher.form-dialog.html',
                        controller: 'TeacherFormDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    file: null,
                                    creationTime: new Date,
                                    isActive: false,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.form', null, { reload: true });
                    }, function() {
                        $state.go('teacher.form');
                    })
                }]
            })
            .state('teacher.form.edit', {
                parent: 'teacher.form',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_TEACHER']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/form/teacher.form-dialog.html',
                        controller: 'TeacherFormDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Form', function(Form) {
                                return Form.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.form', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.form.delete', {
                parent: 'teacher.form',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN', 'ROLE_TEACHER']
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
                        $state.go('teacher.form', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
