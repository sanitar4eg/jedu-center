'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.student.history', {
                parent: 'teacher.student',
                url: '/history/students',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.student.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/student/history/history.students.html',
                        controller: 'StudentHistoryController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('student');
                        $translatePartialLoader.addPart('typeEnumeration');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('teacher.student.integration', {
                parent: 'teacher.student',
                url: '/integration/students',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.student.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/student/integration/integration.students.html',
                        controller: 'StudentIntegrationController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('student');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            });
    });
