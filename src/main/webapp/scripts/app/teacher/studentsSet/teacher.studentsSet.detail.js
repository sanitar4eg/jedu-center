'use strict';

angular.module('jeducenterApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('teacher.studentsSet.detail.students', {
                parent: 'teacher.studentsSet.detail',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                views: {
                    'students': {
                        templateUrl: 'scripts/app/teacher/studentsSet/student/set.student.html',
                        controller: 'SetStudentController'
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
            .state('teacher.studentsSet.detail.groups', {
                parent: 'teacher.studentsSet.detail',
                data: {
                    authorities: ['ROLE_TEACHER', 'ROLE_ADMIN']
                },
                views: {
                    'groups': {
                        templateUrl: 'scripts/app/teacher/groupOfStudent/teacher.groupOfStudents.html',
                        controller: 'TeacherGroupOfStudentController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('groupOfStudent');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
    });
