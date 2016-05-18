'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('students', {
                parent: 'teacher.studentsSet.detail',
                url: '/students',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN'],
                    pageTitle: 'jeducenterApp.student.home.title'
                },
                views: {
                    'students@': {
                        templateUrl: 'scripts/app/teacher/student/teacher.student.html',
                        controller: 'TeacherStudentController'
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
    });
