'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('note', {
                parent: 'entity',
                url: '/notes',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.note.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/note/notes.html',
                        controller: 'NoteController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('note');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('note.detail', {
                parent: 'entity',
                url: '/note/{id}',
                data: {
                    authorities: ['ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.note.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/note/note-detail.html',
                        controller: 'NoteDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('note');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Note', function($stateParams, Note) {
                        return Note.get({id : $stateParams.id});
                    }]
                }
            })
            .state('note.new', {
                parent: 'note',
                url: '/new',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/note/note-dialog.html',
                        controller: 'NoteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {
                                    title: null,
                                    content: null,
                                    date: null,
                                    id: null
                                };
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('note', null, { reload: true });
                    }, function() {
                        $state.go('note');
                    })
                }]
            })
            .state('note.edit', {
                parent: 'note',
                url: '/{id}/edit',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/note/note-dialog.html',
                        controller: 'NoteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Note', function(Note) {
                                return Note.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('note', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('note.delete', {
                parent: 'note',
                url: '/{id}/delete',
                data: {
                    authorities: ['ROLE_ADMIN'],
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/note/note-delete-dialog.html',
                        controller: 'NoteDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Note', function(Note) {
                                return Note.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('note', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
