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
            });
    });
