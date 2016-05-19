'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.studentsSet.detail.students.new', {
                parent: 'teacher.studentsSet.detail.students',
                url: '/set/new',
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
            .state('set.students.edit', {
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
            .state('teacher.studentsSet.detail.students.delete', {
                parent: 'teacher.studentsSet.detail.students',
                url: '/set/{id}/delete',
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
                        $state.go('teacher.studentsSet.detail.students', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('set.student.archiving', {
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
    });
