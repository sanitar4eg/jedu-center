'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('cur-tab.student', {
                parent: 'cur-tab',
                url: '/cur-tab/students',
                data: {
                    authorities: ['ROLE_CURATOR'],
                    pageTitle: 'jeducenterApp.student.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/cur-tab/student/cur-tab.students.html',
                        controller: 'CurTabStudentController'
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
            .state('cur-tab.student.detail', {
                parent: 'cur-tab',
                url: '/cur-tab/student/{id}',
                data: {
                    authorities: ['ROLE_CURATOR'],
                    pageTitle: 'jeducenterApp.student.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/cur-tab/student/cur-tab.student-detail.html',
                        controller: 'CurTabStudentDetailController'
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
            .state('cur-tab.student.detail.recall', {
                parent: 'cur-tab.student.detail',
                url: '/cur-tab/student/new',
                data: {
                    authorities: ['ROLE_CURATOR', 'ROLE_ADMIN']
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
                        $state.go('cur-tab.student.detail', null, {reload: true});
                    }, function () {
                        $state.go('cur-tab.student.detail');
                    })
                }]
            })
            .state('cur-tab.student.detail.recall.edit', {
                parent: 'cur-tab.student.detail',
                url: 'cur-tab/recall/{id}/edit',
                data: {
                    authorities: ['ROLE_CURATOR']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/recall/recall-dialog.html',
                        controller: 'RecallDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Recall', function(Recall) {
                                return Recall.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cur-tab.student.detail', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            })
            .state('cur-tab.student.detail.recall.delete', {
                parent: 'cur-tab.student.detail',
                url: 'cur-tab/recall/{id}/delete',
                data: {
                    authorities: ['ROLE_CURATOR']
                },
                onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                    $uibModal.open({
                        templateUrl: 'scripts/app/entities/recall/recall-delete-dialog.html',
                        controller: 'RecallDeleteController',
                        size: 'md',
                        resolve: {
                            entity: ['Recall', function(Recall) {
                                return Recall.get({id : $stateParams.id});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('cur-tab.student.detail', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
