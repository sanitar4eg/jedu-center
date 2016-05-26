'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.studentsSet.detail.students.new', {
                parent: 'teacher.studentsSet.detail.students',
                url: '/student/new',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/studentsSet/student/set.student-dialog.html',
                        controller: 'SetStudentDialogController',
                        size: 'lg',
                        resolve: {
                            // entity: ['Student', function(Student) {
                            //     return Student.get({id : $stateParams.id});
                            // }]
                            entity: function (StudentsSet) {
                                var set = StudentsSet.get({id: $stateParams.id})
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
                                    studentsSet: set,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.studentsSet.detail.students', null, { reload: true });
                    }, function() {
                        $state.go('teacher.studentsSet.detail.students');
                    })
                }]
            })
            .state('teacher.studentsSet.detail.students.edit', {
                parent: 'teacher.studentsSet.detail.students',
                url: '/student/{studentId}/edit',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/studentsSet/student/set.student-dialog.html',
                        controller: 'SetStudentDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Student', function(Student) {
                                return Student.get({id : $stateParams.studentId});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.studentsSet.detail.students', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.studentsSet.detail.students.delete', {
                parent: 'teacher.studentsSet.detail.students',
                url: '/student/{studentId}/delete',
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
                                return Student.get({id : $stateParams.studentId});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.studentsSet.detail.students', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.studentsSet.detail.students.archiving', {
                parent: 'teacher.studentsSet.detail.students',
                url: '/student/{studentId}/archiving',
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
                                return Student.get({id : $stateParams.studentId});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.studentsSet.detail.students', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.studentsSet.detail.students.unzip', {
                parent: 'teacher.studentsSet.detail.students',
                url: '/student/{studentId}/unzip',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/student/archive/teacher.student.unzip.html',
                        controller: 'TeacherStudentUnzipController',
                        size: 'lg',
                        resolve: {
                            entity: ['Student', function(Student) {
                                return Student.get({id : $stateParams.studentId});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.studentsSet.detail.students', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
    });
