'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('student', {
                parent: 'entity',
                url: '/students',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.student.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/student/students.html',
                        controller: 'StudentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('student');
                        $translatePartialLoader.addPart('universityEnumeration');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('student.detail', {
                parent: 'entity',
                url: '/student/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.student.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/student/student-detail.html',
                        controller: 'StudentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('student');
                        $translatePartialLoader.addPart('universityEnumeration');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Student', function($stateParams, Student) {
                        return Student.get({id : $stateParams.id});
                    }]
                }
            })
            .state('student.new', {
                parent: 'student',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/student/student-dialog.html',
                        controller: 'StudentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    lastName: null,
                                    firstName: null,
                                    middleName: null,
                                    email: null,
                                    phone: null,
                                    university: null,
                                    specialty: null,
                                    faculty: null,
                                    course: null,
                                    isActive: false,
                                    gotJob: false,
                                    comment: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('student', null, { reload: true });
                    }, function() {
                        $state.go('student');
                    })
                }]
            })
            .state('student.edit', {
                parent: 'student',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/student/student-dialog.html',
                        controller: 'StudentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Student', function(Student) {
                                return Student.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('student', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('student.delete', {
                parent: 'student',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/student/student-delete-dialog.html',
                        controller: 'StudentDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Student', function(Student) {
                                return Student.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('student', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
