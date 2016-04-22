'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('lesson', {
                parent: 'entity',
                url: '/lessons',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.lesson.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lesson/lessons.html',
                        controller: 'LessonController'
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
            .state('lesson.detail', {
                parent: 'entity',
                url: '/lesson/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.lesson.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/lesson/lesson-detail.html',
                        controller: 'LessonDetailController'
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
            .state('lesson.new', {
                parent: 'lesson',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lesson/lesson-dialog.html',
                        controller: 'LessonDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    topic: null,
                                    time: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('lesson', null, { reload: true });
                    }, function() {
                        $state.go('lesson');
                    })
                }]
            })
            .state('lesson.edit', {
                parent: 'lesson',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/lesson/lesson-dialog.html',
                        controller: 'LessonDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Lesson', function(Lesson) {
                                return Lesson.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('lesson', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('lesson.delete', {
                parent: 'lesson',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
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
                        $state.go('lesson', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
