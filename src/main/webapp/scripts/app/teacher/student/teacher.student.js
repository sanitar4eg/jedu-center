'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.student', {
                parent: 'teacher',
                url: '/teacher/student',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.student.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/student/archive/teacher.student.archive.html',
                        controller: 'TeacherStudentArchiveController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('student');
                        $translatePartialLoader.addPart('universityEnumeration');
                        $translatePartialLoader.addPart('learningResult');
                        $translatePartialLoader.addPart('typeOfResult');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('teacher.student.detail', {
                parent: 'teacher',
                url: '/teacher/student/{id}',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.student.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/student/teacher.student-detail.html',
                        controller: 'TeacherStudentDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('student');
                        $translatePartialLoader.addPart('universityEnumeration');
                        $translatePartialLoader.addPart('recall');
                        $translatePartialLoader.addPart('typeRecallEnumeration');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Student', function($stateParams, Student) {
                        return Student.get({id : $stateParams.id});
                    }]
                }
            })
            .state('teacher.student.new', {
                parent: 'teacher.student',
                url: '/teacher/new',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/student/teacher.student-dialog.html',
                        controller: 'TeacherStudentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    firstName: null,
                                    lastName: null,
                                    middleName: null,
                                    type: null,
                                    email: null,
                                    phone: null,
                                    university: null,
                                    specialty: null,
                                    course: null,
                                    isActive: true,
                                    gotJob: false,
                                    comment: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.student', null, { reload: true });
                    }, function() {
                        $state.go('teacher.student');
                    })
                }]
            })
            .state('teacher.student.edit', {
                parent: 'teacher.student',
                url: '/teacher/{id}/edit',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/student/teacher.student-dialog.html',
                        controller: 'TeacherStudentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Student', function(Student) {
                                return Student.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.student', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.student.delete', {
                parent: 'teacher.student',
                url: '/teacher/{id}/delete',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
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
                        $state.go('teacher.student', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.student.archiving', {
                parent: 'teacher.student',
                url: '/teacher/{id}/archiving',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/student/archive/teacher.student.archiving.html',
                        controller: 'TeacherStudentArchivingController',
                        size: 'lg',
                        resolve: {
                            entity: ['Student', function(Student) {
                                return Student.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.student', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.student.detail.recall', {
                parent: 'teacher.student.detail',
                url: '/teacher/student/new',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
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
                        $state.go('teacher.student.detail', null, {reload: true});
                    }, function () {
                        $state.go('teacher.student.detail');
                    })
                }]
            });
    });
