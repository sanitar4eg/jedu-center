'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.studentsSet', {
                parent: 'teacher',
                url: '/teacher/studentsSets',
                data: {
                    authorities: ['ROLE_TEACHER','ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.studentsSet.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/studentsSet/teacher.studentsSets.html',
                        controller: 'TeacherStudentsSetController'
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
            .state('teacher.studentsSet.detail', {
                parent: 'teacher',
                url: '/teacher/studentsSet/{id}',
                data: {
                    authorities: ['ROLE_TEACHER','ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.studentsSet.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/studentsSet/teacher.studentsSet-detail.html',
                        controller: 'TeacherStudentsSetDetailController'
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
            .state('teacher.studentsSet.new', {
                parent: 'teacher.studentsSet',
                url: '/teacher/new',
                data: {
                    authorities: ['ROLE_TEACHER','ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/studentsSet/teacher.studentsSet-dialog.html',
                        controller: 'TeacherStudentsSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    description: null,
                                    isActive: true,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.studentsSet', null, { reload: true });
                    }, function() {
                        $state.go('teacher.studentsSet');
                    })
                }]
            })
            .state('teacher.studentsSet.edit', {
                parent: 'teacher.studentsSet',
                url: '/teacher/{id}/edit',
                data: {
                    authorities: ['ROLE_TEACHER','ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/studentsSet/teacher.studentsSet-dialog.html',
                        controller: 'TeacherStudentsSetDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['StudentsSet', function(StudentsSet) {
                                return StudentsSet.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.studentsSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.studentsSet.delete', {
                parent: 'teacher.studentsSet',
                url: '/teacher/{id}/delete',
                data: {
                    authorities: ['ROLE_TEACHER','ROLE_ADMIN']
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
                        $state.go('teacher.studentsSet', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
