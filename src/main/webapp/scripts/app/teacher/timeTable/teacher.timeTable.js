'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.timeTable', {
                parent: 'teacher',
                url: '/teacher/timeTables',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.timeTable.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/timeTable/teacher.timeTables.html',
                        controller: 'TeacherTimeTableController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeTable');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('teacher.timeTable.detail', {
                parent: 'teacher',
                url: '/teacher/timeTable/{id}',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.timeTable.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/timeTable/teacher.timeTable-detail.html',
                        controller: 'TeacherTimeTableDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeTable');
                        $translatePartialLoader.addPart('student');
                        $translatePartialLoader.addPart('evaluation');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TimeTable', function($stateParams, TimeTable) {
                        return TimeTable.get({id : $stateParams.id});
                    }]
                }
            })
            .state('teacher.timeTable.new', {
                parent: 'teacher.timeTable',
                url: '/teacher/new',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/timeTable/teacher.timeTable-dialog.html',
                        controller: 'TeacherTimeTableDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    name: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.timeTable', null, { reload: true });
                    }, function() {
                        $state.go('teacher.timeTable');
                    })
                }]
            })
            .state('teacher.timeTable.edit', {
                parent: 'teacher.timeTable',
                url: '/teacher/{id}/edit',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/teacher/timeTable/teacher.timeTable-dialog.html',
                        controller: 'TeacherTimeTableDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TimeTable', function(TimeTable) {
                                return TimeTable.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.timeTable', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('teacher.timeTable.delete', {
                parent: 'teacher.timeTable',
                url: '/teacher/{id}/delete',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/timeTable/timeTable-delete-dialog.html',
                        controller: 'TimeTableDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['TimeTable', function(TimeTable) {
                                return TimeTable.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('teacher.timeTable', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
