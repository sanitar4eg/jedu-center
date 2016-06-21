'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.timeTable.detail.lesson', {
                parent: 'teacher.timeTable.detail',
                url: '/teacher/timetable/new',
                params: {timeTable: null},
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/lesson/teacher.lesson-dialog.html',
                        controller: 'TeacherLessonDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    topic: null,
                                    time: new Date,
                                    id: null,
                                    timeTable: $stateParams.timeTable
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.timeTable.detail', null, { reload: true });
                    }, function() {
                        $state.go('teacher.timeTable.detail');
                    })
                }]
            })
            .state('teacher.timeTable.detail.lesson.edit', {
                parent: 'teacher.timeTable.detail',
                url: '/teacher/{id}/edit',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/lesson/teacher.lesson-dialog.html',
                        controller: 'TeacherLessonDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Lesson', function(Lesson) {
                                return Lesson.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.lesson', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.timeTable.detail.lesson.delete', {
                parent: 'teacher.timeTable.detail.lesson',
                url: '/teacher/{id}/delete',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                scope: {},
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lesson/lesson-delete-dialog.html',
                        controller: 'LessonDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Lesson', function(Lesson) {
                                return Lesson.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.lesson', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
