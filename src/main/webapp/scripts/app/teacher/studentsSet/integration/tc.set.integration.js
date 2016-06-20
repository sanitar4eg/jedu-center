'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.studentsSet.detail.integration', {
                parent: 'teacher.studentsSet.detail',
                url: '/teacher/integration/students',
                data: {
                    authorities: ['ROLE_TEACHER'],
                    pageTitle: 'jeducenterApp.student.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/studentsSet/integration/tc.set.integration.html',
                        controller: 'TcSetIntegrationController'
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
