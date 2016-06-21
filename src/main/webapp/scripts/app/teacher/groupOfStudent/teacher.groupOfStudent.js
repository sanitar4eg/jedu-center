'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.groupOfStudent', {
                parent: 'teacher',
                url: '/teacher/groupOfStudents',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE'],
                    pageTitle: 'jeducenterApp.groupOfStudent.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/groupOfStudent/teacher.groupOfStudents.html',
                        controller: 'TeacherGroupOfStudentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('groupOfStudent');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('learningType');
                        $translatePartialLoader.addPart('student');
                        return $translate.refresh();
                    }]
                }
            })
            .state('teacher.groupOfStudent.detail', {
                parent: 'teacher',
                url: '/teacher/groupOfStudent/{id}',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE'],
                    pageTitle: 'jeducenterApp.groupOfStudent.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/groupOfStudent/teacher.groupOfStudent-detail.html',
                        controller: 'TeacherGroupOfStudentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('groupOfStudent');
                        $translatePartialLoader.addPart('student');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'GroupOfStudent', function($stateParams, GroupOfStudent) {
                        return GroupOfStudent.get({id : $stateParams.id});
                    }]
                }
            })
            .state('teacher.groupOfStudent.new', {
                parent: 'teacher.groupOfStudent',
                url: '/teacher/new',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/groupOfStudent/teacher.groupOfStudent-dialog.html',
                        controller: 'TeacherGroupOfStudentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    type: null,
                                    description: null,
                                    isActive: true,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.groupOfStudent', null, { reload: true });
                    }, function() {
                        $state.go('teacher.groupOfStudent');
                    })
                }]
            })
            .state('teacher.groupOfStudent.edit', {
                parent: 'teacher.groupOfStudent',
                url: '/teacher/{id}/edit',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/groupOfStudent/teacher.groupOfStudent-dialog.html',
                        controller: 'TeacherGroupOfStudentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['GroupOfStudent', function(GroupOfStudent) {
                                return GroupOfStudent.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.groupOfStudent', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.groupOfStudent.delete', {
                parent: 'teacher.groupOfStudent',
                url: '/teacher/{id}/delete',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE']
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
                        $state.go('teacher.groupOfStudent', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.groupOfStudent.students', {
            parent: 'teacher.groupOfStudent.detail',
            url: '/teacher/groupOfStudent/students/{id}',
            data: {
                authorities: ['ROLE_TEACHER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE'],
                pageTitle: 'jeducenterApp.groupOfStudent.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'scripts/app/teacher/groupOfStudent/teacher.groupOfStudent-students.html',
                    controller: 'TeacherGroupOfStudentStudentsController'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('groupOfStudent');
                    $translatePartialLoader.addPart('student');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'GroupOfStudent', function($stateParams, GroupOfStudent) {
                    return GroupOfStudent.get({id : $stateParams.id});
                }]
            }
        });
    });
