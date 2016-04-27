'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.lesson', {
                parent: 'teacher',
                url: '/teacher/lessons',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.lesson.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/lesson/teacher.lessons.html',
                        controller: 'TeacherLessonController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lesson');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('teacher.lesson.detail', {
                parent: 'teacher',
                url: '/teacher/lesson/{id}',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.lesson.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/lesson/teacher.lesson-detail.html',
                        controller: 'TeacherLessonDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('lesson');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Lesson', function($stateParams, Lesson) {
                        return Lesson.get({id : $stateParams.id});
                    }]
                }
            })
            .state('teacher.lesson.new', {
                parent: 'teacher.lesson',
                url: '/teacher/new',
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
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.lesson', null, { reload: true });
                    }, function() {
                        $state.go('teacher.lesson');
                    })
                }]
            })
            .state('teacher.lesson.edit', {
                parent: 'teacher.lesson',
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
            .state('teacher.lesson.delete', {
                parent: 'teacher.lesson',
                url: '/teacher/{id}/delete',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/lesson/teacher.lesson-delete-dialog.html',
                        controller: 'TeacherLessonDeleteController',
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
