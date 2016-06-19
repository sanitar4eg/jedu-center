'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('timeTable', {
                parent: 'entity',
                url: '/timeTables',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jeducenterApp.timeTable.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeTable/timeTables.html',
                        controller: 'TimeTableController'
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
            .state('timeTable.detail', {
                parent: 'entity',
                url: '/timeTable/{id}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'jeducenterApp.timeTable.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/timeTable/timeTable-detail.html',
                        controller: 'TimeTableDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('timeTable');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'TimeTable', function($stateParams, TimeTable) {
                        return TimeTable.get({id : $stateParams.id});
                    }]
                }
            })
            .state('timeTable.new', {
                parent: 'timeTable',
                url: '/new',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/timeTable/timeTable-dialog.html',
                        controller: 'TimeTableDialogController',
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
                        $state.go('timeTable', null, { reload: true });
                    }, function() {
                        $state.go('timeTable');
                    })
                }]
            })
            .state('timeTable.edit', {
                parent: 'timeTable',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/timeTable/timeTable-dialog.html',
                        controller: 'TimeTableDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['TimeTable', function(TimeTable) {
                                return TimeTable.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('timeTable', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('timeTable.delete', {
                parent: 'timeTable',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_USER'],
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
                        $state.go('timeTable', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
