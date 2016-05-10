'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.student.archive', {
                parent: 'teacher.student',
                url: '/teacher/student/archive',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/teacher/student/archive/teacher.student.archive.html',
                        controller: 'TeacherStudentArchiveController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('student');
                        $translatePartialLoader.addPart('typeEnumeration');
                        $translatePartialLoader.addPart('universityEnumeration');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            });
    });
